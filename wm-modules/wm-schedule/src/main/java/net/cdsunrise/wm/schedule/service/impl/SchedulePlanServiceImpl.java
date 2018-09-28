package net.cdsunrise.wm.schedule.service.impl;

import net.cdsunrise.wm.base.exception.ServiceErrorException;
import net.cdsunrise.wm.base.util.DateUtil;
import net.cdsunrise.wm.base.util.ExcelImport;
import net.cdsunrise.wm.base.web.UserUtils;
import net.cdsunrise.wm.schedule.entity.*;
import net.cdsunrise.wm.schedule.enums.*;
import net.cdsunrise.wm.schedule.feign.BeamFeign;
import net.cdsunrise.wm.schedule.feign.SystemFeign;
import net.cdsunrise.wm.schedule.repository.*;
import net.cdsunrise.wm.schedule.service.SchedulePlanService;
import net.cdsunrise.wm.schedule.util.SchedulePlanAbstractImportUtils;
import net.cdsunrise.wm.schedule.util.SchedulePlanImportUtils;
import net.cdsunrise.wm.schedule.util.ScheduleWorkPointPlanExportUtils;
import net.cdsunrise.wm.schedule.util.ScheduleWorkPointPlanImportUtils;
import net.cdsunrise.wm.schedule.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

/***
 * @author gechaoqing
 * 进度计划实现
 */
@Service
public class SchedulePlanServiceImpl implements SchedulePlanService {
    /***
     * 进度计划仓库
     */
    private SchedulePlanRepository planRepository;
    /**
     * 前置任务数据仓库
     */
    private SchedulePlanFrontRepository planFrontRepository;
    /***
     * 进度关联模型数据仓库
     */
    private SchedulePlanModelRepository modelRepository;
    /***
     * 进度上报数据仓库
     */
    private ScheduleReportRepository reportRepository;
    /***
     * 进度上报明细仓库
     */
    private ScheduleReportDetailRepository reportDetailRepository;
    /***
     * 进度延滞影响因素仓库
     */
    private DelayReasonRepository delayReasonRepository;

    /***
     * 进度上报关联延滞因素仓库
     */
    private ScheduleReportRefDelayReasonRepository refDelayReasonRepository;


    @PersistenceContext
    private EntityManager em;

    private BigDecimal hundred = BigDecimal.valueOf(100);
    @Autowired
    private SystemFeign systemFeign;
    @Autowired
    private BeamFeign beamFeign;
    public SchedulePlanServiceImpl(SchedulePlanRepository schedulePlanRepository
            , SchedulePlanFrontRepository planFrontRepository
            , SchedulePlanModelRepository modelRepository
            , ScheduleReportRepository reportRepository
            , ScheduleReportDetailRepository reportDetailRepository
            , DelayReasonRepository delayReasonRepository
            , ScheduleReportRefDelayReasonRepository refDelayReasonRepository) {
        this.planRepository = schedulePlanRepository;
        this.planFrontRepository = planFrontRepository;
        this.modelRepository = modelRepository;
        this.reportRepository = reportRepository;
        this.reportDetailRepository = reportDetailRepository;
        this.delayReasonRepository = delayReasonRepository;
        this.refDelayReasonRepository = refDelayReasonRepository;
    }

    /***
     * 根据父级任务ID获取子任务个数
     */
    private int countPlan(SchedulePlan entity) {
        Long parentId = entity.getParentId();
        List<SchedulePlan> planList;
        if (parentId == null) {
            planList = planRepository.findByParentIdIsNull();
        } else {
            planList = planRepository.findByParentId(parentId);
        }
        /*for (SchedulePlan plan : planList) {
            if (entity.getName().equals(plan.getName()) && !SchedulePlanStatus.DELETE.name().equals(plan.getStatus())) {
                throw new ServiceErrorException("同级任务名称已重复，请重新设置");
            }
        }*/
        return planList.size();
    }

    /***
     * 获取两个日期的间隔天数
     * @param sDate 开始时间
     * @param eDate 结束时间
     * @return 天数
     */
    private int getDateDayGap(Date sDate, Date eDate) {
        return (int) ((eDate.getTime() - sDate.getTime()) / (1000L * 3600L * 24L));
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(SchedulePlanSaveVo saveVo) {
        SchedulePlan entity = saveVo.getSchedulePlan();
        if (entity == null) {
            throw new ServiceErrorException("无可保存的进度计划");
        }
        //List<SchedulePlanRefModelVo> models = saveVo.getModelList();
        //Long assignToUserId = saveVo.getAssignToUserId();
        boolean isAdd = false;
        SchedulePlan parent = null;
        SchedulePlanCategory planCategory = checkCategory(entity.getCategory());
        Date start = entity.getStartDate();
        Date finish = entity.getCompleteDate();
        if (start == null || finish == null) {
            throw new ServiceErrorException("请正确设置计划开始时间和结束时间！");
        }
        if(start.compareTo(finish)>0){
            throw new ServiceErrorException("开始时间不能大于结束时间！");
        }
        SchedulePlan thisPlan = null;
        if(entity.getId()!=null){
            thisPlan = planRepository.findOne(entity.getId());
            if (thisPlan == null) {
                throw new ServiceErrorException("修改进度计划，但进度计划不存在，无法进行保存！");
            }
        }
        //如果是月计划判断是否在同一个月
        if (SchedulePlanCategory.MONTH==planCategory) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(start);
            int startMonth = calendar.get(Calendar.MONTH);
            calendar.setTime(finish);
            int finishMonth = calendar.get(Calendar.MONTH);
            if (startMonth != finishMonth) {
                throw new ServiceErrorException("月计划开始时间与结束时间不在同一个月内！");
            }
            if(thisPlan!=null
                    &&SchedulePlanConfirmStatus.OK.name().equals(thisPlan.getConfirmStatus())){
                Date now = new Date();
                calendar.setTime(now);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                if(day<20||day>25){
                    throw new ServiceErrorException("月计划只可在20-25号进行修改！");
                }
            }
        }else if(SchedulePlanCategory.YEAR==planCategory){
            if(thisPlan!=null&&SchedulePlanConfirmStatus.OK.name().equals(thisPlan.getConfirmStatus())){
                throw new ServiceErrorException("年计划不可修改！");
            }
        }

        if(StringUtils.isBlank(entity.getName())){
            throw new ServiceErrorException("请填写进度计划名称");
        }

        if(entity.getQuantity()!=null&&entity.getQuantity().compareTo(BigDecimal.ZERO)<0){
            throw new ServiceErrorException("请正确填写计划工程量");
        }
        if (entity.getParentId() != null) {
            parent = planRepository.findOne(entity.getParentId());
            if (parent == null) {
                String error = "【" + entity.getName() + "】的父级任务不存在！";
                throw new ServiceErrorException(error);
            }
            if(entity.getQuantity()!=null){
                List<SchedulePlan> children = planRepository.findDirectChildren(entity.getParentId(),entity.getName());
                if(children!=null&&!children.isEmpty()){
                    BigDecimal quantity = BigDecimal.ZERO;
                    for(SchedulePlan plan:children){
                        if(plan.getQuantity()!=null){
                            quantity = quantity.add(plan.getQuantity());
                        }
                    }
                    quantity = quantity.add(entity.getQuantity());
                    if(quantity.compareTo(parent.getQuantity())>0){
                        String category = planCategory.getDisc();
                        String parentCategory = SchedulePlanCategory.valueOf(parent.getCategory()).getDisc();
                        throw new ServiceErrorException(category+"计划工程量已超出"+parentCategory+"的计划工程量["+parent.getQuantity()+"]");
                    }
                }
            }
        }
        //设置创建人、编码、状态
        if (thisPlan == null) {
            if (parent != null) {
                String parentCode = parent.getCode();
                int size = countPlan(entity);
                entity.setWorkPointId(parent.getWorkPointId());
                entity.setCode(parentCode.concat(SchedulePlan.CODE_SPLIT).concat(String.valueOf(size + 1)));
            } else {
                int size = countPlan(entity);
                entity.setCode(String.valueOf(size + 1));
            }

            isAdd = true;
            entity.setActualCompleteDate(null);
            entity.setActualStartDate(null);
            entity.setCreateUserId(UserUtils.getUserId());
            entity.setAuditStatus(null);
            entity.setCompletePercent(BigDecimal.ZERO);
            entity.setStatus(SchedulePlanStatus.NOT_START.name());
            UserVo userVo = systemFeign.fetchCurrentUser();
            entity.setCompanyId(userVo.getCompanyId());
        } else {
            if (!SchedulePlanStatus.NOT_START.name().equals(thisPlan.getStatus())) {
                throw new ServiceErrorException("进度计划不是未开工状态，不可修改");
            }
            /*if (SchedulePlanConfirmStatus.OK.name().equals(thisPlan.getConfirmStatus())) {
                throw new ServiceErrorException("进度计划已经审核通过，不可修改");
            }*/
            entity.setCode(thisPlan.getCode());
            entity.setCreateUserId(thisPlan.getCreateUserId());
            entity.setCompanyId(thisPlan.getCompanyId());
            entity.setWorkPointId(thisPlan.getWorkPointId());
            entity.setStatus(thisPlan.getStatus());
            entity.setCompletePercent(thisPlan.getCompletePercent());
            entity.setAuditStatus(thisPlan.getAuditStatus());
            //生成制梁计划
            /*if(entity.getCompleteDate().compareTo(thisPlan.getCompleteDate())!=0){
                createLedgerPlan(entity);
            }*/
        }
        if (!isAdd && entity.getId() != null) {
            Long parentId = planRepository.findOne(entity.getId()).getParentId();
            if (parentId != null) {
                parent = planRepository.findOne(parentId);
            }
        }
        boolean isParentNotOk = isAdd && entity.getParentId() == null
                && !SchedulePlanCategory.YEAR.name().equals(entity.getCategory())
                && !SchedulePlanCategory.MASTER.name().equals(entity.getCategory());
        if (isParentNotOk) {
            throw new ServiceErrorException("【" + entity.getName() + "】不可没有父级任务");
        }
//        entity.setCategory(SchedulePlanCategory.MONTH.name());
        if (parent != null) {
            if (start.compareTo(parent.getStartDate()) < 0) {
                throw new ServiceErrorException("开工时间不能早于父级任务的开工时间：" + DateFormatUtils.format(parent.getStartDate(), "yyyy-MM-dd"));
            }
            if (finish.compareTo(parent.getCompleteDate()) > 0) {
                throw new ServiceErrorException("完工时间不能晚于父级任务的完工时间：" + DateFormatUtils.format(parent.getCompleteDate(), "yyyy-MM-dd"));
            }
        }

        int timeLimit = getDateDayGap(start, finish) + 1;
        entity.setTimeLimit(timeLimit);
        String finishDateStr = DateFormatUtils.format(finish, "yyyy-MM-dd") + " 23:59:59";
        try {
            entity.setCompleteDate(DateUtils.parseDate(finishDateStr, "yyyy-MM-dd hh:mm:ss"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //暂时定保存后就是草稿(周计划除外)，需要提交去审核
        if(planCategory==SchedulePlanCategory.WEEK){
            entity.setConfirmStatus(SchedulePlanConfirmStatus.OK.name());
        }else{
            entity.setConfirmStatus(SchedulePlanConfirmStatus.DRAFT.name());
        }
        entity = planRepository.save(entity);
//        List<SchedulePlanFront> frontList = saveVo.getPlanFrontList();
       /* if (!isAdd) {
            //删除已关联的模型信息
            planRepository.deleteRefModelByPlanId(entity.getId());

            //删除已关联的负责人
           // planRepository.deleteRefUserByPlanId(entity.getId());

            //删除前置任务关系
//            planFrontRepository.deleteRefFrontByPlanId(entity.getId());
        }
        if (models != null && models.size() > 0) {
            //保存关联模型信息
            for (SchedulePlanRefModelVo refModel : models) {
                planRepository.insertRefModel(entity.getId(), refModel.getFid(),refModel.getGuid());
            }
        }*/

        /*//保存前置任务
        if (frontList != null && !frontList.isEmpty()) {
            for (SchedulePlanFront planFront : frontList) {
                SchedulePlan front = planRepository.findOne(planFront.getScheduleFrontId());
                if (front == null) {
                    throw new ServiceErrorException("参数错误，找不到前置任务！");
                }
                String frontRel = planFront.getFrontRel();
                long gap =0;
                if (SchedulePlanFrontRel.SS.name().equals(frontRel)) {
                    if (start.compareTo(front.getStartDate()) < 0) {
                        throw new ServiceErrorException("前置关系是开始-开始，前置任务【" + front.getName() + "】开始日期必须小于等于当前任务开始日期");
                    }
                    gap = getDateDayGap(start,front.getStartDate());

                } else if (SchedulePlanFrontRel.SF.name().equals(frontRel)) {
                    if (finish.compareTo(front.getStartDate()) < 0) {
                        throw new ServiceErrorException("前置关系是开始-完成，前置任务【" + front.getName() + "】开始日期必须小于等于当前任务完成日期");
                    }
                    gap = getDateDayGap(finish,front.getStartDate());
                } else if (SchedulePlanFrontRel.FS.name().equals(frontRel)) {
                    if (start.compareTo(front.getCompleteDate()) < 0) {
                        throw new ServiceErrorException("前置关系是完成-开始，前置任务【" + front.getName() + "】完成日期必须小于等于当前任务开始日期");
                    }
                    gap = getDateDayGap(start,front.getCompleteDate());
                } else if (SchedulePlanFrontRel.FF.name().equals(frontRel)) {
                    if (finish.compareTo(front.getCompleteDate()) < 0) {
                        throw new ServiceErrorException("前置关系是完成-完成，前置任务【" + front.getName() + "】完成日期必须小于等于当前任务完成日期");
                    }
                    gap = getDateDayGap(finish,front.getCompleteDate());
                }
                planFront.setFrontVal((int)Math.abs(gap));
            }
            //保存前置任务关系
            planFrontRepository.save(frontList);
        }*/

        //保存关联负责人信息
        //planRepository.insertRefUser(entity.getId(), assignToUserId);
    }

    @Override
    public void saveFront(Long planId, List<SchedulePlanFront> planFrontList) {
        SchedulePlan plan = planRepository.findOne(planId);
        if (plan == null) {
            throw new ServiceErrorException("进度任务不存在");
        }
        //删除前置任务关系
        planFrontRepository.deleteRefFrontByPlanId(planId);
        Date start = plan.getStartDate();
        Date finish = plan.getCompleteDate();
        //保存前置任务
        if (planFrontList != null && !planFrontList.isEmpty()) {
            for (SchedulePlanFront planFront : planFrontList) {
                SchedulePlan front = planRepository.findOne(planFront.getScheduleFrontId());
                if (front == null) {
                    throw new ServiceErrorException("参数错误，找不到前置任务！");
                }
                String frontRel = planFront.getFrontRel();
                long gap = 0;
                if (SchedulePlanFrontRel.SS.name().equals(frontRel)) {
                    if (start.compareTo(front.getStartDate()) < 0) {
                        throw new ServiceErrorException("前置关系是开始-开始，前置任务【" + front.getName() + "】开始日期必须小于等于当前任务开始日期");
                    }
                    gap = getDateDayGap(start, front.getStartDate());

                } else if (SchedulePlanFrontRel.SF.name().equals(frontRel)) {
                    if (finish.compareTo(front.getStartDate()) < 0) {
                        throw new ServiceErrorException("前置关系是开始-完成，前置任务【" + front.getName() + "】开始日期必须小于等于当前任务完成日期");
                    }
                    gap = getDateDayGap(finish, front.getStartDate());
                } else if (SchedulePlanFrontRel.FS.name().equals(frontRel)) {
                    if (start.compareTo(front.getCompleteDate()) < 0) {
                        throw new ServiceErrorException("前置关系是完成-开始，前置任务【" + front.getName() + "】完成日期必须小于等于当前任务开始日期");
                    }
                    gap = getDateDayGap(start, front.getCompleteDate());
                } else if (SchedulePlanFrontRel.FF.name().equals(frontRel)) {
                    if (finish.compareTo(front.getCompleteDate()) < 0) {
                        throw new ServiceErrorException("前置关系是完成-完成，前置任务【" + front.getName() + "】完成日期必须小于等于当前任务完成日期");
                    }
                    gap = getDateDayGap(finish, front.getCompleteDate());
                }
                planFront.setFrontVal((int) Math.abs(gap));
                planFront.setScheduleId(planId);
            }
            //保存前置任务关系
            planFrontRepository.save(planFrontList);
        }
    }

    /***
     * 生成制梁计划
     * @param entity 进度计划
     */
    private void createLedgerPlan(SchedulePlan entity){
        List<String> pierCodeList = modelRepository.getRefModelPierCode(entity.getId());
        if(pierCodeList!=null&&!pierCodeList.isEmpty()){
            LedgerPlanVo ledgerPlanVo = new LedgerPlanVo();
            ledgerPlanVo.setPierList(pierCodeList);
            List<LedgerPiersVo> ledgerPiersVoList = beamFeign.fetchLedgerPiers(ledgerPlanVo);
            if(ledgerPiersVoList!=null&&!ledgerPiersVoList.isEmpty()){
                Set<String> pierCodsSet = new HashSet<>();
                Map<String,Date> pierCompleteDateMap = new HashMap<>(5);
                for(LedgerPiersVo piersVo:ledgerPiersVoList){
                    pierCodsSet.add(piersVo.getPierNumberBig());
                    pierCodsSet.add(piersVo.getPierNumberSmall());
                }
                List<SchedulePlanRefModel> refModelList = modelRepository.getRefModelForLedgerPlan(pierCodsSet);
                if(refModelList!=null&&!refModelList.isEmpty()){
                    for(SchedulePlanRefModel refModel:refModelList){
                        if(StringUtils.isNotBlank(refModel.getPierCode())){
                            pierCompleteDateMap.put(refModel.getPierCode(),refModel.getPlan().getCompleteDate());
                        }
                    }
                }
                List<CreateLedgerPlanVo> ledgerPlanVoList = new ArrayList<>();
                for(LedgerPiersVo piersVo:ledgerPiersVoList){
                    Date c1 = pierCompleteDateMap.get(piersVo.getPierNumberBig());
                    Date c2 = pierCompleteDateMap.get(piersVo.getPierNumberSmall());
                    if(c1!=null&&c2!=null){
                        CreateLedgerPlanVo ledgerPlanVo1 = new CreateLedgerPlanVo(piersVo.getBeamNumber());
                        if(c1.compareTo(c2)>0){
                            ledgerPlanVo1.setCompleteDate(c1);
                        }else{
                            ledgerPlanVo1.setCompleteDate(c2);
                        }
                        ledgerPlanVoList.add(ledgerPlanVo1);
                    }
                }
                if(ledgerPlanVoList.isEmpty()){
                    return;
                }
                beamFeign.createLedgerPlan(ledgerPlanVoList);
            }
        }
    }
    @Override
    public void saveRefModel(Long planId, List<SchedulePlanRefModelVo> models) {
        //删除已关联的模型信息
        planRepository.deleteRefModelByPlanId(planId);

        if (models != null && models.size() > 0) {
            //保存关联模型信息
            for (SchedulePlanRefModelVo refModel : models) {
                String pierCode = refModel.getPierCode();
                if("null".equals(pierCode)){
                    pierCode = null;
                }else{
                    pierCode = pierCode.replaceAll("\n","");
                }
                planRepository.insertRefModel(planId, refModel.getModelId(),pierCode);
            }
        }
        //生成制梁计划
        //createLedgerPlan(planRepository.findOne(planId));
    }

    @Override
    public void submitDraft(Long[] planIds) {
        if (planIds == null || planIds.length == 0) {
            return;
        }
        List<SchedulePlan> planList = planRepository.findByIdInForsubmitDraft(planIds);
        if (planList == null || planList.isEmpty()) {
            throw new ServiceErrorException("无可操作的计划任务！");
        }
        for (SchedulePlan plan : planList) {
            plan.setConfirmStatus(SchedulePlanConfirmStatus.NONE.name());
        }
        planRepository.save(planList);
    }

    private List<Long> getMyTaskPlanId() {
        String sql = "SELECT pru.schedule_plan_id FROM wm_schedule_plan_ref_user pru WHERE pru.user_id=?";
        Query contentQuery = em.createNativeQuery(sql);
        contentQuery.setParameter(1, UserUtils.getUserId());
        List result = contentQuery.getResultList();
        List<Long> planIdList = new ArrayList<>();
        for (Object row : result) {
            Long id = ((BigInteger) row).longValue();
            planIdList.add(id);
        }
        return planIdList;
    }

    @Override
    public List<SchedulePlan> queryMyTask() {
        Long myDeptId = systemFeign.fetchCurrentUserDeptId();
        return planRepository.findByDeptId(myDeptId);
    }

    @Override
    public List<SchedulePlan> queryMyTaskForReport(Long workPointId) {
        Long myDeptId = systemFeign.fetchCurrentUserDeptId();
        List<SchedulePlan> weekPlans = planRepository.findForReport(workPointId,myDeptId);
        List<SchedulePlanRefModel> refModelList = modelRepository.findCompleted();
        for(SchedulePlan weekPlan:weekPlans){
            weekPlan.setRefModelList(refModelList);
        }
        return weekPlans;
    }

    @Override
    public List<SchedulePlanForReportVo> queryMyTaskForReport() {
        Long myDeptId = systemFeign.fetchCurrentUserDeptId();
        List<SchedulePlan> planList = planRepository.findForReportTop10(myDeptId);
        List<SchedulePlanForReportVo> reportVoList = new ArrayList<>();
        if(planList!=null&&!planList.isEmpty()){
            for(SchedulePlan plan:planList){
                SchedulePlanForReportVo forReportVo = new SchedulePlanForReportVo();
                forReportVo.setPlanName(plan.getName());
                forReportVo.setPlanQuantity(plan.getQuantity());
                ScheduleReport lastReport = reportRepository.findFirstBySchedulePlanIdOrderByCreateTimeDesc(plan.getId());
                BigDecimal totalComplete = BigDecimal.ZERO;
                if(lastReport!=null){
                    totalComplete = lastReport.getTotalReportedVolume();
                }
                forReportVo.setCompleteQuantity(totalComplete);
                forReportVo.setPlanCompleteDate(plan.getCompleteDate());
                WorkPointVo workPointVo = systemFeign.fetchWorkPointById(plan.getWorkPointId());
                if(workPointVo!=null){
                    forReportVo.setWorkPointName(workPointVo.getName());
                }
                forReportVo.setPlanId(plan.getId());
                forReportVo.setQuantityUnit(plan.getQuantityUnit());
                reportVoList.add(forReportVo);
            }
        }

        return reportVoList;
    }

    @Override
    public void deleteByIdArray(Long[] ids) {
        if (ids == null || ids.length == 0) {
            throw new ServiceErrorException("删除列表为空，无法进行删除操作！");
        }
        Set<SchedulePlan> result = new HashSet<>();
        for (Long id : ids) {
            List<SchedulePlan> plans = planRepository.findToDelete(id);
            result.addAll(plans);
        }
        List<SchedulePlan> planList = planRepository.findByIdIn(Arrays.asList(ids));
        result.addAll(planList);
        for (SchedulePlan plan : result) {
            plan.setStatus(SchedulePlanStatus.DELETE.name());
        }
        planRepository.save(result);
    }

    @Override
    public List<AnalyzeCalendarVo> queryImportantPoint() {
        return new ArrayList<>();
    }

    private List<SchedulePlan> getPlanAndChildren(List<SchedulePlan> schedulePlanList) {
        if (schedulePlanList != null && schedulePlanList.size() > 0) {
            List<Long> parentIds = new ArrayList<>();
            for (SchedulePlan plan : schedulePlanList) {
                parentIds.add(plan.getId());
            }
            schedulePlanList.addAll(planRepository.findByParentIdList(parentIds));
        }
        return schedulePlanList;
    }

    /***
     * 获取工点下的实施性任务，进行进度分析
     * @param category 周计划/月计划
     */
    private List<SchedulePlan> getPlansByWorkPointId(Long id, String type, String category) {
        return getPlansByWorkPointId(id,type,category,true);
    }

    private List<Long> getMySubDeptIds(){
        List<Long> deptIds = systemFeign.subDepartId();
        Long myDeptId = systemFeign.fetchCurrentUserDeptId();
        deptIds.add(myDeptId);
        return deptIds;
    }
    private List<SchedulePlan> setPlanEditable(String category,List<SchedulePlan> planList){
        if (planList != null && !planList.isEmpty()) {
            if (SchedulePlanCategory.YEAR.name().equals(category)) {
                for (SchedulePlan plan : planList) {
                    if (SchedulePlanConfirmStatus.OK.name().equals(plan.getConfirmStatus())) {
                        plan.setEditable(false);
                    }
                }
            } else if (SchedulePlanCategory.MONTH.name().equals(category)) {
                LocalDate localDate = LocalDate.now();
                int today = localDate.getDayOfMonth();
                boolean dayNotOk = (today<20||today>25);
                for (SchedulePlan plan : planList) {
                    if (SchedulePlanConfirmStatus.OK.name().equals(plan.getConfirmStatus())&&dayNotOk) {
                        plan.setEditable(false);
                    }
                }
            }
        }
        return planList;
    }
    private List<SchedulePlan> getPlansByWorkPointId(Long id
            , String type
            , String category
            ,boolean needChildren) {
        List<SchedulePlan> planList = getPlansByWorkPointId(id,type,category,needChildren,false);
        return setPlanEditable(category,planList);
    }

    private List<SchedulePlan> getPlansByWorkPointId(Long id
            , String type
            , String category
            ,boolean needChildren
            ,boolean isDeptLimit) {

        if (ProjectType.workPoint.name().equals(type)) {
            List<SchedulePlan> schedulePlanList;
            if(isDeptLimit){
                List<Long> deptIds = getMySubDeptIds();
                schedulePlanList = planRepository.findByWorkPointIdAndCategoryAndDeptIdIn(id, category,deptIds);
            }else{
                schedulePlanList = planRepository.findByWorkPointIdAndCategory(id, category);
            }
            if(needChildren)
            {
                return getPlanAndChildren(schedulePlanList);
            }
            return schedulePlanList;
        } else if (ProjectType.line.name().equals(type)) {
            //获取线路下的进度任务
            List<WorkPointVo> workPointVos = systemFeign.fetchWorkPointByLine(id);
            List<Long> idList = new ArrayList<>();
            for (WorkPointVo workPointVo : workPointVos) {
                idList.add(workPointVo.getId());
            }
            if(idList.isEmpty()){
                return new ArrayList<>();
            }
            List<SchedulePlan> schedulePlanList;
            if(isDeptLimit){
                List<Long> deptIds = getMySubDeptIds();
                schedulePlanList = planRepository.findByWorkPointIdInAndCategoryAndDeptIdIn(idList, category,deptIds);
            }else{
                schedulePlanList = planRepository.findByWorkPointIdInAndCategory(idList, category);
            }
            if(needChildren){
                return getPlanAndChildren(schedulePlanList);
            }
            return schedulePlanList;
        }
        return new ArrayList<>();
    }

    @Override
    public List<SchedulePlan> queryByWorkPoint(Long workPointId,String category) {
        if(category==null){
            category = SchedulePlanCategory.MONTH.name();
        }
        checkCategory(category);
        List<SchedulePlan> planList = getPlansByWorkPointId(workPointId, ProjectType.workPoint.name(), category,true,true);
        return setPlanEditable(category,planList);
    }

    @Override
    public AnalyzeCompletePercentVo queryCompletePercentAnalyze(Long id, String type) {
        AnalyzeCompletePercentVo analyzeCompletePercentVo = new AnalyzeCompletePercentVo();
        List<SchedulePlan> planList = getPlansByWorkPointId(id, type, SchedulePlanCategory.MONTH.name(),false);
        BigDecimal hundred = new BigDecimal(100);
        for (SchedulePlan plan : planList) {
            if (plan.getStatus() == null) {
                continue;
            }
            //只统计开工的（在施的）
            if (SchedulePlanStatus.PROCESSING.name().equals(plan.getStatus())) {
                BigDecimal completePercent = plan.getCompletePercent();
                if (completePercent == null) {
                    completePercent = BigDecimal.ZERO;
                }
                analyzeCompletePercentVo.getCompletePercentList().add(completePercent);
                analyzeCompletePercentVo.getTaskNameList().add(plan.getName());
                analyzeCompletePercentVo.getUnCompletePercentList().add(hundred.subtract(completePercent));
            }
        }
        return analyzeCompletePercentVo;
    }


    @Override
    public List<AnalyzeVisualizationScheduleVo> queryVisualizationAnalyze(Long id, String type) {
        List<SchedulePlan> planList = getPlansByWorkPointId(id, type, SchedulePlanCategory.MONTH.name(),false);
        List<AnalyzeVisualizationScheduleVo> visualizationScheduleVoList = new ArrayList<>();
        Map<String,List<SchedulePlan>> planListMap = new HashMap<>(10);
        for (SchedulePlan plan : planList) {
            if (plan.getStatus() == null || !plan.getStatus().equals(SchedulePlanStatus.COMPLETE.name())) {
                continue;
            }
            planListMap.computeIfAbsent(plan.getName(),key-> new ArrayList<>()).add(plan);
        }
        Collection<List<SchedulePlan>> planListCollection = planListMap.values();
        for(List<SchedulePlan> planList1:planListCollection){
            if(planList1.size()>1){
                planList1.sort(Comparator.comparing(SchedulePlan::getActualCompleteDate).reversed());
            }
            SchedulePlan plan=planList1.get(0);
            AnalyzeVisualizationScheduleVo visualizationScheduleVo = new AnalyzeVisualizationScheduleVo();
            visualizationScheduleVo.setName(plan.getName());
            if (plan.getActualCompleteDate() != null) {
                visualizationScheduleVo.setActualEndDate(DateFormatUtils.format(plan.getActualCompleteDate(), "yyyy-MM-dd"));
            }
            visualizationScheduleVo.setPlanEndDate(DateFormatUtils.format(plan.getCompleteDate(), "yyyy-MM-dd"));
            visualizationScheduleVoList.add(visualizationScheduleVo);
        }
        return visualizationScheduleVoList;
    }

    private AnalyzeScheduleDetailVo getScheduleDetailVoV2(String name
            ,List<SchedulePlan> planList
            , Date now) {
        AnalyzeScheduleDetailVo scheduleDetailVo = new AnalyzeScheduleDetailVo();
        scheduleDetailVo.setSchedulePlanName(name);
        String status = SchedulePlanStatus.COMPLETE.name();
        for(SchedulePlan plan:planList){
            if(SchedulePlanStatus.PROCESSING.name().equals(plan.getStatus())){
                status = SchedulePlanStatus.PROCESSING.name();
                break;
            }else if(SchedulePlanStatus.NOT_START.name().equals(plan.getStatus())){
                status = SchedulePlanStatus.NOT_START.name();
            }
        }
        scheduleDetailVo.setSchedulePlanStatus(SchedulePlanStatus.valueOf(status).getDesc());
        BigDecimal totalPlan = BigDecimal.ZERO;
        BigDecimal totalComplete = BigDecimal.ZERO;
        BigDecimal currentWeekPlan = BigDecimal.ZERO;
        BigDecimal currentWeekComplete = BigDecimal.ZERO;
        for(SchedulePlan weekPlan:planList){
            ScheduleReport lastReport = reportRepository.findFirstBySchedulePlanIdOrderByCreateTimeDesc(weekPlan.getId());
            //本周计划量，本周完成量
            if(weekPlan.getStartDate().compareTo(now)<0
                    &&weekPlan.getCompleteDate().compareTo(now)>0){
                if(weekPlan.getQuantity()!=null){
                    currentWeekPlan = currentWeekPlan.add(weekPlan.getQuantity());
                }
                if(lastReport!=null){
                    currentWeekComplete = currentWeekComplete.add(lastReport.getTotalReportedVolume());
                }
            }
            //总计划量，总完成量
            if(weekPlan.getQuantity()!=null){
                totalPlan = totalPlan.add(weekPlan.getQuantity());
            }
            if(lastReport!=null&&lastReport.getTotalReportedVolume()!=null){
                totalComplete = totalComplete.add(lastReport.getTotalReportedVolume());
            }
        }
        //设置总完成量
        setTotal(scheduleDetailVo,totalPlan,totalComplete);
        //本周完成率
        BigDecimal currentWeekCompletePercent = BigDecimal.ZERO;
        if(currentWeekPlan.compareTo(BigDecimal.ZERO)>0){
            currentWeekCompletePercent = currentWeekComplete.divide(currentWeekPlan, 2, BigDecimal.ROUND_HALF_UP).multiply(hundred);
        }
        if (currentWeekCompletePercent.compareTo(hundred) > 0) {
            currentWeekCompletePercent = hundred;
        }
        scheduleDetailVo.setCurrentWeekCompleteQuantity(currentWeekComplete);
        scheduleDetailVo.setCurrentWeekCompletePercent(currentWeekCompletePercent);
        scheduleDetailVo.setCurrentWeekPlanQuantity(currentWeekPlan);
        return scheduleDetailVo;
    }

    private void setTotal(AnalyzeScheduleDetailVo scheduleDetailVo,BigDecimal totalPlan,BigDecimal totalComplete){
        //设置总完成量
        scheduleDetailVo.setTotalCompleteQuantity(totalComplete);
        if (totalPlan.compareTo(BigDecimal.ZERO) > 0) {
            //如果计划量大于总完成量
            if (totalPlan.compareTo(totalComplete) >= 0) {
                //设置剩余量
                scheduleDetailVo.setLastQuantity(totalPlan.subtract(totalComplete));
                //设置完成率
                scheduleDetailVo.setTotalCompletePercent(totalComplete.divide(totalPlan, 2, BigDecimal.ROUND_HALF_UP).multiply(hundred));
            } else {
                //如果总完成量大于计划量，则完成率为100%
                scheduleDetailVo.setTotalCompletePercent(hundred);
            }
        }
    }

    /***
     * 进度分析详细计算处理
     * @param schedulePlan 进度计划
     * @param calendar 日历
     * @param now 当前日期
     * @param refModelList 进度计划关联模型
     * @return 进度分析详细数据
     */
    private AnalyzeScheduleDetailVo getScheduleDetailVo(SchedulePlan schedulePlan
            , Calendar calendar
            , Date now
            , List<SchedulePlanRefModel> refModelList) {
        AnalyzeScheduleDetailVo scheduleDetailVo = new AnalyzeScheduleDetailVo();
        scheduleDetailVo.setSchedulePlanName(schedulePlan.getName());
        scheduleDetailVo.setSchedulePlanStatus(SchedulePlanStatus.valueOf(schedulePlan.getStatus()).getDesc());
        //计划工期
        int totalDay = schedulePlan.getTimeLimit();

        calendar.setTime(now);
        int nowDay = calendar.get(Calendar.DAY_OF_MONTH);
        int nowMonth = calendar.get(Calendar.MONTH);

        Date startDate = schedulePlan.getStartDate();
        Date completeDate = schedulePlan.getCompleteDate();
        calendar.setTime(startDate);
        int startMonth = calendar.get(Calendar.DAY_OF_MONTH);

        //最后一次上报记录
        List<ScheduleReport> reportList = reportRepository.findBySchedulePlanIdOrderByCreateTimeDesc(schedulePlan.getId());
        //总计划完成量
        BigDecimal totalPlan = BigDecimal.ZERO;
        for (SchedulePlanRefModel refModel : refModelList) {
            if(refModel.getQuantity()!=null){
                //累加计划完成量
                totalPlan = totalPlan.add(refModel.getQuantity());
            }
        }
        //总完成量
        BigDecimal totalComplete = scheduleDetailVo.getTotalCompleteQuantity();
        Map<Integer, List<ScheduleReport>> dayReport = new HashMap<>(10);

        //全部上报明细记录
        List<ScheduleReportDetail> reportDetailList = null;

        //上报记录遍历
        if (reportList != null && reportList.size() > 0) {
            ScheduleReport lastReport = reportList.get(0);
            //最后一次上报明细
            List<ScheduleReportDetail> lastReportDetailList = reportDetailRepository.findByReportId(lastReport.getId());
            for (ScheduleReportDetail reportDetail : lastReportDetailList) {
                //累加完成量
                totalComplete = totalComplete.add(reportDetail.getTotalReportedVolume());
            }
            List<Long> reportIds = new ArrayList<>();
            //将上报记录缓存到映射
            for (ScheduleReport report : reportList) {
                reportIds.add(report.getId());

                calendar.setTime(report.getCreateTime());
                int reportDay = calendar.get(Calendar.DAY_OF_MONTH);
                dayReport.computeIfAbsent(reportDay, k -> new ArrayList<>()).add(report);
            }

            reportDetailList = reportDetailRepository.findByReportIdIn(reportIds);
        }
        setTotal(scheduleDetailVo,totalPlan,totalComplete);

        if (nowMonth != startMonth) {
            return scheduleDetailVo;
        }

        //每天工程量
        BigDecimal dayQuantity = totalPlan.divide(new BigDecimal(totalDay), 2, BigDecimal.ROUND_HALF_UP);

        //开始时间的天日
        int startDay = calendar.get(Calendar.DAY_OF_MONTH);
        //开始时间的星期几
        int startWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        startWeek = startWeek == 0 ? 7 : startWeek;
        calendar.setTime(completeDate);
        //完成时间的天日
        int completeDay = calendar.get(Calendar.DAY_OF_MONTH);
        //第几周
        int index = 1;
        //本周
        int nowIndex = index;

        Map<Integer, List<Integer>> dayCount = new HashMap<>(4);
        for (int day = startDay; day <= completeDay; day++) {
            dayCount.computeIfAbsent(index, k -> new ArrayList<>()).add(day);
            if (nowDay == day) {
                //本周
                nowIndex = index;
            }
            //周+1
            if (startWeek % 7 == 0) {
                index++;
                startWeek = 0;
            }
            startWeek++;
        }
        //下周
        int nextIndex = nowIndex + 1;

        //本周的天日
        List<Integer> currentWeekDays = dayCount.get(nowIndex);
        if (currentWeekDays != null) {
            BigDecimal currentWeekComplete = BigDecimal.ZERO;
            for (Integer weekDay : currentWeekDays) {
                List<ScheduleReport> reports = dayReport.get(weekDay);
                if (reports != null && reportDetailList != null) {
                    for (ScheduleReport report : reports) {
                        for (ScheduleReportDetail reportDetail : reportDetailList) {
                            if (reportDetail.getReportId().equals(report.getId())) {
                                //本周完成工程量
                                currentWeekComplete = currentWeekComplete.add(reportDetail.getReportVolume());
                            }
                        }
                    }
                }
            }
            //本周计划工程量
            BigDecimal currentWeekPlan = dayQuantity.multiply(new BigDecimal(currentWeekDays.size()));
            //本周完成率
            BigDecimal currentWeekCompletePercent = currentWeekComplete.divide(currentWeekPlan, 2, BigDecimal.ROUND_HALF_UP).multiply(hundred);
            if (currentWeekCompletePercent.compareTo(hundred) > 0) {
                currentWeekCompletePercent = hundred;
            }
            scheduleDetailVo.setCurrentWeekCompleteQuantity(currentWeekComplete);
            scheduleDetailVo.setCurrentWeekCompletePercent(currentWeekCompletePercent);
            scheduleDetailVo.setCurrentWeekPlanQuantity(currentWeekPlan);
        }

        List<Integer> nextWeekDays = dayCount.get(nextIndex);
        if (nextWeekDays != null) {
            //下周计划量
            scheduleDetailVo.setNextWeekPlanQuantity(dayQuantity.multiply(new BigDecimal(nextWeekDays.size())));
        }

        return scheduleDetailVo;
    }

    @Override
    public List<AnalyzeScheduleDetailVo> queryScheduleDetailAnalyzeV2(Long id, String type) {
        List<SchedulePlan> planList = getPlansByWorkPointId(id, type, SchedulePlanCategory.WEEK.name(),false);
        List<AnalyzeScheduleDetailVo> scheduleDetailVoList = new ArrayList<>();
        final Date now = new Date();
        Map<String,List<SchedulePlan>> planListMap = new HashMap<>(10);
        for(SchedulePlan weekPlan:planList){
            planListMap.computeIfAbsent(weekPlan.getName(),k -> new ArrayList<>()).add(weekPlan);
        }
        planListMap.forEach((name,plans) ->{
            scheduleDetailVoList.add(getScheduleDetailVoV2(name,plans,now));
        });
        return scheduleDetailVoList;
    }

    @Override
    public List<AnalyzeScheduleDetailVo> queryScheduleDetailAnalyze(Long id, String type) {
        List<SchedulePlan> planList = getPlansByWorkPointId(id, type, SchedulePlanCategory.MONTH.name());
        List<AnalyzeScheduleDetailVo> scheduleDetailVoList = new ArrayList<>();
        List<Long> idList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        Date now = new Date();
        for (SchedulePlan schedulePlan : planList) {
            idList.add(schedulePlan.getId());
        }
        List<SchedulePlanRefModel> refModelList = modelRepository.findBySchedulePlanIdIn(idList);
        for (SchedulePlan plan : planList) {
            List<SchedulePlanRefModel> refModelListTemp = new ArrayList<>();
            for (SchedulePlanRefModel refModel : refModelList) {
                if (refModel.getPlan().getId().equals(plan.getId())) {
                    refModelListTemp.add(refModel);
                }
            }
            scheduleDetailVoList.add(getScheduleDetailVo(plan, calendar, now, refModelListTemp));
        }
        return scheduleDetailVoList;
    }



    @Override
    public List<AnalyzeCommonCountVo> queryScheduleDelayReasonAnalyze(Long id, String type) {
        //获取工点下的任务
        List<SchedulePlan> planList = getPlansByWorkPointId(id, type, SchedulePlanCategory.MONTH.name());
        if (planList == null || planList.isEmpty()) {
            return new ArrayList<>();
        }
        List<Long> idList = new ArrayList<>();
        for (SchedulePlan plan : planList) {
            idList.add(plan.getId());
        }
        //根据任务ID获取上报数据
        List<ScheduleReport> reportList = reportRepository.findBySchedulePlanIdIn(idList);

        idList.clear();
        for (ScheduleReport report : reportList) {
            idList.add(report.getId());
        }

        //获取所有第二级延滞因素
        List<DelayReason> secondLevelReasonList = delayReasonRepository.findByParentIdIsNotNull();
        List<String> names = new ArrayList<>();
        for (DelayReason delayReason : secondLevelReasonList) {
            names.add(delayReason.getName());
        }
        //初始化统计工具
        AnalyzeCommonCountUtil commonCountUtil = new AnalyzeCommonCountUtil(names);

        //根据上报ID获取关联延滞因素名称列表
        if (!idList.isEmpty()) {
            List<String> delayReasonNames = refDelayReasonRepository.findReasonNamesByReportIds(idList);
            for (String name : delayReasonNames) {
                commonCountUtil.increment(name);
            }
        }
        return commonCountUtil.countResult();
    }

    @Override
    public List<AnalyzeCommonCountVo> queryScheduleWarningAnalyze(Long id, String type) {
        List<SchedulePlan> planList = getPlansByWorkPointId(id, type, SchedulePlanCategory.MONTH.name());
        String red = "红色预警", yellow = "黄色预警", orange = "橙色预警";
        AnalyzeCommonCountUtil commonCountUtil = new AnalyzeCommonCountUtil(red, yellow, orange);
        Date now = new Date();
        for (SchedulePlan plan : planList) {
            int delay = getDateDayGap(plan.getCompleteDate(), now);
            //延滞21天 红色预警
            if (delay >= 21) {
                commonCountUtil.increment(red);
            }//黄色预警
            else if (delay >= 14) {
                commonCountUtil.increment(yellow);
            }//橙色预警
            else if (delay >= 7) {
                commonCountUtil.increment(orange);
            }
        }
        return commonCountUtil.countResult();
    }

    @Override
    public List<AnalyzeCommonCountVo> queryScheduleStatusAnalyze(Long id, String type) {
        List<SchedulePlan> planList = getPlansByWorkPointId(id, type, SchedulePlanCategory.MONTH.name(),false);
        String processing = "已开工", complete = "已完工", notStart = "未开工";
        AnalyzeCommonCountUtil commonCountUtil = new AnalyzeCommonCountUtil(processing, complete, notStart);
        for (SchedulePlan plan : planList) {
            if (SchedulePlanStatus.NOT_START.name().equals(plan.getStatus())) {
                commonCountUtil.increment(notStart);
            } else if (SchedulePlanStatus.PROCESSING.name().equals(plan.getStatus())) {
                commonCountUtil.increment(processing);
            } else if (SchedulePlanStatus.COMPLETE.name().equals(plan.getStatus())) {
                commonCountUtil.increment(complete);
            }
        }
        return commonCountUtil.countResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<SchedulePlanStatus, List<SchedulePlanStatusModelVo>> queryStatusModels(Long workPointId) {
        String sql = "SELECT prm.guid,prm.model_id AS modelId,prm.fid,sp.status,sp.complete_date AS completeDate FROM wm_schedule_plan_ref_model prm INNER JOIN wm_schedule_plan sp ON prm.schedule_plan_id=sp.id AND sp.status<>'DELETE' AND sp.work_point_id=?";
        Query contentQuery = em.createNativeQuery(sql);
        contentQuery.setParameter(1,workPointId);
        contentQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map> resultList = (List<Map>) contentQuery.getResultList();
        Map<SchedulePlanStatus, List<SchedulePlanStatusModelVo>> map = new HashMap<>(4);
        Date now = new Date();
        for (Map result : resultList) {
            SchedulePlanStatusModelVo modelVo = new SchedulePlanStatusModelVo();
            modelVo.setFid(Long.valueOf(String.valueOf(result.get("fid"))));
            modelVo.setStatus(String.valueOf(result.get("status")));
            modelVo.setGuid(String.valueOf(result.get("guid")));
            modelVo.setModelId(Long.valueOf(String.valueOf(result.get("modelId"))));
            Date completeDate = (Date)result.get("completeDate");
            int delay = getDateDayGap(completeDate, now);
            modelVo.setDelay(delay);
            SchedulePlanStatus status = SchedulePlanStatus.valueOf(modelVo.getStatus());
            map.computeIfAbsent(status, key -> new ArrayList<>()).add(modelVo);
        }
        return map;
    }

    @Override
    public List<SchedulePlan> queryFrontPlanList(QuerySchedulePlanFrontParams params) {
        if (params.getPlanId() != null) {
            List<SchedulePlan> planList = planRepository.findForFront(SchedulePlanCategory.MONTH.name(), params.getPlanId());
            List<SchedulePlanFront> frontList = planFrontRepository.findByScheduleId(params.getPlanId());
            if (frontList.isEmpty()) {
                return planList;
            }
            List<SchedulePlan> temp = new ArrayList<>(planList);
            for (SchedulePlan plan : planList) {
                for (SchedulePlanFront front : frontList) {
                    if (front.getScheduleFrontId().equals(plan.getId())) {
                        temp.remove(plan);
                        break;
                    }
                }
            }
            return temp;
        } else {
            return planRepository.findByCategory(SchedulePlanCategory.MONTH.name());
        }
    }

    @Override
    public List<SchedulePlanForMonitorVo> queryForMonitor() {
        List<SchedulePlan> planList = planRepository.findConfirmedByCategory(SchedulePlanCategory.MONTH.name());
        if (planList == null || planList.size() == 0) {
            return new ArrayList<>();
        }
        List<Long> idList = new ArrayList<>();
        for (SchedulePlan plan : planList) {
            idList.add(plan.getId());
        }
        List<SchedulePlanRefModel> refModelList = modelRepository.findBySchedulePlanIdIn(idList);
        List<SchedulePlanForMonitorVo> monitorVoList = new ArrayList<>();
        for (SchedulePlan plan : planList) {
            SchedulePlanForMonitorVo monitorVo = new SchedulePlanForMonitorVo();
            monitorVo.setEndDate(plan.getCompleteDate());
            monitorVo.setStartDate(plan.getStartDate());
            for (SchedulePlanRefModel refModel : refModelList) {
                if (refModel.getPlan().getId().equals(plan.getId())) {
                    monitorVo.getRefModelList().add(refModel.getModelId());
                    break;
                }
            }
            monitorVoList.add(monitorVo);
        }
        return monitorVoList;
    }

    @Override
    public List<SchedulePlanFront> querySchedulePlanFront(Long planId) {
        return planFrontRepository.findByScheduleId(planId);
    }

    @Override
    public List<SchedulePlan> queryChildren(Long parentId) {
        return planRepository.findByParentId(parentId);
    }

    private Date[] getWeekDate(Date now, int add) {
        Date week;
        if (add == 0) {
            week = now;
        } else {
            week = DateUtils.addWeeks(now, add);
        }
        Date begin = DateUtil.getMonday(week);
        Date end = DateUtil.getSunDay(week);
        return new Date[]{begin, end};
    }

    private Date[] getMonthDate(Date now, int add) {
        Date month;
        if (add == 0) {
            month = now;
        } else {
            month = DateUtils.addMonths(now, add);
        }
        Date begin = DateUtil.getMonthBiginTime(month);
        Date end = DateUtil.getMonthEndTime(month);
        return new Date[]{begin, end};
    }

    @Override
    public List<QuantityMeasurementVo> quantityMeasurement(String dateType) {
        DateRangeType rangeType;
        try {
            rangeType = DateRangeType.valueOf(dateType);
        } catch (IllegalArgumentException e) {
            throw new ServiceErrorException("请求参数错误");
        }
        Date now = new Date();
        Date[] range = null;
        switch (rangeType) {
            case PRE_MONTH:
                range = getMonthDate(now, -1);
                break;
            case PRE_3_WEEK:
                range = getWeekDate(now, -3);
                break;
            case PRE_2_WEEK:
                range = getWeekDate(now, -2);
                break;
            case PRE_WEEK:
                range = getWeekDate(now, -1);
                break;
            case CURRENT_WEEK:
                range = getWeekDate(now, 0);
                break;
            case NEXT_WEEK:
                range = getWeekDate(now, 1);
                break;
            case NEXT_2_WEEK:
                range = getWeekDate(now, 2);
                break;
            case NEXT_3_WEEK:
                range = getWeekDate(now, 3);
                break;
            case NEXT_MONTH:
                range = getMonthDate(now, 1);
                break;
            default:
                break;
        }
        List<SchedulePlan> planList = planRepository.findByCompleteDateBetween(range[0], range[1]);
        List<Long> idList = new ArrayList<>();
        for (SchedulePlan plan : planList) {
            idList.add(plan.getId());
        }
        List<SchedulePlanRefModel> refModelList = modelRepository.findBySchedulePlanIdIn(idList);
        List<QuantityMeasurementVo> measurementVoList = new ArrayList<>();
        for (SchedulePlanRefModel refModel : refModelList) {
            QuantityMeasurementVo measurementVo = new QuantityMeasurementVo();
            measurementVo.setCode("-");
            measurementVo.setName(refModel.getName());
            measurementVo.setQuantity(refModel.getQuantity());
            measurementVo.setUnit(refModel.getUnit());
            measurementVoList.add(measurementVo);
        }
        return measurementVoList;
    }


    @Autowired
    private SchedulePlanImportUtils schedulePlanImportUtils;

    @Autowired
    private ScheduleWorkPointPlanImportUtils scheduleWorkPointPlanImportUtils;

    @Autowired
    private ScheduleWorkPointPlanExportUtils scheduleWorkPointPlanExportUtils;

    private SchedulePlanCategory importPlanDataCheck(MultipartFile multipartFile, String category){
        if (multipartFile == null || multipartFile.isEmpty()) {
            throw new ServiceErrorException("无文件可处理");
        }

        try {
            return SchedulePlanCategory.valueOf(category);
        } catch (IllegalArgumentException e) {
            throw new ServiceErrorException("不支持的计划类型");
        }
    }
    @Override
    public List<SchedulePlanImportVo> importPlanData(MultipartFile multipartFile, String category) {
        SchedulePlanCategory categoryEnum= importPlanDataCheck(multipartFile,category);
        List<WorkPointVo> workPointList = systemFeign.fetchWorkPointByLine(2L);
        List<SchedulePlanImportVo> plans = schedulePlanImportUtils.parseExcel(ExcelImport.generateWorkbook(multipartFile),categoryEnum);
        List<SchedulePlan> ablePlanList = new ArrayList<>();
        Long currentUserDeptId = systemFeign.fetchCurrentUserDeptId();
        for(SchedulePlanImportVo importVo:plans){
            for(WorkPointVo workPointVo:workPointList){
                if(workPointVo.getName().equals(importVo.getName())){
                    importVo.setWorkPointId(workPointVo.getId());
                    setChildrenIds(importVo,workPointVo.getId(),categoryEnum==SchedulePlanCategory.MONTH,ablePlanList,currentUserDeptId);
                }
            }
        }
        planRepository.save(ablePlanList);
        return plans;
    }

    @Override
    public List<SchedulePlanImportVo> importWorkPointPlanData(MultipartFile multipartFile, Long workPointId, String category) {
        SchedulePlanCategory categoryEnum= importPlanDataCheck(multipartFile,category);
        List<SchedulePlanImportVo> plans = scheduleWorkPointPlanImportUtils.parseExcel(ExcelImport.generateWorkbook(multipartFile),categoryEnum);
        List<SchedulePlan> ablePlanList = new ArrayList<>();
        Long currentUserDeptId = systemFeign.fetchCurrentUserDeptId();
        for(SchedulePlanImportVo importVo:plans){
            importVo.setWorkPointId(workPointId);
            setChildrenIds(importVo,workPointId,categoryEnum==SchedulePlanCategory.MONTH,ablePlanList,currentUserDeptId);
        }
        planRepository.save(ablePlanList);
        return plans;
    }

    @Override
    public void exportWorkPointPlanData(Long workPointId, String category,HttpServletResponse response) {
        SchedulePlanCategory planCategory = checkCategory(category);
        List<Long> subDepartId = getMySubDeptIds();
        List<SchedulePlan> planList = planRepository.findAllByCategoryAndWorkPointIdAndDeptIdIn(category,workPointId,subDepartId);
        WorkPointVo workPointVo = systemFeign.fetchWorkPointById(workPointId);
        try {
            scheduleWorkPointPlanExportUtils.export(planList,workPointVo.getName(),planCategory,response);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceErrorException("导出文件生成异常");
        }
    }

    private SchedulePlan importPlanTrans(SchedulePlanImportVo child,boolean needParentId,Long currentUserDeptId){
        SchedulePlan parentPlan=null;
        if(needParentId){
            parentPlan = planRepository.findForImportParentId(child.getName(),child.getWorkPointId(),SchedulePlanCategory.YEAR.name());
            if(parentPlan!=null){
                child.setParentId(parentPlan.getId());
            }
        }
        SchedulePlan newPlan = new SchedulePlan();
        newPlan.setWorkPointId(child.getWorkPointId());
        newPlan.setCreateUserId(UserUtils.getUserId());
        newPlan.setStatus(SchedulePlanStatus.NOT_START.name());
        newPlan.setConfirmStatus(SchedulePlanConfirmStatus.DRAFT.name());
        newPlan.setCompleteDate(child.getCompleteDate());
        newPlan.setStartDate(child.getStartDate());
        newPlan.setTimeLimit(child.getDays());
        newPlan.setCategory(child.getCategory());
        newPlan.setName(child.getName());
        newPlan.setQuantity(child.getQuantity());
        newPlan.setParentId(child.getParentId());
        newPlan.setDeptId(currentUserDeptId);
        int size = countPlan(newPlan);
        String code;
        if(parentPlan!=null){
            code = parentPlan.getCode().concat(SchedulePlan.CODE_SPLIT).concat(String.valueOf(size + 1));
        }else{
            code = String.valueOf(size+1);
        }
        newPlan.setCode(code);
        return newPlan;
    }
    private void setChildrenIds(SchedulePlanImportVo parent, Long workPointId, boolean needParentId, List<SchedulePlan> ablePlanList,Long currentUserDeptId){
        if(parent!=null&&!parent.getChildren().isEmpty()){
            for(SchedulePlanImportVo child : parent.getChildren()){
                child.setWorkPointId(workPointId);
                if(!child.getChildren().isEmpty()){
                    setChildrenIds(child,workPointId,needParentId,ablePlanList,currentUserDeptId);
                }else {
                    ablePlanList.add(importPlanTrans(child,needParentId,currentUserDeptId));
                }
            }
        }else if(parent!=null&&parent.getChildren().isEmpty()){
            ablePlanList.add(importPlanTrans(parent,needParentId,currentUserDeptId));
        }
    }

    private SchedulePlanCategory checkCategory(String category) {
        SchedulePlanCategory planCategory;
        try {
            planCategory = SchedulePlanCategory.valueOf(category);
        } catch (Exception e) {
            throw new ServiceErrorException("不支持的计划类型");
        }
        return planCategory;
    }

    private List<SchedulePlan> getByCategory(boolean isMine, String category, Long parentId) {
        Long myDeptId = systemFeign.fetchCurrentUserDeptId();
        if (isMine) {
            if (parentId == null) {
                return planRepository.findByCategoryAndDeptId(category, myDeptId);
            } else {
                return planRepository.findByCategoryAndParentIdAndDeptId(category, parentId, myDeptId);
            }
        } else {
            List<Long> deptIds = systemFeign.subDepartId();
            deptIds.add(myDeptId);
            if (parentId == null) {
                return planRepository.findAllByCategoryAndDeptIdIn(category,deptIds);
            } else {
                return planRepository.findAllByCategoryAndParentIdAndDeptIdIn(category, parentId,deptIds);
            }
        }
    }

    private List<SchedulePlan> getByCategoryAndWorkPoint(boolean isMine, String category, Long workPointId, Long parentId) {
        Long myDeptId = systemFeign.fetchCurrentUserDeptId();
        if (isMine) {
            if (parentId == null) {
                return planRepository.findByCategoryAndDeptIdAndWorkPointId(category, myDeptId, workPointId);
            } else {
                return planRepository.findByCategoryAndDeptIdAndWorkPointIdAndParentId(category, myDeptId, workPointId, parentId);
            }
        } else {
            List<Long> deptIds = systemFeign.subDepartId();
            deptIds.add(myDeptId);
            if (parentId == null) {
                return planRepository.findAllByCategoryAndWorkPointIdAndDeptIdIn(category, workPointId,deptIds);
            } else {
                return planRepository.findAllByCategoryAndWorkPointIdAndParentIdAndDeptIdIn(category, workPointId, parentId,deptIds);
            }
        }
    }

    @Override
    public List<SchedulePlan> queryMyTaskByCategoryAndWorkPoint(String category, Long workPointId, Long parentId) {
        checkCategory(category);
        if (workPointId <= 0) {
            return getByCategory(true, category, parentId);
        }
        return getByCategoryAndWorkPoint(true, category, workPointId, parentId);
    }

    private final String YES = "Y";
    @Override
    public List<SchedulePlan> queryAllByCategoryAndWorkPoint(String category, Long workPointId, Long parentId,String forParent) {
        List<SchedulePlan> planList;
        SchedulePlanCategory planCategory = checkCategory(category);
        if (workPointId <= 0) {
            planList = getByCategory(false, category, parentId);
        }else{
            planList = getByCategoryAndWorkPoint(false, category, workPointId, parentId);
        }
        if(YES.equals(forParent)){
            if(planList!=null&&!planList.isEmpty()){
                int gapDays = 7;
                if(planCategory==SchedulePlanCategory.YEAR){
                    gapDays = 30;
                }
                LocalDateTime now = LocalDateTime.now();
                Date nowDate = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
                LocalDateTime gapNow = now.plusDays(gapDays);
                Date gapNowDate = Date.from(gapNow.atZone(ZoneId.systemDefault()).toInstant());
                List<Long> parentPlanId = new ArrayList<>();
                List<SchedulePlan> availablePlanList = new ArrayList<>();
                for(SchedulePlan plan:planList){
                    //只取当前日期在任务开始完成时间范围内的
                    if(plan.getStartDate().compareTo(nowDate)<0
                            &&plan.getCompleteDate().compareTo(gapNowDate)>0){
                        parentPlanId.add(plan.getId());
                        availablePlanList.add(plan);
                    }
                }
                if(availablePlanList.isEmpty()){
                    return availablePlanList;
                }
                List<SchedulePlan> childrenList = planRepository.findByParentIdList(parentPlanId);
                Map<Long,List<SchedulePlan>> childrenListMap = new HashMap<>(planList.size());
                for(SchedulePlan child:childrenList){
                    childrenListMap.computeIfAbsent(child.getParentId(),k -> new ArrayList<>()).add(child);
                }
                for(SchedulePlan plan:availablePlanList){
                    if(plan.getQuantity()==null||plan.getQuantity().compareTo(BigDecimal.ZERO)==0){
                        continue;
                    }
                    List<SchedulePlan> children = childrenListMap.get(plan.getId());
                    if(children==null||children.isEmpty()){
                        plan.setLastQuantity(plan.getQuantity());
                    }else{
                        BigDecimal usedQuantity = BigDecimal.ZERO;
                        for(SchedulePlan child:children){
                            if(child.getQuantity()!=null)
                            {
                                usedQuantity = usedQuantity.add(child.getQuantity());
                            }
                        }
                        plan.setLastQuantity(plan.getQuantity().subtract(usedQuantity));
                    }
                }
                return setPlanEditable(category,availablePlanList);
            }
        }
        return setPlanEditable(category,planList);
    }

    @Override
    public List<SchedulePlanRefModelVo> getAllRefModels() {
        return modelRepository.getAllModelVo();
    }

    @Override
    public SchedulePlan getById(Long id) {
        return planRepository.findOne(id);
    }

    @Override
    public List<SchedulePlanCompareMonitorVo> queryPlansForCompareMonitor(Long workPointId) {
        List<SchedulePlan> monthPlans = queryByWorkPoint(workPointId,SchedulePlanCategory.MONTH.name());
        List<SchedulePlanCompareMonitorVo> compareMonitorVoList = new ArrayList<>();
        if(monthPlans!=null&&!monthPlans.isEmpty()){
            for(SchedulePlan plan:monthPlans){
                List<SchedulePlanRefModel> refModels = plan.getRefModelList();
                if(refModels!=null&&!refModels.isEmpty()){
                    SchedulePlanCompareMonitorVo compareMonitorVo = new SchedulePlanCompareMonitorVo();
                    if(plan.getActualCompleteDate()!=null){
                        if(plan.getCompleteDate().compareTo(plan.getActualCompleteDate())>0){
                            compareMonitorVo.setType(1);
                        }else if(plan.getCompleteDate().compareTo(plan.getActualCompleteDate())<0){
                            compareMonitorVo.setType(2);
                        }
                        compareMonitorVo.setCompleteDateStr(DateFormatUtils.format(plan.getActualCompleteDate(),"yyyy-MM-dd"));
                    }else{
                        compareMonitorVo.setCompleteDateStr(DateFormatUtils.format(plan.getCompleteDate(),"yyyy-MM-dd"));
                    }
                    compareMonitorVo.setStartDateStr(DateFormatUtils.format(plan.getStartDate(),"yyyy-MM-dd"));
                    for(SchedulePlanRefModel refModel:refModels){
                        SchedulePlanCompareMonitorVo.RefModel model = new SchedulePlanCompareMonitorVo.RefModel(refModel.getModelId());
                        compareMonitorVo.getRefModelList().add(model);
                    }
                    compareMonitorVoList.add(compareMonitorVo);
                }
            }
        }
        return compareMonitorVoList;
    }

    @Override
    public void updatePlanDate(Long id, Date start, Date end) {
        if(id==null||start==null||end==null){
            throw new ServiceErrorException("参数不完整，无法修改！");
        }
        SchedulePlan thisPlan = planRepository.findOne(id);
        if (thisPlan == null) {
            throw new ServiceErrorException("进度计划不存在");
        }
        SchedulePlanCategory planCategory = SchedulePlanCategory.valueOf(thisPlan.getCategory());
        if (SchedulePlanCategory.MONTH == planCategory) {
            if (SchedulePlanConfirmStatus.OK.name().equals(thisPlan.getConfirmStatus())) {
                LocalDate localDate = LocalDate.now();
                int day = localDate.getDayOfMonth();
                if (day < 20 || day > 25) {
                    throw new ServiceErrorException("月计划只可在20-25号进行修改！");
                }
            }
        } else if (SchedulePlanCategory.YEAR == planCategory) {
            if (SchedulePlanConfirmStatus.OK.name().equals(thisPlan.getConfirmStatus())) {
                throw new ServiceErrorException("审核通过的年计划不可修改！");
            }
        }
        List<SchedulePlan> children = planRepository.findByParentId(id);
        if(children!=null&&!children.isEmpty()){
            for(SchedulePlan child : children){
                if(child.getStartDate().compareTo(start)<0
                        ||child.getCompleteDate().compareTo(end)>0){
                    throw new ServiceErrorException("该进度任务与子任务的开始结束时间["
                            +DateFormatUtils.format(child.getStartDate(),"yyyy-MM-dd")
                            +"至"
                            +DateFormatUtils.format(child.getCompleteDate(),"yyyy-MM-dd")
                            +"]不匹配，请重新修改");
                }
            }
        }
        planRepository.updatePlanDate(id,start,end);
    }
}
