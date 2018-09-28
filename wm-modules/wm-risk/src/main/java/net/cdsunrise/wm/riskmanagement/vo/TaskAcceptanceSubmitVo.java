package net.cdsunrise.wm.riskmanagement.vo;

import io.swagger.annotations.ApiParam;
import lombok.Data;

/**
 * @author lijun
 * @date 2018-04-23.
 * @descritpion
 */
@Data
public class TaskAcceptanceSubmitVo {

    @ApiParam("上报ID")
    private Long reportId;

    @ApiParam("采取措施")
    private String measure;
}
