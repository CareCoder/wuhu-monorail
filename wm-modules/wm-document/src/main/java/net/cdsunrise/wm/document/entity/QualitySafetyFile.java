package net.cdsunrise.wm.document.entity;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import net.cdsunrise.wm.base.hibernate.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Author: RoronoaZoro丶WangRui
 * Time: 2018/8/1/001
 * Describe:
 */
@Data
@Entity
@Table(name = "wm_quality_safety_file")
public class QualitySafetyFile extends BaseEntity {

    @ApiParam("文件名称")
    private String fileName;

    @ApiParam("文件后缀")
    private String suffix;

    @ApiParam("文件大小M")
    private double size;

    @ApiParam("文件路径")
    private String url;

    @ApiParam("上传时间")
    private Date uploadTime;

}
