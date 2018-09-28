package net.cdsunrise.wm.schedule.vo;

import io.swagger.annotations.ApiParam;
import lombok.Data;

/**
 * @author gechaoqing
 * 上报延滞因素
 */
@Data
public class ScheduleReportDelayReasonVo {
    @ApiParam("延滞因素ID")
    private Long delayId;
    @ApiParam("延滞原因描述")
    private String delayComment;
    @ApiParam("延滞解决措施")
    private String delaySolution;
}
