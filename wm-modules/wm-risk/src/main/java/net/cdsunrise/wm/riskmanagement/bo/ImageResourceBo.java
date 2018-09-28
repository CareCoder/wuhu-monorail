package net.cdsunrise.wm.riskmanagement.bo;

import lombok.Data;

/**
 * @author lijun
 * @date 2018-04-20.
 */
@Data
public class ImageResourceBo {
    private Long id;

    /**
     * 原文件名
     */
    private String originalName;
    /**
     * 新文件名
     */
    private String newName;
    /**
     * 访问地址
     */
    private String url;
}
