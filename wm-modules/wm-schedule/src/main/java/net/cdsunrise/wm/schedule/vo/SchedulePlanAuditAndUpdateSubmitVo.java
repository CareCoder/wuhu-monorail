package net.cdsunrise.wm.schedule.vo;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import net.cdsunrise.wm.schedule.entity.SchedulePlan;

import java.math.BigDecimal;
import java.util.Date;

/***
 * @author gechaoqing
 * 进度计划审核和修改提交的数据
 */
@Data
public class SchedulePlanAuditAndUpdateSubmitVo {
    @ApiParam("审核的任务，进行修改后的数据")
    private Long planId;
    @ApiParam("进度任务开始日期，yyyy-MM-dd")
    private Date startDate;
    @ApiParam("进度任务完成日期，yyyy-MM-dd")
    private Date completeDate;
    @ApiParam("计划工程量")
    private BigDecimal quantity;
    @ApiParam("审核意见建议")
    private String opinion;
    @ApiParam("审核结果，任务完成审核['PASS','UN_PASS']，任务编制确认审核['OK','NOT_OK']")
    private String result;
}
