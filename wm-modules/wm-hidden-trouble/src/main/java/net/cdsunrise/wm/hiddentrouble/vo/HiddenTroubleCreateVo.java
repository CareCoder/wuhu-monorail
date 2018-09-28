package net.cdsunrise.wm.hiddentrouble.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author lijun
 * @date 2018-04-26.
 * @descritpion
 */
@Data
@ApiModel("隐患提交对象")
public class HiddenTroubleCreateVo {
    /**
     * 隐患名称
     */
    @ApiModelProperty("风险名称")
    private String name;

    @ApiModelProperty("风险等级  1 2 3 4 5")
    private Integer level;

    @ApiModelProperty("上传图片")
    private MultipartFile[] images;
    /**
     * 经办人ID
     */
    @ApiModelProperty("经办人ID")
    private Long handlerId;

    @ApiModelProperty("验收人ID")
    private Long acceptancePersonId;

    @ApiModelProperty("工点")
    private String workPointName;

    @ApiModelProperty("工点ID")
    private Long workPointId;
}
