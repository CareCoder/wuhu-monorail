package net.cdsunrise.wm.quality.vo;

import lombok.Data;

import java.util.Date;

@Data
public class TeamworkVo {
    private Long id;
    private String name;
    private Integer status;
    private String fileUuid;
    private Long workPointId;
    private Date createTime;
    private Date modifyTime;
    private String fileUrl;
}
