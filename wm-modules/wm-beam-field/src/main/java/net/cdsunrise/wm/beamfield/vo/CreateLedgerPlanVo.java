package net.cdsunrise.wm.beamfield.vo;

import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.util.Date;

/**
 * Author: WangRui
 * Date: 2018/6/6
 * Describe:
 */
@Data
public class CreateLedgerPlanVo {

    @ApiParam("梁号")
    private String beamNumber;

    @ApiParam("用梁时间")
    private Date useTime;

    @ApiParam("工点")
    private String workPoint;
}
