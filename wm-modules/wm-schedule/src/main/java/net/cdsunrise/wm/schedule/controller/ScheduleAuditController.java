package net.cdsunrise.wm.schedule.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.cdsunrise.wm.schedule.service.ScheduleAuditService;
import net.cdsunrise.wm.schedule.vo.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/***
 * @author gechaoqing
 * 进度审核
 */
@Api("进度审核")
@RestController
@RequestMapping("/schedule-audit")
public class ScheduleAuditController {
    private ScheduleAuditService scheduleAuditService;
    public ScheduleAuditController(ScheduleAuditService scheduleAuditService){
        this.scheduleAuditService = scheduleAuditService;
    }

    @ApiOperation("获取需要进行审核完成的进度列表")
    @GetMapping("/for-complete/list")
    public List<ScheduleCombineReportAndAuditVo> getScheduleForAudit(){
        return scheduleAuditService.getScheduleForCompleteAudit();
    }

    @ApiOperation("获取需要进行审核编制确认的进度列表")
    @GetMapping("/for-confirm/list")
    public List<SchedulePlanCombineAuditVo> getScheduleForConfirm(){
        return scheduleAuditService.getSchedulePlanForConfirmAudit();
    }

    @ApiOperation("根据条件查询获取需要进行审核编制确认的进度列表")
    @GetMapping("/for-confirm/query")
    public List<SchedulePlanCombineAuditVo> getScheduleForConfirm(SchedulePlanConfirmAuditQueryVo queryVo){
        return scheduleAuditService.getSchedulePlanForConfirmAudit(queryVo);
    }

    @ApiOperation("审核历史查询")
    @GetMapping("/his/{planId}")
    public List<ScheduleAuditVo> getAuditHis(@PathVariable("planId") Long planId,String auditType){
        return scheduleAuditService.getAuditHis(planId,auditType);
    }

    @ApiOperation("提交进度完成审核数据")
    @PostMapping("/complete/submit")
    public void audit(SchedulePlanAuditSubmitVo submitVo){
        scheduleAuditService.auditComplete(submitVo);
    }

    @ApiOperation("提交编制计划确认审核数据")
    @PostMapping("/confirm/submit")
    public void auditConfirm(SchedulePlanAuditSubmitVo submitVo){
        scheduleAuditService.auditConfirm(submitVo);
    }

    public void auditConfirmAndUpdatePlan(SchedulePlanAuditAndUpdateSubmitVo submitVo){

    }
}
