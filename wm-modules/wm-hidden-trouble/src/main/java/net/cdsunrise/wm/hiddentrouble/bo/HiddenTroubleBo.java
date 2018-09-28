package net.cdsunrise.wm.hiddentrouble.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.cdsunrise.wm.hiddentrouble.enums.HiddenTroubleStatus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author lijun
 * @date 2018-04-26.
 * @descritpion
 */
@Data
public class HiddenTroubleBo {

    private Long id;
    /**
     * 创建人ID
     */
    @ApiModelProperty("创建人ID")
    private Long creatorId;

    @ApiModelProperty("创建人名称")
    private String creatorName;
    /**
     * 隐患名称
     */
    @ApiModelProperty("隐患名称")
    private String name;
    /**
     * 隐患等级
     */
    @ApiModelProperty("隐患等级")
    private Integer level;
    /**
     * 隐患状态
     */
    @ApiModelProperty("隐患状态")
    private HiddenTroubleStatus status;
    /**
     * 经办人ID
     */
    @ApiModelProperty("经办人ID")
    private Long handlerId;

    @ApiModelProperty("经办人名称")
    private String handlerName;
    /**
     * 处理信息
     */
    @ApiModelProperty("处理信息")
    private String handleScheme;
    /**
     * 处理时间
     */
    @ApiModelProperty("处理时间")
    private Date handleTime;
    /**
     * 验收人ID
     */
    @ApiModelProperty("验收人ID")
    private Long acceptancePersonId;

    @ApiModelProperty("验收人名称")
    private String acceptancePersonName;
    /**
     * 验收备注
     */
    @ApiModelProperty("验收备注")
    private String acceptanceRemark;
    /**
     * 验收时间
     */
    @ApiModelProperty("验收时间")
    private Date acceptanceTime;

    private Date createTime;
    @ApiModelProperty("图片列表")
    private List<ImageResourceBo> images = new ArrayList<>();
}
