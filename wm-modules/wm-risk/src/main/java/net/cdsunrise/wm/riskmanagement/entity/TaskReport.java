package net.cdsunrise.wm.riskmanagement.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.cdsunrise.wm.base.hibernate.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 任务上报
 *
 * @author lijun
 * @date 2018-04-20.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "wm_task_report")
public class TaskReport extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "task_id")
    private InspectionTask task;

    private Boolean existsRisk;

    private String remark;

    /**
     * 上报的图片
     */
    @ManyToMany
    @JoinTable(
            name = "wm_report_image",
            joinColumns = @JoinColumn(name = "report_id"),
            inverseJoinColumns = @JoinColumn(name = "image_id")
    )
    private List<ImageResource> reportImages = new ArrayList<>();
    /**
     * 创建人ID
     */
    private Long creatorId;

    /**
     * 验收方案
     */
    private String acceptanceMeasure;
    /**
     * 验收时间
     */
    private Date acceptanceTime;
}
