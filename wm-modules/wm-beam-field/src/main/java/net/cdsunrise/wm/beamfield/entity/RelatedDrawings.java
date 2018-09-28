package net.cdsunrise.wm.beamfield.entity;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import net.cdsunrise.wm.base.hibernate.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Author: WangRui
 * Date: 2018/5/28
 * Describe: 梁号关联图纸
 */
@Data
@Entity
@Table(name = "wm_related_drawings")
public class RelatedDrawings extends BaseEntity {

    @ApiParam("梁型-必填")
    private String beamType;

    @ApiParam("梁长")
    private String beamLength;

    @ApiParam("墩高-必填")
    private String pierHeight;

    @ApiParam("图纸类型 每个梁号特有图纸12种，1-12标识，通用图纸6种，13-18标识")
    private Integer drawingsType;

    @ApiParam("文件名称")
    private String fileName;

    @ApiParam("文件路径")
    private String fileUrl;

}
