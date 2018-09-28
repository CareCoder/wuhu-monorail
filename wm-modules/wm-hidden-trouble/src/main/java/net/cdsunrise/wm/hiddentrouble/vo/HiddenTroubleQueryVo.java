package net.cdsunrise.wm.hiddentrouble.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.cdsunrise.wm.hiddentrouble.enums.HiddenTroubleStatus;

import java.util.Date;

/**
 * @author lijun
 * @date 2018-04-26.
 * @descritpion
 */
@Data
public class HiddenTroubleQueryVo {
    /**
     * 经办人ID
     */
    @ApiModelProperty("上报人 或 经办人 名称")
    private String handlerName;
    /**
     * 隐患名称
     */
    @ApiModelProperty("隐患状态")
    private HiddenTroubleStatus status;
    /**
     * 等级
     */
    @ApiModelProperty("等级")
    private Integer level;

    @ApiModelProperty("处理开始时间")
    private Date handleStartDate;

    @ApiModelProperty("处理结束时间")
    private Date handleEndDate;
}
