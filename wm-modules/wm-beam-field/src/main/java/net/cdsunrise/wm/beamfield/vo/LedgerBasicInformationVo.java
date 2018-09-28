package net.cdsunrise.wm.beamfield.vo;

import io.swagger.annotations.ApiParam;
import lombok.Data;

/**
 * Author: WangRui
 * Time: 2018/6/3/003
 * Describe:
 */
@Data
public class LedgerBasicInformationVo {

    @ApiParam("梁号")
    private String beamNumber;

    @ApiParam("工点")
    private String workPoint;

}