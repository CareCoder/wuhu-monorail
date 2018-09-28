package net.cdsunrise.wm.schedule.entity;

import lombok.Data;
import net.cdsunrise.wm.base.hibernate.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/***
 * @author gechaoqing
 * 进度上报实体
 */
@Data
@Entity
@Table(name = "wm_schedule_report")
public class ScheduleReport extends BaseEntity{
    private Long schedulePlanId;
    /***
     * 上报完成后进度的完成率
     */
    private BigDecimal completePercent;
    private Long reportUserId;
    private BigDecimal reportVolume;
    private BigDecimal totalReportedVolume;
    /***
     * 附件原名称
     */
    private String attachmentOriName;
    /***
     * 附件下载地址
     */
    private String attachmentUrl;

    /***
     * 上报描述
     */
    private String reportComment;
}
