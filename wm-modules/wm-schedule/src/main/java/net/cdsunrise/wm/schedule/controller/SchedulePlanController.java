package net.cdsunrise.wm.schedule.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.cdsunrise.wm.base.web.annotation.SavePath;
import net.cdsunrise.wm.schedule.entity.SchedulePlan;
import net.cdsunrise.wm.schedule.entity.SchedulePlanFront;
import net.cdsunrise.wm.schedule.enums.ProjectType;
import net.cdsunrise.wm.schedule.enums.SchedulePlanStatus;
import net.cdsunrise.wm.schedule.service.SchedulePlanService;
import net.cdsunrise.wm.schedule.vo.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

/***
 * @author gechaoqing
 * 进度计划编制
 */
@Api("进度计划编制/分析")
@RestController
@RequestMapping("/schedule-plan")
public class SchedulePlanController {
    private SchedulePlanService schedulePlanService;
    public SchedulePlanController(SchedulePlanService schedulePlanService){
        this.schedulePlanService = schedulePlanService;
    }

    @ApiOperation("新增/编辑任务计划保存")
    @SavePath
    public void save( SchedulePlanSaveVo saveVo) {
        schedulePlanService.save(saveVo);
    }

    @ApiOperation("保存前置任务列表")
    @PostMapping("/front/save")
    public void saveFront( SchedulePlanFrontSaveVo saveVo){
        schedulePlanService.saveFront(saveVo.getPlanId(),saveVo.getPlanFrontList());
    }

    @ApiOperation("保存关联模型列表")
    @PostMapping("/ref-model/save")
    public void saveRefModel(SchedulePlanRefModelSaveVo saveVo){
        schedulePlanService.saveRefModel(saveVo.getPlanId(),saveVo.getModels());
    }

    @ApiOperation("删除任务计划")
    @PostMapping("/del")
    public void del(Long[] ids){
        schedulePlanService.deleteByIdArray(ids);
    }

    @ApiOperation("进度分析-工点任务完成百分比")
    @GetMapping("/analyze/complete-percent/{workPointId}")
    public AnalyzeCompletePercentVo analyzeCompletePercent(@PathVariable("workPointId") Long workPointId){
        return schedulePlanService.queryCompletePercentAnalyze(workPointId, ProjectType.workPoint.name());
    }

    @ApiOperation("进度分析-工点形象进度")
    @GetMapping("/analyze/visualization/{workPointId}")
    public List<AnalyzeVisualizationScheduleVo> analyzeVisualizationSchedule(@PathVariable("workPointId") Long workPointId){
        return schedulePlanService.queryVisualizationAnalyze(workPointId, ProjectType.workPoint.name());
    }

    /*@ApiOperation("进度分析-工点进度明细")
    @GetMapping("/analyze/detail/{workPointId}")
    public List<AnalyzeScheduleDetailVo> queryScheduleDetailAnalyze(@PathVariable("workPointId") Long workPointId){
        return schedulePlanService.queryScheduleDetailAnalyze(workPointId, ProjectType.workPoint.name());
    }*/

    @ApiOperation("进度分析-工点进度明细")
    @GetMapping("/analyze/detail/{workPointId}")
    public List<AnalyzeScheduleDetailVo> queryScheduleDetailAnalyze(@PathVariable("workPointId") Long workPointId){
        return schedulePlanService.queryScheduleDetailAnalyzeV2(workPointId, ProjectType.workPoint.name());
    }

    @ApiOperation("进度分析-延滞因素统计")
    @GetMapping("/analyze/delay-reason/{workPointId}")
    public List<AnalyzeCommonCountVo> queryScheduleDelayReasonAnalyze(@PathVariable("workPointId") Long workPointId){
        return schedulePlanService.queryScheduleDelayReasonAnalyze(workPointId, ProjectType.workPoint.name());
    }

    @ApiOperation("进度分析-预警统计")
    @GetMapping("/analyze/warning/{workPointId}")
    public List<AnalyzeCommonCountVo> queryScheduleWarningAnalyze(@PathVariable("workPointId") Long workPointId){
        return schedulePlanService.queryScheduleWarningAnalyze(workPointId, ProjectType.workPoint.name());
    }

    @ApiOperation("进度分析-工况统计[type = workPoint|line] [id = workPointId|lineId]")
    @GetMapping("/analyze/status/{id}")
    public List<AnalyzeCommonCountVo> queryScheduleStatusAnalyze(@PathVariable("id") Long workPointId,String type){
        return schedulePlanService.queryScheduleStatusAnalyze(workPointId, type);
    }


    @ApiOperation("进度状态模型高亮")
    @GetMapping("/status/model-highlight/{id}")
    public Map<SchedulePlanStatus, List<SchedulePlanStatusModelVo>> getScheduleStatusModel(@PathVariable("id") Long workPointId){
        return schedulePlanService.queryStatusModels(workPointId);
    }

    @ApiOperation("进度模拟")
    @GetMapping("/monitor")
    public List<SchedulePlanForMonitorVo> getScheduleMonitor(){
        return schedulePlanService.queryForMonitor();
    }

    @ApiOperation("获取我的任务列表")
    @GetMapping("/mine")
    public List<SchedulePlan> getMine(){
        return schedulePlanService.queryMyTask();
    }

    @ApiOperation("获取我需要上报的任务列表")
    @GetMapping("/mine/for-report")
    public List<SchedulePlan> getMine(Long workPointId){
        return schedulePlanService.queryMyTaskForReport(workPointId);
    }

    @ApiOperation("获取我需要上报的任务列表")
    @GetMapping("/mine/for-report/top10")
    public List<SchedulePlanForReportVo> getForReportTop10(){
        return schedulePlanService.queryMyTaskForReport();
    }

    @ApiOperation("获取任务下级子集列表")
    @GetMapping("/children/{parentId}")
    public List<SchedulePlan> getChildren(@PathVariable("parentId") Long parentId){
        return schedulePlanService.queryChildren(parentId);
    }


    @ApiOperation("获取工点的任务列表")
    @GetMapping("/work-point/{workPointId}")
    public List<SchedulePlan> getWorkPointTask(@PathVariable("workPointId") Long workPointId,String category){
        return schedulePlanService.queryByWorkPoint(workPointId,category);
    }

    @ApiOperation("进度对比模拟")
    @GetMapping("/monitor/compare/{workPointId}")
    public List<SchedulePlanCompareMonitorVo> getWorkPointPlanForCompareMonitor(@PathVariable("workPointId") Long workPointId){
        return schedulePlanService.queryPlansForCompareMonitor(workPointId);
    }

    @ApiOperation("获取可选择的前置任务列表")
    @GetMapping("/selectable-front-list")
    public List<SchedulePlan> getFrontList(QuerySchedulePlanFrontParams params){
        return schedulePlanService.queryFrontPlanList(params);
    }

    @ApiOperation("获取任务已配置的前置任务列表")
    @GetMapping("/front-list")
    public List<SchedulePlanFront> getScheduleFrontList(Long planId){
        return schedulePlanService.querySchedulePlanFront(planId);
    }

    @ApiOperation("任务草稿提交审核")
    @PostMapping("/submit-draft")
    public void submitDraft(Long[] planIds){
        schedulePlanService.submitDraft(planIds);
    }

    @ApiOperation("工程量快速计量")
    @GetMapping("/quantity-measurement")
    public List<QuantityMeasurementVo> quantityMeasurement(String dateRange){
        return schedulePlanService.quantityMeasurement(dateRange);
    }

    @ApiOperation("根据计划类别获取我的任务列表[YEAR,MONTH,WEEK]")
    @GetMapping("/my/by/category")
    public List<SchedulePlan> getMyTaskByCategory(String category,Long workPointId,@RequestParam(required = false) Long parentId){
        return schedulePlanService.queryMyTaskByCategoryAndWorkPoint(category,workPointId,parentId);
    }

    @ApiOperation("根据计划类别获取所有有效计划列表[YEAR,MONTH,WEEK]")
    @GetMapping("/all/by/category")
    public List<SchedulePlan> getAllByCategory(String category,Long workPointId,@RequestParam(required = false) Long parentId,@RequestParam(required = false) String forParent){
        return schedulePlanService.queryAllByCategoryAndWorkPoint(category,workPointId,parentId,forParent);
    }


    @ApiOperation("导入进度计划")
    @PostMapping("/import")
    public List<SchedulePlanImportVo> importPlanData(MultipartFile file,String category){
        return schedulePlanService.importPlanData(file,category);
    }

    @ApiOperation("导入工点进度计划")
    @PostMapping("/import/work-point")
    public List<SchedulePlanImportVo> importWorkPointPlanData(MultipartFile file,Long workPointId,String category){
        return schedulePlanService.importWorkPointPlanData(file,workPointId,category);
    }

    @ApiOperation("导出工点进度计划")
    @GetMapping("/export/work-point")
    public void exportWorkPointPlan(Long workPointId,String category,HttpServletResponse response){
        schedulePlanService.exportWorkPointPlanData(workPointId,category,response);
    }

    @ApiOperation("获取当前所有已经关联的模型")
    @GetMapping("/ref-model/all")
    public List<SchedulePlanRefModelVo> getAllRefModel(){
        return schedulePlanService.getAllRefModels();
    }

    @ApiOperation("根据进度计划ID获取进度计划详情")
    @GetMapping("/get/{id}")
    public SchedulePlan getById(@PathVariable("id") Long id){
        return schedulePlanService.getById(id);
    }

    @ApiOperation("更新进度计划日期")
    @PostMapping("/set-plan-date")
    public void updatePlanDate(Long id, Date start,Date end){
        schedulePlanService.updatePlanDate(id,start,end);
    }
}
