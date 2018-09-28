package net.cdsunrise.wm.document.vo;

import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.util.Date;

/**
 * Author: RoronoaZoro丶WangRui
 * Time: 2018/8/7/007
 * Describe:
 */
@Data
public class FileManagementResultVo {

    private Long id;

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
