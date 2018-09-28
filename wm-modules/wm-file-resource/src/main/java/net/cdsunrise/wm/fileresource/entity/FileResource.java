package net.cdsunrise.wm.fileresource.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.cdsunrise.wm.base.hibernate.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author lijun
 * @date 2018-04-20.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "wm_file_resource")
public class FileResource extends BaseEntity {
    /**
     * 原文件名
     */
    private String originalName;
    /**
     * 新文件名
     */
    private String fileName;
    /**
     * 文件后缀
     */
    private String suffix;
}
