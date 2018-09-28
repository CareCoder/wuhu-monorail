package net.cdsunrise.wm.hiddentrouble.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.cdsunrise.wm.base.hibernate.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author lijun
 * @date 2018-04-26.
 * @descritpion
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "wm_image_resource")
public class ImageResource extends BaseEntity {
    private static final long serialVersionUID = 8322094705425681979L;
    /**
     * 原文件名
     */
    private String originalName;
    /**
     * 新文件名
     */
    private String newName;
}
