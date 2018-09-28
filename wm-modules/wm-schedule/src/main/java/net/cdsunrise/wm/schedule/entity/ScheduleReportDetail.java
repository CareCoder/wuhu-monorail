package net.cdsunrise.wm.schedule.entity;

import lombok.Data;
import net.cdsunrise.wm.base.hibernate.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/***
 * @author gechaoqing
 * 进度上报明细
 */
@Data
@Entity
@Table(name = "wm_schedule_report_detail")
public class ScheduleReportDetail extends BaseEntity{
    private Long reportId;
    private Long projectVolumeId;
    private BigDecimal reportVolume;
    private BigDecimal totalReportedVolume;
    /***
     * 当前工程量完成率
     */
    private BigDecimal completePercent;
}
