package net.cdsunrise.wm.beamfield.vo;

import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Author: WangRui
 * Date: 2018/6/4
 * Describe:
 */
@Data
public class LedgerPlanAndBasicInfoQueryResultVo {


    @ApiParam("id")
    private Long id;

    @ApiParam("梁号")
    private String beamNumber;

    @ApiParam("备注")
    private String note;

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

    @ApiParam("审核状态")
    private Integer examineStatus;

    @ApiParam("工序审核步骤id,若为-1则显示开始制梁，0-22显示审核，23显示打印按")
    private Integer processExamineId;
}
