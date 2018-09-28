package net.cdsunrise.wm.virtualconstruction.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author lijun
 * @date 2018-04-20.
 */
@Data
@ApiModel("视频对象")
public class VideoBo {
    private Long id;

    @ApiModelProperty("视频名称")
    private String name;
    /**
     * 封面图ID
     */
    @ApiModelProperty("封面图URL")
    private String coverImageUrl;
    /**
     * 视频ID
     */
    @ApiModelProperty("视频URL")
    private String videoUrl;
    /**
     * 描述
     */
    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("创建时间")
    private Date createTime;
}
