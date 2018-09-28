package net.cdsunrise.wm.schedule.vo;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import net.cdsunrise.wm.schedule.enums.ScheduleReportType;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/***
 * @author gechaoqing
 * 进度上报集合
 */
@Data
public class ScheduleReportCollectionVo {
    @ApiParam("上报进度计划ID")
    private Long planId;
    @ApiParam("上报工程量数据")
    private List<ScheduleReportQuantityVo> report;
    @ApiParam("上报延滞因素")
    private List<ScheduleReportDelayReasonVo> delayReasonList;

    /***
     * 上报类型
     * @see ScheduleReportType#REPORT_ONLY 仅上报
     * @see ScheduleReportType#REPORT_AND_COMPLETE 上报并完成
     */
    @ApiParam("上报类型，仅上报/上报并完成：['REPORT_ONLY','REPORT_AND_COMPLETE']")
    private String reportType = ScheduleReportType.REPORT_ONLY.name();

    @ApiParam("上报完成的关联模型")
    private List<SchedulePlanRefModelVo> modelList;
}
