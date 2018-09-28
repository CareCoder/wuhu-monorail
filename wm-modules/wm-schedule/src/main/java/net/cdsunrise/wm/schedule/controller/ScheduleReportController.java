package net.cdsunrise.wm.schedule.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.cdsunrise.wm.schedule.service.ScheduleReportService;
import net.cdsunrise.wm.schedule.vo.ScheduleReportCollectionVo;
import net.cdsunrise.wm.schedule.vo.ScheduleReportRefModelVo;
import net.cdsunrise.wm.schedule.vo.ScheduleReportSubmitVo;
import net.cdsunrise.wm.schedule.vo.ScheduleReportVo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/***
 * @author gechaoqing
 * 进度上报
 */
@Api("进度上报")
@RestController
@RequestMapping("/schedule-report")
public class ScheduleReportController {
    private ScheduleReportService scheduleReportService;
    public ScheduleReportController(ScheduleReportService scheduleReportService){
        this.scheduleReportService = scheduleReportService;
    }

    @ApiOperation("上报数据提交")
    @PostMapping("/submit")
    public void report(ScheduleReportCollectionVo reportCollectionVo,@RequestParam(required = false) MultipartFile file){
        scheduleReportService.report(reportCollectionVo,file);
    }

    @ApiOperation("进度上报数据提交")
    @PostMapping("/submit/quantity")
    public void reportSubmit(ScheduleReportSubmitVo submitVo){
        scheduleReportService.reportSubmit(submitVo);
    }

    @ApiOperation("进度上报完成")
    @PostMapping("/complete")
    public void complete(Long[] planId){
        scheduleReportService.completePlans(planId);
    }

    @ApiOperation("获取进度上报历史数据")
    @GetMapping("/hist/{planId}")
    public List<ScheduleReportVo> getReportHis(@PathVariable("planId") Long planId){
        return scheduleReportService.getReportHis(planId);
    }

    @ApiOperation("获取需要上报的工程量列表")
    @GetMapping("/quantity-list/{planId}")
    public List<ScheduleReportRefModelVo> getReportQuantityList(@PathVariable("planId") Long planId){
        return scheduleReportService.getRefModelForReport(planId);
    }

}
