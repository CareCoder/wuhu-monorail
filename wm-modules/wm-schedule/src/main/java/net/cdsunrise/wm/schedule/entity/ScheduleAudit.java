package net.cdsunrise.wm.schedule.entity;

import lombok.Data;
import net.cdsunrise.wm.base.hibernate.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/***
 * @author gechaoqing
 * 进度审核记录
 */
@Data
@Entity
@Table(name = "wm_schedule_audit")
public class ScheduleAudit extends BaseEntity{
    /***
     * 审核人用户ID
     */
    private Long userId;
    private String result;
    private String opinion;
    private Long schedulePlanId;
    /**
     * 审核类型
     * @see net.cdsunrise.wm.schedule.enums.SchedulePlanAuditType#COMPLETE
     * @see net.cdsunrise.wm.schedule.enums.SchedulePlanAuditType#CONFIRM
     */
    private String type;
}
