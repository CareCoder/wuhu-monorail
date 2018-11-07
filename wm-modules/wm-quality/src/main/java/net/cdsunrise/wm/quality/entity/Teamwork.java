package net.cdsunrise.wm.quality.entity;

import lombok.Data;
import net.cdsunrise.wm.base.hibernate.BaseEntity;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;

/***
 * @author yq
 * 协同工作
 */
@Data
@Entity
@Table(name = "wm_teamword")
@DynamicUpdate
public class Teamwork extends BaseEntity {
    /**
     * 名字
     */
    private String name;

    /**
     * 状态 0是未开启 1是开启
     */
    private Integer status;

    /**
     * 文件的uuid,之后去文件系统查找文件
     */
    private String fileUuid;

    /**
     * 工作站点的id
     */
    private Long workPointId;
}
