package net.cdsunrise.wm.schedule.entity;

import lombok.Data;
import net.cdsunrise.wm.base.hibernate.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/***
 * @author gechaoqing
 * 进度上报关联延滞因素
 */
@Data
@Entity
@Table(name="wm_schedule_report_ref_delay_reason")
public class ScheduleReportRefDelayReason extends BaseEntity {
    private Long reportId;
    /***
     * 延迟原因ID
     */
    private Long delayId;
    /***
     * 延迟原因描述
     */
    private String delayComment;
    /***
     * 延迟解决措施
     */
    private String delaySolution;
}
