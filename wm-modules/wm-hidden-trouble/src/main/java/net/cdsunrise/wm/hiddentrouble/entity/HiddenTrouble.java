package net.cdsunrise.wm.hiddentrouble.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.cdsunrise.wm.base.hibernate.BaseEntity;
import net.cdsunrise.wm.hiddentrouble.enums.HiddenTroubleStatus;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author lijun
 * @date 2018-04-26.
 * @descritpion
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "wm_hidden_trouble")
public class HiddenTrouble extends BaseEntity {

    private static final long serialVersionUID = -7664975697898906004L;
    /**
     * 创建人ID
     */
    private Long creatorId;
    /**
     * 创建人名称
     */
    private String creatorName;
    /**
     * 隐患名称
     */
    private String name;
    /**
     * 隐患等级
     */
    private Integer level;
    /**
     * 工点ID
     */
    private Long workPointId;
    /**
     * 工点名称
     */
    private String workPointName;
    /**
     * 隐患状态
     */
    @Enumerated(EnumType.STRING)
    private HiddenTroubleStatus status;
    /**
     * 经办人ID
     */
    private Long handlerId;
    /**
     * 经办人名称
     */
    private String handlerName;
    /**
     * 处理方案
     */
    private String handleScheme;
    /**
     * 处理时间
     */
    private Date handleTime;
    /**
     * 验收人ID
     */
    private Long acceptancePersonId;
    /**
     * 验收人名称
     */
    private String acceptancePersonName;
    /**
     * 验收备注
     */
    private String acceptanceRemark;
    /**
     * 验收时间
     */
    private Date acceptanceTime;

    @ManyToMany
    @JoinTable(
            name = "wm_hidden_trouble_image",
            joinColumns = @JoinColumn(name = "hidden_trouble_id"),
            inverseJoinColumns = @JoinColumn(name = "image_id")
    )
    private List<ImageResource> images = new ArrayList<>();

}
