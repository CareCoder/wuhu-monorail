package net.cdsunrise.wm.document.entity;

import io.swagger.annotations.Api;
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
@Table(name = "wm_data_file_manage")
public class FileManagement extends BaseEntity {

    @ApiParam("文件名称")
    private String fileName;

    @ApiParam("类型 1:文件夹 2:文件")
    private int type;

    @ApiParam("父级文件夹id，没有则为0")
    private Long parentId;

    @ApiParam("文件后缀")
    private String suffix;

    @ApiParam("文件大小")
    private double size;

    @ApiParam("路径")
    private String url;

    @ApiParam("上传人id")
    private Long uploadPersonId;

    @ApiParam("上传人姓名")
    private String uploadPersonName;

    @ApiParam("上传时间")
    private Date uploadTime;

    @ApiParam("是否需要权限控制 1:有权限控制,2:无权限控制")
    private int isControl;
}
