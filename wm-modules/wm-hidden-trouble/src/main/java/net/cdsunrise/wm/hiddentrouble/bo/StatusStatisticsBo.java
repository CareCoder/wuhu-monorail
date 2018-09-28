package net.cdsunrise.wm.hiddentrouble.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author lijun
 * @date 2018-04-26.
 * @descritpion 排查统计
 */
@Data
public class StatusStatisticsBo {

    @ApiModelProperty("未处理")
    private Integer unprocessed = 0;

    @ApiModelProperty("已处理")
    private Integer processed = 0;

    @ApiModelProperty("已验收")
    private Integer accepted = 0;

}
