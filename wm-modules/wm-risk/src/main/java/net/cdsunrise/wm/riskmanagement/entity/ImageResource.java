package net.cdsunrise.wm.riskmanagement.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.cdsunrise.wm.base.hibernate.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author lijun
 * @date 2018-04-20.
 * @descritpion 上传图片 本模块所有上传图片保存在这个表中
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "wm_image_resource")
public class ImageResource extends BaseEntity {
    /**
     * 原文件名
     */
    private String originalName;
    /**
     * 新文件名
     */
    private String newName;
}
