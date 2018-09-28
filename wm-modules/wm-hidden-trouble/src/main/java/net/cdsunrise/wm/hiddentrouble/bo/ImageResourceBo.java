package net.cdsunrise.wm.hiddentrouble.bo;

import lombok.Data;

/**
 * @author lijun
 * @date 2018-04-26.
 * @descritpion
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
     * 访问url
     */
    private String url;
}
