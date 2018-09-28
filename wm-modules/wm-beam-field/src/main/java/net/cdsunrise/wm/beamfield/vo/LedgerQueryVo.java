package net.cdsunrise.wm.beamfield.vo;

import io.swagger.annotations.ApiParam;
import lombok.Data;

/**
 * Author : WangRui
 * Date : 2018/4/11
 */
@Data
public class LedgerQueryVo  {

    @ApiParam("梁号 模糊查询")
    private String beamNumber;

    @ApiParam("梁型线路区间  模糊查询")
    private String beamTypeLineInterval;

    @ApiParam("梁型线路  模糊查询")
    private String beamTypeLine;

    @ApiParam("梁型左、右线  模糊查询")
    private String beamTypeLeftOrRightLine;
}
