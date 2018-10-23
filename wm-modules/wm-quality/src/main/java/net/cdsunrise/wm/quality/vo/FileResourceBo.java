package net.cdsunrise.wm.quality.vo;

import lombok.Data;

@Data
public class FileResourceBo {
    private String originalName;
    private String fileName;
    private String suffix;
    private String uuid;
    private String url;
}
