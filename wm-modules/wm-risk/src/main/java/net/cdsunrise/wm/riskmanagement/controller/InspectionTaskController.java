package net.cdsunrise.wm.riskmanagement.controller;

import io.swagger.annotations.ApiOperation;
import net.cdsunrise.wm.base.hibernate.PageCondition;
import net.cdsunrise.wm.base.hibernate.Pager;
import net.cdsunrise.wm.base.web.UserUtils;
import net.cdsunrise.wm.base.web.annotation.ListPath;
import net.cdsunrise.wm.base.web.annotation.PagePath;
import net.cdsunrise.wm.riskmanagement.bo.LevelClassificationSummaryBo;
import net.cdsunrise.wm.riskmanagement.bo.RiskStatisticsBo;
import net.cdsunrise.wm.riskmanagement.service.InspectionTaskService;
import net.cdsunrise.wm.riskmanagement.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 风险任务
 *
 * @author lijun
 * @apiDefine InspectionTaskNotFound
 */
@Validated
@RestController
@RequestMapping("/inspection-task")
public class InspectionTaskController {
    @Autowired
    private InspectionTaskService inspectionTaskService;

    /**
     * @api {post} /inspection-task/create 创建巡视任务(用于APP)
     */
    @ApiOperation(value ="创建巡视任务")
    @PostMapping("/create")
    public void create(TaskCreateSubmitVo createSubmitVo) {
        inspectionTaskService.create(createSubmitVo);
    }

    /**
     * @api {post} /inspection-task/report 任务上报（用于APP）
     */
    @ApiOperation(value = "任务上报")
    @PostMapping("/report")
    public void report(TaskReportSubmitVo reportSubmitVo) {
        inspectionTaskService.report(reportSubmitVo);
    }

    /**
     * @api {post} /inspection-task/acceptance 任务验收（PC）
     */
    @ApiOperation(value = "任务验收")
    @PostMapping("/acceptance")
    public void acceptance(TaskAcceptanceSubmitVo acceptanceSubmitVo) {
        inspectionTaskService.acceptance(acceptanceSubmitVo);
    }

    /**
     * @api {get} /inspection-task/get/:id  获取单个任务信息
     */
    @ApiOperation(value = "获取单个任务信息")
    @GetMapping(value = "/get/{id}")
    public InspectionTaskVo get(@PathVariable("id") Long id) {
        return inspectionTaskService.get(id);
    }

    /**
     * @api {get} /inspection-task/list  列表查询
     */
    @ApiOperation(value = "列表查询")
    @ListPath
    public List<InspectionTaskVo> getList(InspectionTaskQueryVo queryVo) {
        List<InspectionTaskVo> tasks = inspectionTaskService.findList(queryVo);
        return tasks;
    }

    @ApiOperation("删除任务")
    @GetMapping("/del/{id}")
    public void delete(@PathVariable("id") Long id) {
        inspectionTaskService.delete(id);
    }

    @ApiOperation("获取当前登录用户创建的任务分页")
    @GetMapping("/my-create-page")
    public Pager<InspectionTaskVo> getMyPage(InspectionTaskQueryVo queryVo, PageCondition condition) {
        queryVo.setCreatorId(UserUtils.getUserId());
        return inspectionTaskService.getPager(queryVo, condition);
    }


    @ApiOperation("获取当前登录用户创建的列表")
    @GetMapping("/my-create-list")
    public List<InspectionTaskVo> getMyList(InspectionTaskQueryVo queryVo) {
        queryVo.setCreatorId(UserUtils.getUserId());
        return inspectionTaskService.findList(queryVo);
    }

    @ApiOperation("获取当前用户处理的任务列表")
    @GetMapping("/my-handle-list")
    public List<InspectionTaskVo> getMyHandleList(InspectionTaskQueryVo queryVo) {
        queryVo.setHandlerId(UserUtils.getUserId());
        return inspectionTaskService.findList(queryVo);
    }

    @ApiOperation("获取当前用户处理的任务分页")
    @GetMapping("/my-handle-page")
    public Pager<InspectionTaskVo> getMyHandlePage(InspectionTaskQueryVo queryVo, PageCondition condition) {
        queryVo.setHandlerId(UserUtils.getUserId());
        return inspectionTaskService.getPager(queryVo, condition);
    }


    @ApiOperation("分页查询")
    @PagePath
    public Pager<InspectionTaskVo> getPager(InspectionTaskQueryVo queryVo, PageCondition condition) {
        Pager<InspectionTaskVo> pager = inspectionTaskService.getPager(queryVo, condition);
        return pager;
    }

    /**
     * @api {get} /inspection-task/level-summary  分类汇总
     */
    @ApiOperation("分类汇总")
    @GetMapping("/level-summary")
    public List<LevelClassificationSummaryBo> getLevelSummary(@NotNull(message = "开始时间不能为空") Date startDate,
                                                              @NotNull(message = "结束时间不能为空") Date endDate,
                                                              Long workPointId) {
        List<LevelClassificationSummaryBo> summaries = inspectionTaskService.getLevelSummary(startDate, endDate, workPointId);
        return summaries;
    }

    /**
     * @api {get} /inspection-task/risk-statistics  根据风险分类统计
     */
    @GetMapping("/risk-statistics")
    public List<RiskStatisticsBo> getRiskStatistics(Date startDate,
                                                    Date endDate,
                                                    Long workPointId) {
        List<RiskStatisticsBo> riskStatistics = inspectionTaskService.getRiskStatistics(startDate, endDate, workPointId);
        return riskStatistics;
    }

    /**
     * @api {get} /inspection-task/level-statistics  等级统计
     */
    @ApiOperation("按等级统计")
    @GetMapping("/level-statistics")
    public Map<String, Integer> getLevelStatistics(Date startDate,
                                                   Date endDate,
                                                   Long workPointId) {
        Map<String, Integer> levelStatistics = inspectionTaskService.getLevelStatistics(startDate, endDate, workPointId);
        return levelStatistics;
    }
}
