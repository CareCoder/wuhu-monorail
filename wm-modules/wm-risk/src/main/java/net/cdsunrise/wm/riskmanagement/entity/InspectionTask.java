package net.cdsunrise.wm.riskmanagement.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.cdsunrise.wm.base.hibernate.BaseEntity;
import net.cdsunrise.wm.riskmanagement.enums.InspectionTaskStatus;

import javax.persistence.*;
import java.util.*;

/**
 * @author lijun
 * @date 2018-04-12.
 * @description 巡视任务
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = false)
@Table(name = "wm_inspection_task")
public class InspectionTask extends BaseEntity {
    /**
     * 标题
     */
    private String title;
    /**
     * 位置（BIM位置）
     */
    private String location;
    /**
     * （添加时指定）创建人ID
     */
    private Long creatorId;
    /**
     * 风险源
     */
    private RiskSource riskSource;
    /**
     * 创建人名称
     */
    private String creatorName;
    /**
     * 创建部门ID
     */
    private Long createDeptId;
    /**
     * （添加时指定）经办人ID
     */
    private Long handlerId;
    /**
     * 经办人名称
     */
    private String handlerName;
    /**
     * 验收人ID
     */
    private Long acceptancePersonId;
    /**
     * 验收人名称
     */
    private String acceptancePersonName;
    /**
     * 工点名
     */
    private String workPointName;
    /**
     * 工点ID
     */
    private Long workPointId;
    /**
     * 任务状态
     */
    private InspectionTaskStatus status;
    /**
     * 截图位置
     */
    @ManyToMany
    @JoinTable(
            name = "wm_inspection_screen_image",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "image_id")
    )
    private List<ImageResource> screenImages = new ArrayList<>();
    /**
     * 开始时间
     */
    private Date startDate;
    /**
     * 结束时间
     */
    private Date endDate;
    /**
     * 情况说明，备注
     */
    private String remark;
}
