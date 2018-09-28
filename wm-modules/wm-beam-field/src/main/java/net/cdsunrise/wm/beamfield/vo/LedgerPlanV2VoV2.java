package net.cdsunrise.wm.beamfield.vo;

import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Author: WangRui
 * Date: 2018/6/5
 * Describe:
 */
@Data
public class LedgerPlanV2VoV2 {

    @ApiParam("工点")
    private String workPoint;

    @ApiParam("用梁时间")
    private Date useTime;

    @ApiParam("墩号数组")
    private List<String> pierList;


}
