package net.cdsunrise.wm.schedule.vo;

import io.swagger.annotations.ApiParam;
import lombok.Data;

/***
 * @author gechaoqing
 * 进度计划审核提交的数据
 */
@Data
public class SchedulePlanAuditSubmitVo {
    @ApiParam("审核任务ID列表")
    private Long[] planId;
    @ApiParam("审核意见建议")
    private String opinion;
    @ApiParam("审核结果，任务完成审核['PASS','UN_PASS']，任务编制确认审核['OK','NOT_OK']")
    private String result;
}
