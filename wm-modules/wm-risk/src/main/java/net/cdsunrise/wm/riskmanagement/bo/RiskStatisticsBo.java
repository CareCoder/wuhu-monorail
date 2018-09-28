package net.cdsunrise.wm.riskmanagement.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lijun
 * @date 2018-04-20.
 */
@Data
@ApiModel("风险类型")
public class RiskStatisticsBo {
    @ApiModelProperty("风险类型名称")
    private String name;
    @ApiModelProperty("风险类型数量")
    private Integer quantity;
}
