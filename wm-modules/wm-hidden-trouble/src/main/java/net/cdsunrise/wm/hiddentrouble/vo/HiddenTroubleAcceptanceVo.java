package net.cdsunrise.wm.hiddentrouble.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lijun
 * @date 2018-04-26.
 * @descritpion
 */
@Data
@ApiModel("处理结果")
public class HiddenTroubleAcceptanceVo {
    private Long id;
    @ApiModelProperty("验收备注")
    private String remark;
}
