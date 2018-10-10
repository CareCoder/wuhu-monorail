package net.cdsunrise.wm.schedule.service.impl;

import net.cdsunrise.wm.base.dao.CommonDAO;
import net.cdsunrise.wm.base.exception.ServiceErrorException;
import net.cdsunrise.wm.base.hibernate.QueryHelper;
import net.cdsunrise.wm.base.web.User;
import net.cdsunrise.wm.base.web.UserUtils;
import net.cdsunrise.wm.schedule.entity.ScheduleAudit;
import net.cdsunrise.wm.schedule.entity.SchedulePlan;
import net.cdsunrise.wm.schedule.entity.SchedulePlanBackUp;
import net.cdsunrise.wm.schedule.enums.*;
import net.cdsunrise.wm.schedule.feign.SystemFeign;
import net.cdsunrise.wm.schedule.repository.ScheduleAuditRepository;
import net.cdsunrise.wm.schedule.repository.SchedulePlanRepository;
import net.cdsunrise.wm.schedule.service.ScheduleAuditService;
import net.cdsunrise.wm.schedule.service.ScheduleReportService;
import net.cdsunrise.wm.schedule.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * @author gechaoqing
 * 进度审核服务
 */
@Service
public class ScheduleAuditServiceImpl implements ScheduleAuditService {
    @Autowired
    private CommonDAO commonDAO;

    private ScheduleAuditRepository auditRepository;
    private SchedulePlanRepository planRepository;
    private ScheduleReportService reportService;
    private SystemFeign systemFeign;

    public ScheduleAuditServiceImpl(ScheduleAuditRepository auditRepository
            , SchedulePlanRepository planRepository
            , SystemFeign systemFeign
            , ScheduleReportService reportService) {
        this.auditRepository = auditRepository;
        this.planRepository = planRepository;
        this.systemFeign = systemFeign;
        this.reportService = reportService;
    }

    /***
     * 获取审核历史数据列表
     * @param planIdList 进度计划任务ID列表
     * @return 审核历史数据映射MAP
     */
    private Map<Long,List<ScheduleAuditVo>> getAuditHis(List<Long> planIdList,SchedulePlanAuditType auditType) {
        Map<Long,List<ScheduleAuditVo>> auditHisMap = new HashMap<>(planIdList.size());
        // 进度关联审核记录
        List<ScheduleAudit> auditList = auditRepository.findBySchedulePlanIdInAndType(planIdList,auditType.name());
        ArrayList<Long> idList = new ArrayList<>();
        for (ScheduleAudit audit : auditList) {
            idList.add(audit.getUserId());
        }
        // 获取用户信息列表
        List<UserVo> userVoList = systemFeign.fetchUserListByUserIdList(idList);
        // 审核记录
        for (ScheduleAudit audit : auditList) {
            ScheduleAuditVo auditHisVo = new ScheduleAuditVo();
            auditHisVo.setResult(audit.getResult());
            auditHisVo.setAuditDate(audit.getCreateTime());
            auditHisVo.setOpinion(audit.getOpinion());
            auditHisVo.setSchedulePlanId(audit.getSchedulePlanId());
            if(userVoList!=null&&!userVoList.isEmpty()){
                for (UserVo userVo : userVoList) {
                    if (userVo.getId().equals(audit.getUserId())) {
                        auditHisVo.setUserName(userVo.getRealName());
                        break;
                    }
                }
            }

            auditHisMap.computeIfAbsent(audit.getSchedulePlanId(),k->new ArrayList<>()).add(auditHisVo);
        }
        return auditHisMap;
    }

    @Override
    public List<ScheduleAuditVo> getAuditHis(Long planId,String auditType) {
        SchedulePlan plan = planRepository.findOne(planId);
        if (plan == null) {
            throw new ServiceErrorException("进度任务不存在！");
        }
        List<Long> planIdList = new ArrayList<>();
        planIdList.add(planId);
        SchedulePlanAuditType auditType1 = (auditType==null?SchedulePlanAuditType.COMPLETE:SchedulePlanAuditType.valueOf(auditType));
        return getAuditHis(planIdList,auditType1).get(planId);
    }

    private ScheduleAudit buildAudit(SchedulePlan plan, String opinion, String result,String auditType) {
        ScheduleAudit audit = new ScheduleAudit();
        audit.setOpinion(opinion);
        audit.setResult(result);
        audit.setUserId(UserUtils.getUserId());
        audit.setType(auditType);
        audit.setSchedulePlanId(plan.getId());
        return audit;
    }

    @Override
    public void auditConfirm(SchedulePlanAuditSubmitVo submitVo){
        Long[] planId = submitVo.getPlanId();
        String opinion = submitVo.getOpinion();
        String result = submitVo.getResult();
        if (planId == null || planId.length == 0) {
            throw new ServiceErrorException("无可操作的计划任务！");
        }
        List<SchedulePlan> planList = planRepository.findByIdInForConfirm(planId);
        if (planList == null || planList.isEmpty()) {
            throw new ServiceErrorException("无可操作的计划任务！");
        }
        try{
            SchedulePlanConfirmStatus.valueOf(result);
        }catch (Exception e){
            throw new ServiceErrorException("审核状态参数不正确");
        }
        List<ScheduleAudit> auditList = new ArrayList<>();
        for (SchedulePlan plan : planList) {
            auditList.add(buildAudit(plan, opinion, result, SchedulePlanAuditType.CONFIRM.name()));
            plan.setConfirmStatus(result);
        }
        auditRepository.save(auditList);
        planRepository.save(planList);
    }

    @Override
    public void auditConfirmAndUpdatePlan(SchedulePlanAuditAndUpdateSubmitVo submitVo) {
        Long planId = submitVo.getPlanId();
        if(planId==null){
            throw new ServiceErrorException("无可更新的进度计划！");
        }
        String result = submitVo.getResult();
        try{
            SchedulePlanConfirmStatus.valueOf(result);
        }catch (Exception e){
            throw new ServiceErrorException("审核状态参数不正确");
        }
        SchedulePlan plan = planRepository.findOne(planId);
        ScheduleAudit audit = buildAudit(plan,submitVo.getOpinion(),result,SchedulePlanAuditType.CONFIRM.name());
        plan.setConfirmStatus(result);
        if(submitVo.getStartDate()!=null||submitVo.getCompleteDate()!=null||submitVo.getQuantity()!=null){
            SchedulePlanBackUp backUp = plan.getSchedulePlanBackUp();
            if(backUp==null){
                backUp = new SchedulePlanBackUp();
                plan.setSchedulePlanBackUp(backUp);
            }
            if(submitVo.getStartDate()!=null){
                backUp.setStartDate(plan.getStartDate());
                plan.setStartDate(submitVo.getStartDate());
            }
            if(submitVo.getCompleteDate()!=null){
                backUp.setCompleteDate(plan.getCompleteDate());
                plan.setCompleteDate(submitVo.getCompleteDate());
            }
            if(submitVo.getQuantity()!=null){
                backUp.setQuantity(plan.getQuantity());
                plan.setQuantity(submitVo.getQuantity());
            }
        }
        auditRepository.save(audit);
        planRepository.save(plan);
    }

    @Override
    public void auditComplete(SchedulePlanAuditSubmitVo submitVo) {
        Long[] planId = submitVo.getPlanId();
        String opinion = submitVo.getOpinion();
        String result = submitVo.getResult();
        if (planId == null || planId.length == 0) {
            throw new ServiceErrorException("无可操作的计划任务！");
        }
        List<SchedulePlan> planList = planRepository.findByIdInForAuditComplete(planId);
        if (planList == null || planList.isEmpty()) {
            throw new ServiceErrorException("无可操作的计划任务！");
        }
        ScheduleAuditStatus auditStatus;
        try {
            auditStatus = ScheduleAuditStatus.valueOf(result);
        } catch (Exception e) {
            throw new ServiceErrorException("审核状态参数不正确");
        }
        List<ScheduleAudit> auditList = new ArrayList<>();
        for (SchedulePlan plan : planList) {
            auditList.add(buildAudit(plan, opinion, result, SchedulePlanAuditType.COMPLETE.name()));
            plan.setAuditStatus(result);
            if (auditStatus == ScheduleAuditStatus.PASS) {
                plan.setStatus(SchedulePlanStatus.COMPLETE.name());
            } else if (auditStatus == ScheduleAuditStatus.UN_PASS) {
                plan.setStatus(SchedulePlanStatus.PROCESSING.name());
            }
        }
        auditRepository.save(auditList);
        planRepository.save(planList);
    }

    @Override
    public List<ScheduleCombineReportAndAuditVo> getScheduleForCompleteAudit() {
        List<ScheduleCombineReportAndAuditVo> combineReportAndAuditList = new ArrayList<>();
        List<SchedulePlan> planList = planRepository.findForAudit();
        if (planList != null && planList.size() > 0) {
            List<Long> idList = new ArrayList<>();
            for (SchedulePlan plan : planList) {
                idList.add(plan.getId());
            }
            // 进度关联审核历史记录
            Map<Long,List<ScheduleAuditVo>> auditHisMap = getAuditHis(idList,SchedulePlanAuditType.COMPLETE);
            // 进度关联上报历史记录
            Map<Long,List<ScheduleReportVo>> reportHisMap = reportService.getReportHis(idList);

            // 遍历进度列表，组装进度上报和审核记录
            for (SchedulePlan plan : planList) {
                ScheduleCombineReportAndAuditVo reportAndAuditVo = new ScheduleCombineReportAndAuditVo();
                reportAndAuditVo.setSchedulePlan(plan);
                reportAndAuditVo.setAuditHisList(auditHisMap.get(plan.getId()));
                reportAndAuditVo.setReportList(reportHisMap.get(plan.getId()));
                combineReportAndAuditList.add(reportAndAuditVo);
            }
        }
        return combineReportAndAuditList;
    }

    private List<SchedulePlanCombineAuditVo> getSchedulePlanForConfirm(List<SchedulePlan> planList){
        List<SchedulePlanCombineAuditVo> combineAuditVoList = new ArrayList<>();
        if (planList != null && planList.size() > 0) {
            List<Long> idList = new ArrayList<>();
            for (SchedulePlan plan : planList) {
                idList.add(plan.getId());
            }
            // 进度关联审核历史记录
            Map<Long, List<ScheduleAuditVo>> auditHisMap = getAuditHis(idList, SchedulePlanAuditType.CONFIRM);
            // 遍历进度列表，组装进度上报和审核记录
            for (SchedulePlan plan : planList) {
                SchedulePlanCombineAuditVo auditVo = new SchedulePlanCombineAuditVo();
                auditVo.setSchedulePlan(plan);
                auditVo.setAuditHisList(auditHisMap.get(plan.getId()));
                combineAuditVoList.add(auditVo);
            }
        }
        return combineAuditVoList;
    }

    @Override
    public List<SchedulePlanCombineAuditVo> getSchedulePlanForConfirmAudit() {
        List<SchedulePlan> planList = planRepository.findForConfirm();
        return getSchedulePlanForConfirm(planList);
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
    @Override
    public List<SchedulePlanCombineAuditVo> getSchedulePlanForConfirmAudit(SchedulePlanConfirmAuditQueryVo queryVo) {
        if(queryVo.getDepartId()==null){
            throw new ServiceErrorException("项目部门无效！");
        }
        checkCategory(queryVo.getCategory());
        List<Long> deptIds = new ArrayList<>();
        if(queryVo.getDepartId()!=0) {
            deptIds = systemFeign.subDepartId(queryVo.getDepartId());
            deptIds.add(queryVo.getDepartId());
        }
        List<SchedulePlan> planList;

//        if(!deptIds.isEmpty()){
//            if(StringUtils.isBlank(queryVo.getCreateDate())){
//                planList = planRepository.findForConfirm(queryVo.getCategory(),queryVo.getStatus(),deptIds);
//            }else{
//                planList = planRepository.findForConfirm(queryVo.getCategory(),queryVo.getStatus(),deptIds,queryVo.getCreateDate());
//            }
//        }else{
//            if(StringUtils.isBlank(queryVo.getCreateDate())){
//                planList = planRepository.findForConfirm(queryVo.getCategory(),queryVo.getStatus());
//            }else{
//                planList = planRepository.findForConfirm(queryVo.getCategory(),queryVo.getStatus(),queryVo.getCreateDate());
//            }
//        }

        QueryHelper queryHelper = new QueryHelper(SchedulePlan.class, "schedule_plan");
        queryHelper.addCondition(queryVo.getCategory() != null, "category=?" , queryVo.getCategory())
        .addCondition(queryVo.getStatus() != null, "confirm_status=?" , queryVo.getStatus())
        .addCondition(!deptIds.isEmpty(), "dept_id in (?)", deptIds.stream().map(String::valueOf).reduce("", (f, s) -> f + s + ","))
        .addCondition(queryVo.getCreateDate() != null, "create_time=?" , queryVo.getCreateDate())
        .useNativeSql(false);

        planList = commonDAO.findList(queryHelper);

        if(planList!=null&&!planList.isEmpty()){
            List<DepartmentVo> departmentVoList = systemFeign.getAllDepartment();
            Map<Long,String> departAuthNames = new HashMap<>(10);
            Map<Long,String> departNames = new HashMap<>(10);
            if(departmentVoList!=null&&!departmentVoList.isEmpty()){
                for(DepartmentVo departmentVo:departmentVoList){
                    departNames.put(departmentVo.getId(),departmentVo.getName());
                }
                for(DepartmentVo departmentVo:departmentVoList){
                    Long authId = departmentVo.getUserAuthorId();
                    departAuthNames.put(departmentVo.getId(),departNames.get(authId));
                }
            }
            List<WorkPointVo> workPointVoList = systemFeign.fetchWorkPointByLine(2L);
            for(SchedulePlan plan : planList){
                for(WorkPointVo workPointVo:workPointVoList){
                    if(workPointVo.getId().equals(plan.getWorkPointId())){
                        plan.setWorkPointName(workPointVo.getName());
                        break;
                    }
                }
                plan.setDeptName(departAuthNames.get(plan.getDeptId()));
            }
        }

        return getSchedulePlanForConfirm(planList);
    }
}
