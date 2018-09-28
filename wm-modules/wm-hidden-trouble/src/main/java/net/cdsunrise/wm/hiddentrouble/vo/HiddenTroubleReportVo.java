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
@ApiModel("隐患上报对象")
public class HiddenTroubleReportVo {
    private Long id;

    @ApiModelProperty("处理方案")
    private String scheme;
}
