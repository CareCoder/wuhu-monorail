package net.cdsunrise.wm.schedule.vo;

import lombok.Data;

import java.util.Date;

/***
 * @author gechaoqing
 * 进度审核历史记录
 */
@Data
public class ScheduleAuditVo {
    private Long schedulePlanId;
    private String userName;
    private String result;
    private String opinion;
    private Date auditDate;
}
