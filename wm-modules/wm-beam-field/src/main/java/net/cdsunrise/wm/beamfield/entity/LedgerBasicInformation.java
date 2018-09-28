package net.cdsunrise.wm.beamfield.entity;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import net.cdsunrise.wm.base.annotation.ExcelCell;
import net.cdsunrise.wm.base.annotation.ExcelCellType;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Author: WangRui
 * Date: 2018/6/1
 * Describe:
 */
@Data
@Entity
@Table(name = "wm_ledger_basic_info")
public class LedgerBasicInformation {

    @Id
    @ApiParam("梁号")
    @ExcelCell(value = ExcelCellType.CELL_TYPE_STRING)
    private String beamNumber;

    @ApiParam("模型构件唯一标示")
    private String modelComponentUniquelyMarking;

    @ApiParam("图号")
    private String graphNumber;

    @ApiParam("线路")
    private String line;

    @ApiParam("适用范围")
    private String applicationScope;

    @ApiParam("线路区间")
    private String lineInterval;

    @ApiParam("桥墩编号小")
    @ExcelCell(index = 1, value = ExcelCellType.CELL_TYPE_STRING)
    private String pierNumberSmall;

    @ApiParam("桥墩编号大")
    @ExcelCell(index = 3, value = ExcelCellType.CELL_TYPE_STRING)
    private String pierNumberBig;

    @ApiParam("里程 小里程")
    private String mileageSmall;

    @ApiParam("里程 大里程")
    private String mileageBig;

    @ApiParam("梁型")
    @ExcelCell(index = 5, value = ExcelCellType.CELL_TYPE_STRING)
    private String beamType;

    @ApiParam("跨度")
    @ExcelCell(index = 7, value = ExcelCellType.CELL_TYPE_DECIMAL)
    private BigDecimal beamSpan;

    @ApiParam("梁长")
    @ExcelCell(index = 6, value = ExcelCellType.CELL_TYPE_DECIMAL)
    private BigDecimal beamLength;

    @ApiParam("曲线半径")
    @ExcelCell(index = 8, value = ExcelCellType.CELL_TYPE_DECIMAL)
    private BigDecimal radiusOfCurve;

    @ApiParam("超高/mm小里程")
    private String superHighSmallMileage;

    @ApiParam("超高/mm大里程")
    private String superHighBigMileage;

    @ApiParam("超高长度/m")
    private String superHighLength;

    @ApiParam("预埋件 疏散通道")
    private String ePEscapeRoute;

    @ApiParam("预埋件 接触轨槽道数量")
    private BigDecimal ePContactRailChannelNumber;

    @ApiParam("预埋件 信标/套")
    private String ePBeacon;

    @ApiParam("预埋件 支撑型钢")
    private String ePSupportingSteel;

    @ApiParam("预埋件 支座/套 铸钢拉力")
    private String ePSupportCastSteelPullingForce;

    @ApiParam("预埋件 支座/套 球形钢")
    private String ePSupportSphericalSteel;

    @ApiParam("预埋件 内模/㎡")
    private String ePInternalModel;

    @ApiParam("预埋件 PVC管/m")
    private String ePPVC;

    @ApiParam("预埋件 钢管/m")
    private String ePSteelPipe;

    @ApiParam("预埋件 指形板座")
    private String ePFingerPlateSeat;

    @ApiParam("预埋件 钢绞线/t")
    private String ePSteelStrand;

    @ApiParam("预埋件 波纹管/m φ50")
    private String ePCorrugatedPipe50;

    @ApiParam("预埋件 波纹管/m φ55")
    private String ePCorrugatedPipe55;

    @ApiParam("预埋件 波纹管/m φ60")
    private String ePCorrugatedPipe60;

    @ApiParam("预埋件 波纹管/m φ70")
    private String ePCorrugatedPipe70;

    @ApiParam("预埋件 锚具/锚垫板 3")
    private String ePAnchoragePlate3;

    @ApiParam("预埋件 锚具/锚垫板 4")
    private String ePAnchoragePlate4;

    @ApiParam("预埋件 锚具/锚垫板 5")
    private String ePAnchoragePlate5;

    @ApiParam("预埋件 锚具/锚垫板 6")
    private String ePAnchoragePlate6;

    @ApiParam("预埋件 锚具/锚垫板 7")
    private String ePAnchoragePlate7;

    @ApiParam("预埋件 压浆料 /m³")
    private String ePGroutingMaterial;

    @ApiParam("钢筋工程 设计 HPB8")
    private String HPB8;

    @ApiParam("钢筋工程 设计 HPB10")
    private String HPB10;

    @ApiParam("钢筋工程 设计 HPB12")
    private String HPB12;

    @ApiParam("钢筋工程 设计 HPB16")
    private String HPB16;

    @ApiParam("钢筋工程 设计 HPB18")
    private String HPB18;

    @ApiParam("钢筋工程 设计 HPB32")
    private String HPB32;

    @ApiParam("钢筋工程 套筒 φ32")
    private String sleeve32;

    @ApiParam("钢筋工程 套筒 φ16")
    private String sleeve16;

    @ApiParam("C60")
    private String c60;

    @ApiParam("工点")
    private String workPoint;

    @ApiParam("类型-直/曲线")
    @ExcelCell(index = 4, value = ExcelCellType.CELL_TYPE_STRING)
    private String type;

    @ApiParam("墩高")
    @ExcelCell(index = 9, value = ExcelCellType.CELL_TYPE_STRING)
    private String pierHeight;

    @ApiParam("小里程 断面 顶面宽度/m")
    private String smSectionTop;

    @ApiParam("小里程 断面 底面宽度/m")
    private String smSectionBottom;

    @ApiParam("小里程 断面 左侧高度/m")
    private String smSectionLeft;

    @ApiParam("小里程 断面 右侧高度/m")
    private String smSectionRight;

    @ApiParam("小里程 梁端临时支撑 里程/m")
    private String smBeamTemporarySupportM;

    @ApiParam("小里程 梁端临时支撑 高程/m")
    private String smBeamTemporarySupportA;

    @ApiParam("小里程 墩身临时支撑 里程/m")
    private String smPierTemporarySupportM;

    @ApiParam("小里程 墩身临时支撑 高程/m")
    private String smPierTemporarySupportA;

    @ApiParam("大里程 断面 顶面宽度/m")
    private String bmSectionTop;

    @ApiParam("大里程 断面 底面宽度/m")
    private String bmSectionBottom;

    @ApiParam("大里程 断面 左侧高度/m")
    private String bmSectionLeft;

    @ApiParam("大里程 断面 右侧高度/m")
    private String bmSectionRight;

    @ApiParam("大里程 梁端临时支撑 里程/m")
    private String bmBeamTemporarySupportM;

    @ApiParam("大里程 梁端临时支撑 高程/m")
    private String bmBeamTemporarySupportA;

    @ApiParam("大里程 墩身临时支撑 里程/m")
    private String bmPierTemporarySupportM;

    @ApiParam("大里程 墩身临时支撑 高程/m")
    private String bmPierTemporarySupportA;

    @ApiParam("1/4断面 顶面宽度/m")
    private String sectionOneTopWidth;

    @ApiParam("1/4断面 底面宽度/m")
    private String sectionOneBottomWidth;

    @ApiParam("1/4断面 左边高度/m")
    private String sectionOneLeftWidth;

    @ApiParam("1/4断面 右边高度/m")
    private String sectionOneRightWidth;

    @ApiParam("2/4断面 顶面宽度/m")
    private String sectionTwoTopWidth;

    @ApiParam("2/4断面 底面宽度/m")
    private String sectionTwoBottomWidth;

    @ApiParam("2/4断面 左边高度/m")
    private String sectionTwoLeftWidth;

    @ApiParam("2/4断面 右边高度/m")
    private String sectionTwoRightWidth;

    @ApiParam("3/4断面 顶面宽度/m")
    private String sectionThreeTopWidth;

    @ApiParam("3/4断面 底面宽度/m")
    private String sectionThreeBottomWidth;

    @ApiParam("3/4断面 左边高度/m")
    private String sectionThreeLeftWidth;

    @ApiParam("3/4断面 右边高度/m")
    private String sectionThreeRightWidth;

}
