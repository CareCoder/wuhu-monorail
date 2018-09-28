package net.cdsunrise.wm.virtualconstruction.bo;

import lombok.Data;

import java.util.Date;

/**
 * @author lijun
 * @date 2018-04-20.
 */
@Data
public class FileResourceBo {
    private Long id;
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

    private String url;

    private Date createTime;

    private Date modifyTime;
}
