package net.cdsunrise.wm.beamfield.vo;

import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * Author: WangRui
 * Time: 2018/6/3/003
 * Describe:
 */
@Data
public class LedgerPlanAppQueryResult {

    @ApiParam("梁号")
    private String beamNumber;

    @ApiParam("用梁时间")
    private Date useTime;

    @ApiParam("线路")
    private String line;

    @ApiParam("工点")
    private String workPoint;

    @ApiParam("梁型")
    private String beamType;

    @ApiParam("桥墩编号小")
    private String pierNumberSmall;

    @ApiParam("桥墩编号大")
    private String pierNumberBig;

    @ApiParam("类型-直/曲线")
    private String type;

    @ApiParam("梁长")
    private BigDecimal beamLength;

    @ApiParam("跨度")
    private BigDecimal beamSpan;

    @ApiParam("曲线半径")
    private BigDecimal radiusOfCurve;

    @ApiParam("墩高")
    private String pierHeight;

    @ApiParam("图纸url")
    private Map<String,String> url;
}