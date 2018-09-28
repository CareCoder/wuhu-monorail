package net.cdsunrise.wm.beamfield.vo;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import net.cdsunrise.wm.base.annotation.ExcelCell;
import net.cdsunrise.wm.base.annotation.ExcelCellType;

import java.math.BigDecimal;

/**
 * Author: RoronoaZoro丶WangRUi
 * Time: 2018/6/14/014
 * Describe: LedgerV2版本的excel导出模板
 */
@Data
public class LedgerV2ExcelModel {

    @ApiParam("模型构件唯一标示")
    @ExcelCell(value = ExcelCellType.CELL_TYPE_STRING)
    private String modelComponentUniquelyMarking;

    @ApiParam("梁号")
    @ExcelCell(index = 1,value = ExcelCellType.CELL_TYPE_STRING)
    private String beamNumber;

    @ApiParam("图号")
    @ExcelCell(index = 2,value = ExcelCellType.CELL_TYPE_STRING)
    private String graphNumber;

    @ApiParam("线路")
    @ExcelCell(index = 3,value = ExcelCellType.CELL_TYPE_STRING)
    private String line;

    @ApiParam("适用范围")
    @ExcelCell(index = 4,value = ExcelCellType.CELL_TYPE_STRING)
    private String applicationScope;

    @ApiParam("线路区间")
    @ExcelCell(index = 5,value = ExcelCellType.CELL_TYPE_STRING)
    private String lineInterval;

    @ApiParam("桥墩编号小")
    @ExcelCell(index = 6, value = ExcelCellType.CELL_TYPE_STRING)
    private String pierNumberSmall;

    @ApiParam("桥墩编号大")
    @ExcelCell(index = 7, value = ExcelCellType.CELL_TYPE_STRING)
    private String pierNumberBig;

    @ApiParam("里程 小里程")
    @ExcelCell(index = 8,value = ExcelCellType.CELL_TYPE_STRING)
    private String mileageSmall;

    @ApiParam("里程 大里程")
    @ExcelCell(index = 9,value = ExcelCellType.CELL_TYPE_STRING)
    private String mileageBig;

    @ApiParam("梁型")
    @ExcelCell(index = 10, value = ExcelCellType.CELL_TYPE_STRING)
    private String beamType;

    @ApiParam("跨度")
    @ExcelCell(index = 11, value = ExcelCellType.CELL_TYPE_DECIMAL)
    private BigDecimal beamSpan;

    @ApiParam("梁长")
    @ExcelCell(index = 12, value = ExcelCellType.CELL_TYPE_DECIMAL)
    private BigDecimal beamLength;

    @ApiParam("曲线半径")
    @ExcelCell(index = 13, value = ExcelCellType.CELL_TYPE_DECIMAL)
    private BigDecimal radiusOfCurve;

    @ApiParam("超高/mm小里程")
    @ExcelCell(index = 14,value = ExcelCellType.CELL_TYPE_STRING)
    private String superHighSmallMileage;

    @ApiParam("超高/mm大里程")
    @ExcelCell(index = 15,value = ExcelCellType.CELL_TYPE_STRING)
    private String superHighBigMileage;

    @ApiParam("超高长度/m")
    @ExcelCell(index = 16,value = ExcelCellType.CELL_TYPE_STRING)
    private String superHighLength;

    @ApiParam("预埋件 疏散通道")
    @ExcelCell(index = 17,value = ExcelCellType.CELL_TYPE_STRING)
    private String ePEscapeRoute;

    @ApiParam("预埋件 接触轨槽道数量")
    @ExcelCell(index = 18,value = ExcelCellType.CELL_TYPE_STRING)
    private BigDecimal ePContactRailChannelNumber;

    @ApiParam("预埋件 信标/套")
    @ExcelCell(index = 19,value = ExcelCellType.CELL_TYPE_STRING)
    private String ePBeacon;

    @ApiParam("预埋件 支撑型钢")
    @ExcelCell(index = 20,value = ExcelCellType.CELL_TYPE_STRING)
    private String ePSupportingSteel;

    @ApiParam("预埋件 支座/套 铸钢拉力")
    @ExcelCell(index = 21,value = ExcelCellType.CELL_TYPE_STRING)
    private String ePSupportCastSteelPullingForce;

    @ApiParam("预埋件 支座/套 球形钢")
    @ExcelCell(index = 22,value = ExcelCellType.CELL_TYPE_STRING)
    private String ePSupportSphericalSteel;

    @ApiParam("预埋件 内模/㎡")
    @ExcelCell(index = 23,value = ExcelCellType.CELL_TYPE_STRING)
    private String ePInternalModel;

    @ApiParam("预埋件 PVC管/m")
    @ExcelCell(index = 24,value = ExcelCellType.CELL_TYPE_STRING)
    private String ePPVC;

    @ApiParam("预埋件 钢管/m")
    @ExcelCell(index = 25,value = ExcelCellType.CELL_TYPE_STRING)
    private String ePSteelPipe;

    @ApiParam("预埋件 指形板座")
    @ExcelCell(index = 26,value = ExcelCellType.CELL_TYPE_STRING)
    private String ePFingerPlateSeat;

    @ApiParam("预埋件 钢绞线/t")
    @ExcelCell(index = 27,value = ExcelCellType.CELL_TYPE_STRING)
    private String ePSteelStrand;

    @ApiParam("预埋件 波纹管/m φ50")
    @ExcelCell(index = 28,value = ExcelCellType.CELL_TYPE_STRING)
    private String ePCorrugatedPipe50;

    @ApiParam("预埋件 波纹管/m φ55")
    @ExcelCell(index = 29,value = ExcelCellType.CELL_TYPE_STRING)
    private String ePCorrugatedPipe55;

    @ApiParam("预埋件 波纹管/m φ60")
    @ExcelCell(index = 30,value = ExcelCellType.CELL_TYPE_STRING)
    private String ePCorrugatedPipe60;

    @ApiParam("预埋件 波纹管/m φ70")
    @ExcelCell(index = 31,value = ExcelCellType.CELL_TYPE_STRING)
    private String ePCorrugatedPipe70;

    @ApiParam("预埋件 锚具/锚垫板 3")
    @ExcelCell(index = 32,value = ExcelCellType.CELL_TYPE_STRING)
    private String ePAnchoragePlate3;

    @ApiParam("预埋件 锚具/锚垫板 4")
    @ExcelCell(index = 33,value = ExcelCellType.CELL_TYPE_STRING)
    private String ePAnchoragePlate4;

    @ApiParam("预埋件 锚具/锚垫板 5")
    @ExcelCell(index = 34,value = ExcelCellType.CELL_TYPE_STRING)
    private String ePAnchoragePlate5;

    @ApiParam("预埋件 锚具/锚垫板 6")
    @ExcelCell(index = 35,value = ExcelCellType.CELL_TYPE_STRING)
    private String ePAnchoragePlate6;

    @ApiParam("预埋件 锚具/锚垫板 7")
    @ExcelCell(index = 36,value = ExcelCellType.CELL_TYPE_STRING)
    private String ePAnchoragePlate7;

    @ApiParam("预埋件 压浆料 /m³")
    @ExcelCell(index = 37,value = ExcelCellType.CELL_TYPE_STRING)
    private String ePGroutingMaterial;

    @ApiParam("钢筋工程 设计 HPB8")
    @ExcelCell(index = 38,value = ExcelCellType.CELL_TYPE_STRING)
    private String HPB8;

    @ApiParam("钢筋工程 设计 HPB10")
    @ExcelCell(index = 39,value = ExcelCellType.CELL_TYPE_STRING)
    private String HPB10;

    @ApiParam("钢筋工程 设计 HPB12")
    @ExcelCell(index = 40,value = ExcelCellType.CELL_TYPE_STRING)
    private String HPB12;

    @ApiParam("钢筋工程 设计 HPB16")
    @ExcelCell(index = 41,value = ExcelCellType.CELL_TYPE_STRING)
    private String HPB16;

    @ApiParam("钢筋工程 设计 HPB18")
    @ExcelCell(index = 42,value = ExcelCellType.CELL_TYPE_STRING)
    private String HPB18;

    @ApiParam("钢筋工程 设计 HPB32")
    @ExcelCell(index = 43,value = ExcelCellType.CELL_TYPE_STRING)
    private String HPB32;

    @ApiParam("钢筋工程 套筒 φ32")
    @ExcelCell(index = 44,value = ExcelCellType.CELL_TYPE_STRING)
    private String sleeve32;

    @ApiParam("钢筋工程 套筒 φ16")
    @ExcelCell(index = 45,value = ExcelCellType.CELL_TYPE_STRING)
    private String sleeve16;

    @ApiParam("C60")
    @ExcelCell(index = 46,value = ExcelCellType.CELL_TYPE_STRING)
    private String c60;

    @ApiParam("是否下发")
    @ExcelCell(index = 47,value = ExcelCellType.CELL_TYPE_STRING)
    private String isGrant = "已下发";

    @ApiParam("制梁台座")
    @ExcelCell(index = 48,value = ExcelCellType.CELL_TYPE_STRING)
    private String beamPedestal;

    @ApiParam("钢筋绑扎完成时间")
    @ExcelCell(index = 49,value = ExcelCellType.CELL_TYPE_STRING)
    private String steelBarBindingTime;

    @ApiParam("合模时间")
    @ExcelCell(index = 50,value = ExcelCellType.CELL_TYPE_STRING)
    private String modeTime;

    @ApiParam("浇筑时间")
    @ExcelCell(index = 51,value = ExcelCellType.CELL_TYPE_STRING)
    private String pouringTime;

    @ApiParam("坍落度")
    @ExcelCell(index = 52,value = ExcelCellType.CELL_TYPE_STRING)
    private String slump;

    @ApiParam("入模温度")
    @ExcelCell(index = 53,value = ExcelCellType.CELL_TYPE_STRING)
    private String dieTemperature;

    @ApiParam("拆模时间")
    @ExcelCell(index = 54,value = ExcelCellType.CELL_TYPE_STRING)
    private String dieBreakingTime;

    @ApiParam("初张拉时间")
    @ExcelCell(index = 55,value = ExcelCellType.CELL_TYPE_STRING)
    private String initialTensioningTime;

    @ApiParam("移梁时间")
    @ExcelCell(index = 56,value = ExcelCellType.CELL_TYPE_STRING)
    private String beamShiftingTime;

    @ApiParam("存梁台座号")
    @ExcelCell(index = 57,value = ExcelCellType.CELL_TYPE_STRING)
    private String storageBeamNumber;

    @ApiParam("终张拉时间")
    @ExcelCell(index = 58,value = ExcelCellType.CELL_TYPE_STRING)
    private String endTensioningTime;

    @ApiParam("压浆时间")
    @ExcelCell(index = 59,value = ExcelCellType.CELL_TYPE_STRING)
    private String pulpingTime;
}
