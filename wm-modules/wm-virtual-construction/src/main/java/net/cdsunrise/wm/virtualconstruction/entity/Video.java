package net.cdsunrise.wm.virtualconstruction.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.cdsunrise.wm.base.hibernate.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * @author lijun
 * @date 2018-04-20.
 */
@EqualsAndHashCode(callSuper = true)
@Table(name = "wm_video")
@Entity
@Data
public class Video extends BaseEntity {
    /**
     * 名称
     */
    private String name;
    /**
     * 封面图ID
     */
    private Long coverImageId;
    /**
     * 视频ID
     */
    private Long videoId;
    /**
     * 描述
     */
    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;
}
