package net.cdsunrise.wm.beamfield.vo;

import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.util.Date;

/**
 * Author: WangRui
 * Date: 2018/6/1
 * Describe:
 */
@Data
public class LedgerPlanV2Vo {

    @ApiParam("梁号")
    private String beamNumber;

    @ApiParam("用梁时间-年月日")
    private Date useTime;

    @ApiParam("工点")
    private String workPoint;

    @ApiParam("线路")
    private String line;

    @ApiParam("用梁时间-年月")
    private String useTimeYM;

}
