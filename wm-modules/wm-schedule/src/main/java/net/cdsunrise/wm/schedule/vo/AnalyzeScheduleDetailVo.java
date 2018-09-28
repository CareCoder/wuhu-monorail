package net.cdsunrise.wm.schedule.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/***
 * @author gechaoqing
 * 进度分析详细
 */
@Data
@ApiModel("进度分析信息")
public class AnalyzeScheduleDetailVo {
    @ApiModelProperty("工程单位")
    private String workPointName;
    @ApiModelProperty("工作内容")
    private String schedulePlanName;
    @ApiModelProperty("工况")
    private String schedulePlanStatus;
    @ApiModelProperty("本周计划")
    private BigDecimal currentWeekPlanQuantity=BigDecimal.ZERO;
    @ApiModelProperty("本周完成情况")
    private BigDecimal currentWeekCompleteQuantity=BigDecimal.ZERO;
    @ApiModelProperty("开累")
    private BigDecimal totalCompleteQuantity=BigDecimal.ZERO;
    @ApiModelProperty("剩余")
    private BigDecimal lastQuantity=BigDecimal.ZERO;
    @ApiModelProperty("本周完成率")
    private BigDecimal currentWeekCompletePercent=BigDecimal.ZERO;
    @ApiModelProperty("开累完成率")
    private BigDecimal totalCompletePercent=BigDecimal.ZERO;
    @ApiModelProperty("下周计划")
    private BigDecimal nextWeekPlanQuantity=BigDecimal.ZERO;
}
