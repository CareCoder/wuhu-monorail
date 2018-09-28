package net.cdsunrise.wm.schedule.service.impl;

import net.cdsunrise.wm.base.exception.ServiceErrorException;
import net.cdsunrise.wm.base.util.FileUtils;
import net.cdsunrise.wm.base.web.User;
import net.cdsunrise.wm.base.web.UserUtils;
import net.cdsunrise.wm.schedule.entity.*;
import net.cdsunrise.wm.schedule.enums.SchedulePlanFrontRel;
import net.cdsunrise.wm.schedule.enums.SchedulePlanStatus;
import net.cdsunrise.wm.schedule.enums.ScheduleReportType;
import net.cdsunrise.wm.schedule.feign.SystemFeign;
import net.cdsunrise.wm.schedule.repository.*;
import net.cdsunrise.wm.schedule.service.ScheduleReportService;
import net.cdsunrise.wm.schedule.vo.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/***
 * @author gechaoqing
 * 进度上报
 */
@Service
public class ScheduleReportServiceImpl implements ScheduleReportService {
    @PersistenceContext
    private EntityManager em;
    private String attachmentFilePath = "planReport/";

    @Value("${uploadPath}")
    private String uploadPath;
    @Value("${fileUrl}")
    private String fileUrl;
    private String urlSplit = "/";
    private BigDecimal hundred = new BigDecimal(100);
    private SchedulePlanRepository planRepository;
    private ScheduleReportRepository reportRepository;
    private ScheduleReportDetailRepository reportDetailRepository;
    private SystemFeign systemFeign;
    private SchedulePlanModelRepository modelRepository;
    private ScheduleReportRefDelayReasonRepository delayReasonRepository;

    public ScheduleReportServiceImpl(SystemFeign systemFeign
            ,SchedulePlanRepository planRepository
            , ScheduleReportRepository reportRepository
            , ScheduleReportDetailRepository reportDetailRepository
            ,SchedulePlanModelRepository modelRepository
            ,ScheduleReportRefDelayReasonRepository delayReasonRepository) {
        this.planRepository = planRepository;
        this.reportRepository = reportRepository;
        this.reportDetailRepository = reportDetailRepository;
        this.systemFeign = systemFeign;
        this.modelRepository = modelRepository;
        this.delayReasonRepository = delayReasonRepository;
    }

    /***
     * 判断是否是我的任务
     * @param plan
     */
    private void checkIsMyTask(SchedulePlan plan) {
        if (plan == null) {
            throw new ServiceErrorException("任务不存在");
        }
//        Long userId = UserUtils.getUserId();
        UserVo userVo = systemFeign.fetchCurrentUser();
        if(!plan.getDeptId().equals(userVo.getDeptId())){
            throw new ServiceErrorException("无权限操作该任务");
        }

       /* Query query = em.createNativeQuery("SELECT count(1) FROM wm_schedule_plan_ref_user ru WHERE ru.schedule_plan_id=? AND ru.user_id=?");
        query.setParameter(1, plan.getId());
        query.setParameter(2, userId);
        Object count = query.getSingleResult();
        Integer size = Integer.valueOf(count.toString());
        if (size == 0) {
            throw new ServiceErrorException("无权限操作该任务");
        }*/
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void report(ScheduleReportCollectionVo report,MultipartFile file) {
        Long planId = report.getPlanId();
        if(planId==null||planId==0){
            throw new ServiceErrorException("进度任务数据唯一标识错误！");
        }
        List<ScheduleReportQuantityVo> reportVoList = report.getReport();
        List<ScheduleReportDelayReasonVo> delayReasonVoList = report.getDelayReasonList();
        /*boolean notReportAble = (reportVoList == null || reportVoList.size() == 0) && report.getFile() == null;
        if (notReportAble) {
            throw new ServiceErrorException("无工程量数据或附件，上报失败");
        }*/

        SchedulePlan plan = planRepository.findOne(planId);
        checkIsMyTask(plan);
        //已开工
        plan.setStatus(SchedulePlanStatus.PROCESSING.name());
        //如果是上报且完成则设置状态为上报完成待审核
        if(ScheduleReportType.valueOf(report.getReportType())==ScheduleReportType.REPORT_AND_COMPLETE){
            plan.setStatus(SchedulePlanStatus.AUDITING.name());
            plan.setActualCompleteDate(new Date());
        }
        List<SchedulePlanRefModelVo> refModelVoList = report.getModelList();
        if(refModelVoList!=null&&!refModelVoList.isEmpty()){
            for(SchedulePlanRefModelVo refModel:refModelVoList){
                modelRepository.updateRefModelComplete(refModel.getModelId());
            }
        }
        String reportAttachmentUrl = null;
        String reportAttachmentOriName = null;
        if (file != null && !file.isEmpty()) {
            try {
                //处理上传附件
                List<String> fileNames = FileUtils.renameUpload(uploadPath + attachmentFilePath, file);
                if (fileNames.size() > 0) {
                    String fileStr = file.getOriginalFilename();
                    //上传文件原始名称
                    String oriFileName = fileStr.substring(fileStr.lastIndexOf('\\') + 1);
                    String fileName = fileNames.get(0);
                    //设置上传文件的访问地址
                    if (!fileUrl.substring(fileUrl.length() - 1).equals(urlSplit)) {
                        reportAttachmentUrl = (fileUrl + urlSplit + attachmentFilePath + fileName);
                    } else {
                        reportAttachmentUrl = (fileUrl + attachmentFilePath + fileName);
                    }
                    reportAttachmentOriName = (oriFileName);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //新增进度上报记录
        ScheduleReport scheduleReport = new ScheduleReport();
        scheduleReport.setAttachmentOriName(reportAttachmentOriName);
        scheduleReport.setAttachmentUrl(reportAttachmentUrl);
        scheduleReport.setSchedulePlanId(planId);
        scheduleReport.setReportUserId(UserUtils.getUserId());
        scheduleReport = reportRepository.save(scheduleReport);
        //上报延滞因素
        reportDelayReason(delayReasonVoList,scheduleReport);
        //上报工程量明细
        reportQuantities(reportVoList,plan,scheduleReport);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void reportSubmit(ScheduleReportSubmitVo submitVo) {
        Long planId = submitVo.getPlanId();
        if(planId==null||planId==0){
            throw new ServiceErrorException("进度任务数据唯一标识错误！");
        }
        SchedulePlan plan = planRepository.findOne(planId);
        checkIsMyTask(plan);

        //已开工
        plan.setStatus(SchedulePlanStatus.PROCESSING.name());
        //如果是上报且完成则设置状态为上报完成待审核
        if(ScheduleReportType.valueOf(submitVo.getReportType())==ScheduleReportType.REPORT_AND_COMPLETE){
            plan.setStatus(SchedulePlanStatus.COMPLETE.name());
            plan.setCompletePercent(hundred);
            plan.setActualCompleteDate(new Date());
            if(submitVo.getQuantity()==null){
                //获取父级任务，并更新完成率
                String planCode = plan.getCode();
                List<SchedulePlan> parentPlanList = new ArrayList<>();
                setParentPlanCompletePercent(planCode, parentPlanList);
                if (!parentPlanList.isEmpty()) {
                    planRepository.save(parentPlanList);
                }
            }
        }
        List<SchedulePlanRefModelVo> refModelVoList = submitVo.getModelList();
        if(refModelVoList!=null&&!refModelVoList.isEmpty()){
            for(SchedulePlanRefModelVo refModel:refModelVoList){
                modelRepository.updateRefModelComplete(refModel.getModelId());
            }
        }

        //新增进度上报记录
        ScheduleReport scheduleReport = new ScheduleReport();
        scheduleReport.setSchedulePlanId(planId);
        scheduleReport.setReportComment(submitVo.getComment());
        scheduleReport.setReportUserId(UserUtils.getUserId());
        scheduleReport.setReportVolume(submitVo.getQuantity());
        reportPlanQuantity(submitVo.getQuantity(),plan,scheduleReport);
        List<ScheduleReportDelayReasonVo> delayReasonVoList = submitVo.getDelayReasonList();
        //上报延滞因素
        reportDelayReason(delayReasonVoList,scheduleReport);
    }

    /***
     * 延滞因素上报
     * @param delayReasonVoList 延滞因素信息列表
     * @param scheduleReport 新的上报记录
     */
    private void reportDelayReason(List<ScheduleReportDelayReasonVo> delayReasonVoList,ScheduleReport scheduleReport){
        if(delayReasonVoList!=null&&!delayReasonVoList.isEmpty()){
            List<ScheduleReportRefDelayReason> reportRefDelayReasons = new ArrayList<>();
            for(ScheduleReportDelayReasonVo delayReasonVo:delayReasonVoList){
                ScheduleReportRefDelayReason reportRefDelayReason = new ScheduleReportRefDelayReason();
                reportRefDelayReason.setDelayComment(delayReasonVo.getDelayComment());
                reportRefDelayReason.setDelayId(delayReasonVo.getDelayId());
                reportRefDelayReason.setDelaySolution(delayReasonVo.getDelaySolution());
                reportRefDelayReason.setReportId(scheduleReport.getId());
                reportRefDelayReasons.add(reportRefDelayReason);
            }
            delayReasonRepository.save(reportRefDelayReasons);
        }
    }

    private void reportPlanQuantity(BigDecimal reportQuantity, SchedulePlan plan, ScheduleReport scheduleReport) {
        if(reportQuantity!=null){
            BigDecimal completeTotal = BigDecimal.ZERO;
            BigDecimal planTotal = plan.getQuantity();
            //获取对应工程量清单最后一次的上报数据
            ScheduleReport lastReport = reportRepository.findFirstBySchedulePlanIdOrderByCreateTimeDesc(plan.getId());
            if(lastReport!=null){
                completeTotal = lastReport.getTotalReportedVolume();
                scheduleReport.setTotalReportedVolume(lastReport.getTotalReportedVolume().add(scheduleReport.getReportVolume()));
            }else{
                completeTotal=scheduleReport.getReportVolume();
                scheduleReport.setTotalReportedVolume(scheduleReport.getReportVolume());
            }
            //完成率
            BigDecimal completePercent = BigDecimal.ZERO;
            if (planTotal.compareTo(BigDecimal.ZERO) > 0) {
                completePercent =completeTotal.divide(planTotal, 4, BigDecimal.ROUND_HALF_UP).multiply(hundred);
            }
            //计算进度计划完成率
            plan.setCompletePercent(completePercent);
            planRepository.save(plan);
            scheduleReport.setCompletePercent(completePercent);
            reportRepository.save(scheduleReport);

            //获取父级任务，并更新完成率
            String planCode = plan.getCode();
            List<SchedulePlan> parentPlanList = new ArrayList<>();
            setParentPlanCompletePercent(planCode, parentPlanList);
            if (!parentPlanList.isEmpty()) {
                planRepository.save(parentPlanList);
            }
        }
    }

    /***
     * 工程量明细上报
     * @param reportVoList 工程量明细列表
     * @param plan 进度计划
     * @param scheduleReport 新的上报记录
     */
    private void reportQuantities(List<ScheduleReportQuantityVo> reportVoList,SchedulePlan plan,ScheduleReport scheduleReport){
        //上报工程量明细
        if (reportVoList != null) {
            Long planId = plan.getId();
            //总完成量
            BigDecimal completeTotal = BigDecimal.ZERO;
            //总计划量
            BigDecimal planTotal = BigDecimal.ZERO;
            //获取对应工程量清单最后一次的上报数据
            ScheduleReport lastReport = reportRepository.findFirstBySchedulePlanIdOrderByCreateTimeDesc(planId);
            List<ScheduleReportDetail> lastReportDetailList = null;
            if (lastReport != null) {
                lastReportDetailList = reportDetailRepository.findByReportId(lastReport.getId());
            }
            List<SchedulePlanRefModel> refModelList = plan.getRefModelList();
            //= modelRepository.findBySchedulePlanId(planId);

            //进度上报明细
            for (ScheduleReportQuantityVo reportVo : reportVoList) {
                //计划工程量
                BigDecimal planQuantity = BigDecimal.ZERO;
                //完成率
                BigDecimal completePercent = BigDecimal.ZERO;
                for(SchedulePlanRefModel refModelInList:refModelList){
                    if(reportVo.getProjectVolumeId().equals(refModelInList.getModelId())){
                        planQuantity = refModelInList.getQuantity();
                        break;
                    }
                }
                ScheduleReportDetail reportDetail = new ScheduleReportDetail();
                reportDetail.setReportId(scheduleReport.getId());
                //上报的工程量数据
                reportDetail.setProjectVolumeId(reportVo.getProjectVolumeId());
                reportDetail.setReportVolume(reportVo.getReportVolume());
                //总的已上报工程量是否已设置
                boolean isTotalReportedSet = false;
                //设置总的已上报工程量
                if (lastReportDetailList != null && !lastReportDetailList.isEmpty()) {
                    for (ScheduleReportDetail lastReportDetail : lastReportDetailList) {
                        if (lastReportDetail.getProjectVolumeId().equals(reportVo.getProjectVolumeId())) {
                            reportDetail.setTotalReportedVolume(lastReportDetail.getTotalReportedVolume().add(reportVo.getReportVolume()));
                            isTotalReportedSet = true;
                            break;
                        }
                    }
                }
                //如果总的已上报工程量没有设置，则表示是第一次上报，将本次上报的量设置为总的已上报的量
                if (!isTotalReportedSet) {
                    reportDetail.setTotalReportedVolume(reportVo.getReportVolume());
                }

                if(planQuantity.compareTo(BigDecimal.ZERO)>0){
                    completePercent = reportDetail.getTotalReportedVolume().divide(planQuantity, 4, BigDecimal.ROUND_HALF_UP).multiply(hundred);
                }
                if (completePercent.compareTo(hundred) > 0) {
                    completePercent = hundred;
                }
                //计算当前工程量完成率
                reportDetail.setCompletePercent(completePercent);

                //总的计划工程量计算
                planTotal = planTotal.add(planQuantity);
                //总的已完成工程量计算
                completeTotal = completeTotal.add(reportDetail.getTotalReportedVolume());

                reportDetailRepository.save(reportDetail);
            }
            BigDecimal completePercent = completeTotal.divide(planTotal, 4, BigDecimal.ROUND_HALF_UP).multiply(hundred);
            if (completePercent.compareTo(hundred) > 0) {
                completePercent = hundred;
            }
            //计算进度计划完成率
            plan.setCompletePercent(completePercent);
            planRepository.save(plan);
            scheduleReport.setCompletePercent(completePercent);
            reportRepository.save(scheduleReport);

            //获取父级任务，并更新完成率
            String planCode = plan.getCode();
            List<SchedulePlan> parentPlanList = new ArrayList<>();
            setParentPlanCompletePercent(planCode, parentPlanList);
            if (!parentPlanList.isEmpty()) {
                planRepository.save(parentPlanList);
            }
        }
    }

    /****
     * 工程量上报后，
     * 设置父级任务的完成率
     */
    private void setParentPlanCompletePercent(String code
            , List<SchedulePlan> parentPlanList) {
        if (code.contains(SchedulePlan.CODE_SPLIT)) {
            String parentCode = code.substring(0, code.lastIndexOf(SchedulePlan.CODE_SPLIT));
            SchedulePlan parentPlan = planRepository.findByCode(code);
            List<SchedulePlan> subPlanList = planRepository.findByParentId(parentPlan.getId());
            if(subPlanList.isEmpty()){
                return;
            }
            BigDecimal complete = BigDecimal.ZERO;
            BigDecimal count = BigDecimal.ZERO;
            for (SchedulePlan subPlan : subPlanList) {
                BigDecimal completePercent = subPlan.getCompletePercent();
                if (completePercent == null) {
                    completePercent = BigDecimal.ZERO;
                }
                complete = complete.add(completePercent);
                count = count.add(hundred);
            }
            BigDecimal completePercent = complete.divide(count, 2, BigDecimal.ROUND_HALF_UP).multiply(hundred);
            if(completePercent.compareTo(hundred)<0){
                //开工
                parentPlan.setStatus(SchedulePlanStatus.PROCESSING.name());
            }else{
                parentPlan.setStatus(SchedulePlanStatus.COMPLETE.name());
            }
            parentPlan.setCompletePercent(completePercent);
            parentPlanList.add(parentPlan);
            setParentPlanCompletePercent(parentCode, parentPlanList);
        }
    }


    /***
     * 获取两个日期的间隔天数
     * @param sDate 开始时间
     * @param eDate 结束时间
     * @return 天数
     */
    private int getDateDayGap(Date sDate,Date eDate){
        return (int)((eDate.getTime()-sDate.getTime())/(1000L * 3600L *24L));
    }

    @Override
    public void completePlans(Long[] planId){
        if (planId == null || planId.length == 0) {
            throw new ServiceErrorException("无可操作的计划任务！");
        }
        List<SchedulePlan> planList = planRepository.findByIdInForReport(planId);
        if(planList==null||planList.isEmpty()){
            throw new ServiceErrorException("无可操作的计划任务！");
        }
        for (SchedulePlan plan : planList) {
            checkIsMyTask(plan);
            plan.setStatus(SchedulePlanStatus.COMPLETE.name());
            plan.setActualCompleteDate(new Date());
        }
        planRepository.save(planList);
    }

    @Deprecated
    public void complete(Long[] planId) {
        if (planId == null || planId.length == 0) {
            throw new ServiceErrorException("无可操作的计划任务！");
        }
        List<SchedulePlan> planList = planRepository.findByIdInForReport(planId);
        if(planList==null||planList.isEmpty()){
            throw new ServiceErrorException("无可操作的计划任务！");
        }
        String sql = "SELECT prf.front_val,prf.front_rel,sp.start_date,sp.complete_date,sp.status,sp.name " +
                "FROM wm_schedule_plan_ref_front prf " +
                "INNER JOIN wm_schedule_plan sp ON prf.schedule_front_id=sp.id " +
                "AND prf.schedule_id=? " +
                "AND (prf.front_rel=? OR prf.front_rel=?) " +
                "AND sp.status<>'DELETE'";
        Query contentQuery = em.createNativeQuery(sql);
        for (SchedulePlan plan : planList) {
            checkIsMyTask(plan);
            contentQuery.setParameter(1,plan.getId())
                    .setParameter(2,SchedulePlanFrontRel.SF)
                    .setParameter(3,SchedulePlanFrontRel.FF);
            List resultList = contentQuery.getResultList();
            if(resultList!=null&&!resultList.isEmpty()){
                for(Object row:resultList){
                    Object[] cells = (Object[]) row;
                    Integer frontVal = (Integer)cells[0];
                    String frontRel = (String)cells[1];
                    Date frontStartDate = (Date)cells[2];
                    Date frontCompleteDate = (Date)cells[3];
                    String status = (String)cells[4];
                    String name = (String)cells[5];
                    int dayGap;
                    // 前置条件判断，符合条件则可上报完成，否则不可
                    if(SchedulePlanFrontRel.valueOf(frontRel)==SchedulePlanFrontRel.FF
                            &&SchedulePlanStatus.valueOf(status)==SchedulePlanStatus.COMPLETE){
                        dayGap = getDateDayGap(frontCompleteDate,new Date());
                        if(frontVal>dayGap){
                            throw new ServiceErrorException("前置任务【"+name+"】已完成，但间隔天数不正确，当前任务不可完成");
                        }
                    }else if(SchedulePlanFrontRel.valueOf(frontRel)==SchedulePlanFrontRel.SF
                            &&SchedulePlanStatus.valueOf(status)==SchedulePlanStatus.PROCESSING){
                        dayGap = getDateDayGap(frontStartDate,new Date());
                        if(frontVal>dayGap){
                            throw new ServiceErrorException("前置任务【"+name+"】已开始，但间隔天数不正确，当前任务不可完成");
                        }
                    }else{
                        throw new ServiceErrorException("前置任务【"+name+"】与当前任务的前置条件不满足，不可完成");
                    }
                }
            }
            plan.setStatus(SchedulePlanStatus.AUDITING.name());
            plan.setActualCompleteDate(new Date());
        }
        planRepository.save(planList);
    }

    @Override
    public List<ScheduleReportRefModelVo> getRefModelForReport(Long planId) {
         List<ScheduleReportRefModelVo> reportRefModelVoList = new ArrayList<>();
         //获取关联模型列表
         List<SchedulePlanRefModel> modelList = modelRepository.findBySchedulePlanId(planId);
         //获取最后一次上报记录
         ScheduleReport report = reportRepository.findFirstBySchedulePlanIdOrderByCreateTimeDesc(planId);
         //根据最后一次上报记录获取上报明细
         List<ScheduleReportDetail> reportDetailList = reportDetailRepository.findByReportId(report.getId());
         if(modelList!=null&&modelList.size()>0){
             for(SchedulePlanRefModel refModel:modelList){
                 ScheduleReportRefModelVo refModelVo = new ScheduleReportRefModelVo();
                 refModelVo.setName(refModel.getName());
                 refModelVo.setPlanQuantity(refModel.getQuantity());
                 refModelVo.setProjectVolumeId(refModel.getModelId());
                 //遍历上报明细设置总上报工程量
                 for(ScheduleReportDetail reportDetail:reportDetailList){
                     if(reportDetail.getProjectVolumeId().equals(refModel.getModelId())){
                         refModelVo.setReportedQuantity(reportDetail.getTotalReportedVolume());
                         break;
                     }
                 }
                 reportRefModelVoList.add(refModelVo);
             }
         }
         return reportRefModelVoList;
    }


    @Override
    public Map<Long,List<ScheduleReportVo>> getReportHis(List<Long> planIdList){
        Map<Long,List<ScheduleReportVo>> reportHisVoMap = new HashMap<>(planIdList.size());
        // 根据任务ID集合获取上报记录列表
        List<ScheduleReport> reportList = reportRepository.findBySchedulePlanIdIn(planIdList);
        List<Long> idList = new ArrayList<>();
        StringBuilder inList = new StringBuilder();
        ArrayList<Long> userIdList = new ArrayList<>();
        List resultList=null;
        if(!reportList.isEmpty()){
            for(ScheduleReport report:reportList){
                idList.add(report.getId());
                inList.append(",?");
                userIdList.add(report.getReportUserId());
            }
            //根据上报ID集合，查询上报明细以及关联模型工程量相关信息
            String sql = "SELECT srd.report_volume,prm.quantity,prm.name,srd.report_id " +
                    "FROM wm_schedule_report_detail srd " +
                    "LEFT JOIN wm_schedule_plan_ref_model prm ON prm.model_id=srd.project_volume_id " +
                    "WHERE srd.report_id IN ("+inList.substring(1)+")";
            Query contentQuery = em.createNativeQuery(sql);
            for (int j=0;j<idList.size();j++){
                // 设置查询ID集合参数
                contentQuery.setParameter(j+1,idList.get(j));
            }
            // 获取查询结果
            resultList = contentQuery.getResultList();
        }
        List<ScheduleReportDetailVo> hisDetailVoList = new ArrayList<>();
        if(resultList!=null&&!resultList.isEmpty()){
            // 遍历查询结果
            for(Object row:resultList) {
                ScheduleReportDetailVo hisDetailVo = new ScheduleReportDetailVo();
                Object[] cells = (Object[]) row;
                // 本次上报量
                BigDecimal reportVolume = (BigDecimal)cells[0];
                // 计划总工程量
                BigDecimal quantity = (BigDecimal)cells[1];
                // 工程量名称
                String name = (String)cells[2];
                // 上报ID
                Long reportId = (Long)cells[3];
                hisDetailVo.setPlanVolume(quantity);
                hisDetailVo.setReportItemName(name);
                hisDetailVo.setReportItemVolume(reportVolume);
                hisDetailVo.setReportId(reportId);
                hisDetailVoList.add(hisDetailVo);
            }
        }
        //获取用户信息
        List<UserVo> userVoList = systemFeign.fetchUserListByUserIdList(userIdList);
        //组装上报历史数据列表
        for(ScheduleReport report:reportList) {
            ScheduleReportVo reportHisVo = new ScheduleReportVo();
            reportHisVo.setAttachmentName(report.getAttachmentOriName());
            reportHisVo.setAttachmentUrl(report.getAttachmentUrl());
            reportHisVo.setReportDate(report.getCreateTime());
            if(userVoList!=null&&!userVoList.isEmpty()){
                for(UserVo userVo:userVoList){
                    if(userVo.getId().equals(report.getReportUserId())){
                        reportHisVo.setReportUser(userVo.getRealName());
                        break;
                    }
                }
            }

            //过滤上报历史明细数据
            for(ScheduleReportDetailVo hisDetailVo:hisDetailVoList){
                if(hisDetailVo.getReportId().equals(report.getId())){
                    reportHisVo.getHisDetailVoList().add(hisDetailVo);
                }
            }
            reportHisVoMap.computeIfAbsent(report.getSchedulePlanId(),k->new ArrayList<>()).add(reportHisVo);
        }
        return  reportHisVoMap;
    }

    private List<ScheduleReportVo> getSimpleReportHis(List<Long> planIdList) {
        List<ScheduleReportVo> reportVoList = new ArrayList<>();
        // 根据任务ID集合获取上报记录列表
        List<ScheduleReport> reportList = reportRepository.findBySchedulePlanIdIn(planIdList);
        if(!reportList.isEmpty()) {
            ArrayList<Long> userIdList = new ArrayList<>();
            for (ScheduleReport report : reportList) {
                if(!userIdList.contains(report.getReportUserId())){
                    userIdList.add(report.getReportUserId());
                }
            }
            //获取用户信息
            List<UserVo> userVoList = systemFeign.fetchUserListByUserIdList(userIdList);
            //组装上报历史数据列表
            for(ScheduleReport report:reportList) {
                ScheduleReportVo reportHisVo = new ScheduleReportVo();
                reportHisVo.setAttachmentName(report.getAttachmentOriName());
                reportHisVo.setAttachmentUrl(report.getAttachmentUrl());
                reportHisVo.setReportDate(report.getCreateTime());
                reportHisVo.setComment(report.getReportComment());
                reportHisVo.setReportQuantity(report.getReportVolume());
                reportHisVo.setId(report.getId());
                if(userVoList!=null&&!userVoList.isEmpty()){
                    for (UserVo userVo : userVoList) {
                        if (userVo.getId().equals(report.getReportUserId())) {
                            reportHisVo.setReportUser(userVo.getRealName());
                            break;
                        }
                    }
                }
                reportVoList.add(reportHisVo);
            }
        }

        return reportVoList;
    }

    @Override
    public List<ScheduleReportVo> getReportHis(Long planId) {
        SchedulePlan plan = planRepository.findOne(planId);
        if(plan==null){
            throw new ServiceErrorException("进度任务不存在！");
        }
        List<Long> planIdList=new ArrayList<>();
        planIdList.add(planId);
        return getSimpleReportHis(planIdList);
    }
}
