package net.cdsunrise.wm.document.vo;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * Author: RoronoaZoro丶WangRui
 * Time: 2018/8/1/001
 * Describe:
 */
@Data
public class FileManagementUploadVo {

    @ApiParam("文件")
    private MultipartFile file;

    @ApiParam("文件夹名称")
    private String folderName;

    @ApiParam("父级文件夹id，没有就为0")
    private Long parentId;

    @ApiParam("类型 1:文件夹 2:文件")
    private int type;

    @ApiParam("是否需要权限控制 1:有权限控制,2:无权限控制")
    private int isControl;

    @ApiParam("部门id拼接字符串")
    private String deptIdString;
}
