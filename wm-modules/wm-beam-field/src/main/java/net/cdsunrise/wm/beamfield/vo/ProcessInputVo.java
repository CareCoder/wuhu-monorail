package net.cdsunrise.wm.beamfield.vo;

import io.swagger.annotations.ApiParam;
import lombok.Data;

/**
 * Author: RoronoaZoro丶WangRUi
 * Time: 2018/6/13/013
 * Describe:
 */
@Data
public class ProcessInputVo {

    @ApiParam("id 非空")
    private Long id;

    @ApiParam("工序id 非空")
    private Integer processId;

    @ApiParam("isPass 非空")
    private String isPass;

    @ApiParam("审核意见")
    private String advice;

    @ApiParam("制梁台座")
    private String beamPedestal;

    @ApiParam("钢筋绑扎完成时间")
    private String steelBarBindingTime;

    @ApiParam("合模时间")
    private String modeTime;

    @ApiParam("浇筑时间")
    private String pouringTime;

    @ApiParam("坍落度")
    private String slump;

    @ApiParam("入模温度")
    private String dieTemperature;

    @ApiParam("拆模时间")
    private String dieBreakingTime;

    @ApiParam("初张拉时间")
    private String initialTensioningTime;

    @ApiParam("移梁时间")
    private String beamShiftingTime;

    @ApiParam("存梁台座号")
    private String storageBeamNumber;

    @ApiParam("终张拉时间")
    private String endTensioningTime;

    @ApiParam("压浆时间")
    private String pulpingTime;

}
