package net.cdsunrise.wm.schedule.service;

import net.cdsunrise.wm.schedule.vo.*;

import java.util.List;

/***
 * @author gechaoqing
 * 进度审核
 */
public interface ScheduleAuditService {
    /***
     * 获取任务审核历史记录
     * @param planId 进度计划ID
     * @param auditType 审核类型
     * @return 审核记录
     */
    List<ScheduleAuditVo> getAuditHis(Long planId,String auditType);

    /***
     * 审核进度完成
     * @param submitVo 审核数据
     */
    void auditComplete(SchedulePlanAuditSubmitVo submitVo);

    /***
     * 审核进度计划编制
     * @param submitVo 审核数据
     */
    void auditConfirm(SchedulePlanAuditSubmitVo submitVo);

    /***
     * 审核进度计划编制
     * @param submitVo 审核数据
     */
    void auditConfirmAndUpdatePlan(SchedulePlanAuditAndUpdateSubmitVo submitVo);

    /***
     * 获取需要审核完成的进度计划列表
     * @return 进度计划组合信息列表
     */
    List<ScheduleCombineReportAndAuditVo> getScheduleForCompleteAudit();

    /***
     * 获取需要审核确认的进度计划列表
     * @return 进度计划组合信息列表
     */
    List<SchedulePlanCombineAuditVo> getSchedulePlanForConfirmAudit();

    /***
     * 获取需要审核确认的进度计划列表
     * @param queryVo 查询条件
     * @return 进度计划组合信息列表
     */
    List<SchedulePlanCombineAuditVo> getSchedulePlanForConfirmAudit(SchedulePlanConfirmAuditQueryVo queryVo);
}
