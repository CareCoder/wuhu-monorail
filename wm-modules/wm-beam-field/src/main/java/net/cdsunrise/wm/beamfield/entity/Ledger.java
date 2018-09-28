package net.cdsunrise.wm.beamfield.entity;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import net.cdsunrise.wm.base.annotation.ExcelCell;
import net.cdsunrise.wm.base.annotation.ExcelCellType;
import net.cdsunrise.wm.base.hibernate.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Author : WangRui
 * Date : 2018/5/3
 * Describe :
 */
@Data
@Entity
@Table(name = "wm_leger")
public class Ledger extends BaseEntity{

    /**
     * 梁号
     */
    @ApiParam("梁号")
    @ExcelCell(index = 1, value = ExcelCellType.CELL_TYPE_STRING)
    private String beamNumber;

    /**
     * 图号
     */
    @ApiParam("图号")
    @ExcelCell(index = 2, value = ExcelCellType.CELL_TYPE_STRING)
    private String graphNumber;

    /**
     * 线路
     */
    @ApiParam("线路")
    @ExcelCell(index = 3, value = ExcelCellType.CELL_TYPE_STRING)
    private String line;

    /**
     * 适用范围
     */
    @ApiParam("适用范围")
    @ExcelCell(index = 4, value = ExcelCellType.CELL_TYPE_STRING)
    private String scopeOfApplication;

    /**
     * 线路区间
     */
    @ApiParam("线路区间")
    @ExcelCell(index = 5, value = ExcelCellType.CELL_TYPE_STRING)
    private String lineInterval;

    /**
     * 墩号  小里程
     */
    @ApiParam("墩号  小里程")
    @ExcelCell(index = 6, value = ExcelCellType.CELL_TYPE_STRING)
    private String pierNumberSmallMileage;

    /**
     * 墩号  大里程
     */
    @ApiParam("墩号  大里程")
    @ExcelCell(index = 7, value = ExcelCellType.CELL_TYPE_STRING)
    private String pierNumberBigMileage;

    /**
     * 里程号  小里程
     */
    @ApiParam("里程号  小里程")
    @ExcelCell(index = 8, value = ExcelCellType.CELL_TYPE_STRING)
    private String mileageNumberS;

    /**
     * 里程号  大里程
     */
    @ApiParam("里程号  大里程")
    @ExcelCell(index = 9, value = ExcelCellType.CELL_TYPE_STRING)
    private String mileageNumberB;

    /**
     * 梁型 --结构类型
     */
    @ApiParam("梁型 --结构类型")
    @ExcelCell(index = 10, value = ExcelCellType.CELL_TYPE_STRING)
    private String beamType;

    /**
     * 跨度/m
     */
    @ApiParam("跨度/m")
    @ExcelCell(index = 11, value = ExcelCellType.CELL_TYPE_STRING)
    private String beamSpan;

    /**
     * 梁长/m
     */
    @ApiParam("梁长/m")
    @ExcelCell(index = 12, value = ExcelCellType.CELL_TYPE_STRING)
    private String beamLength;

    /**
     * 曲线半径/m --直/曲线
     */
    @ApiParam("曲线半径/m --直/曲线")
    @ExcelCell(index = 13, value = ExcelCellType.CELL_TYPE_STRING)
    private String beamRadiusOfCurve;

    /**
     * 是否下发
     */
    @ApiParam("是否下发")
    @ExcelCell(index = 14, value = ExcelCellType.CELL_TYPE_STRING)
    private String isGrant;

    /**
     * 超高/mm  小里程
     */
    @ApiParam("超高/mm  小里程")
    @ExcelCell(index = 15, value = ExcelCellType.CELL_TYPE_STRING)
    private String sHSmallMileage;

    /**
     * 超高/mm  大里程
     */
    @ApiParam("超高/mm  大里程")
    @ExcelCell(index = 16, value = ExcelCellType.CELL_TYPE_STRING)
    private String sHBigMileage;

    /**
     * 超高长度/m
     */
    @ApiParam("超高长度/m")
    @ExcelCell(index = 17, value = ExcelCellType.CELL_TYPE_STRING)
    private String sHLength;

    /**
     * 预埋件  疏散通道
     */
    @ApiParam("预埋件  疏散通道")
    @ExcelCell(index = 18, value = ExcelCellType.CELL_TYPE_STRING)
    private String edPartsEscapeRoute;

    /**
     * 预埋件  接触轨/套
     */
    @ApiParam("预埋件  接触轨/套")
    @ExcelCell(index = 19, value = ExcelCellType.CELL_TYPE_STRING)
    private String edPartsContactRailChannel;

    /**
     * 预埋件  信标/套
     */
    @ApiParam("预埋件  信标/套")
    @ExcelCell(index = 20, value = ExcelCellType.CELL_TYPE_STRING)
    private String edPartsBeacon;

    /**
     * 预埋件  支撑型钢
     */
    @ApiParam("预埋件  支撑型钢")
    @ExcelCell(index = 21, value = ExcelCellType.CELL_TYPE_STRING)
    private String edPartsSupportingSteel;

    /**
     * 预埋件  支座/套  铸钢拉力
     */
    @ApiParam("预埋件  支座/套  铸钢拉力")
    @ExcelCell(index = 22, value = ExcelCellType.CELL_TYPE_STRING)
    private String edPartsSupportCSPF;

    /**
     * 预埋件  支座/套  球形钢
     */
    @ApiParam("预埋件  支座/套  球形钢")
    @ExcelCell(index = 23, value = ExcelCellType.CELL_TYPE_STRING)
    private String edPartsSupportSS;

    /**
     * 预埋件  内模/㎡
     */
    @ApiParam("预埋件  内模/㎡")
    @ExcelCell(index = 24, value = ExcelCellType.CELL_TYPE_STRING)
    private String edPartsInternalModel;

    /**
     * 预埋件  PVC管/m
     */
    @ApiParam("预埋件  PVC管/m")
    @ExcelCell(index = 25, value = ExcelCellType.CELL_TYPE_STRING)
    private String edPartsPVC;

    /**
     * 预埋件  钢管/m
     */
    @ApiParam("预埋件  钢管/m")
    @ExcelCell(index = 26, value = ExcelCellType.CELL_TYPE_STRING)
    private String edPartsSteelPipe;

    /**
     * 预埋件  指形板座
     */
    @ApiParam("预埋件  指形板座")
    @ExcelCell(index = 27, value = ExcelCellType.CELL_TYPE_STRING)
    private String edPartsFPS;

    /**
     * 预埋件  钢绞线/t
     */
    @ApiParam("预埋件  钢绞线/t")
    @ExcelCell(index = 28, value = ExcelCellType.CELL_TYPE_STRING)
    private String edPartsSteelStrand;

    /**
     * 预埋件  波纹管/m  φ50
     */
    @ApiParam("预埋件  波纹管/m  φ50")
    @ExcelCell(index = 29, value = ExcelCellType.CELL_TYPE_STRING)
    private String edPartsCP50;

    /**
     * 预埋件  波纹管/m  φ55
     */
    @ApiParam("预埋件  波纹管/m  φ55")
    @ExcelCell(index = 30, value = ExcelCellType.CELL_TYPE_STRING)
    private String edPartsCP55;

    /**
     * 预埋件  波纹管/m  φ60
     */
    @ApiParam("预埋件  波纹管/m  φ60")
    @ExcelCell(index = 31, value = ExcelCellType.CELL_TYPE_STRING)
    private String edPartsCP60;

    /**
     * 预埋件  波纹管/m  φ70
     */
    @ApiParam("预埋件  波纹管/m  φ70")
    @ExcelCell(index = 32, value = ExcelCellType.CELL_TYPE_STRING)
    private String edPartsCP70;

    /**
     * 预埋件  锚具/锚垫板  -φ15.2  3
     */
    @ApiParam("预埋件  锚具/锚垫板  -φ15.2  3")
    @ExcelCell(index = 33, value = ExcelCellType.CELL_TYPE_STRING)
    private String edPartsAAP3;

    /**
     * 预埋件  锚具/锚垫板  -φ15.2  4
     */
    @ApiParam("预埋件  锚具/锚垫板  -φ15.2  4")
    @ExcelCell(index = 34, value = ExcelCellType.CELL_TYPE_STRING)
    private String edPartsAAP4;

    /**
     * 预埋件  锚具/锚垫板  -φ15.2  5
     */
    @ApiParam("预埋件  锚具/锚垫板  -φ15.2  5")
    @ExcelCell(index = 35, value = ExcelCellType.CELL_TYPE_STRING)
    private String edPartsAAP5;

    /**
     * 预埋件  锚具/锚垫板  -φ15.2  6
     */
    @ApiParam("预埋件  锚具/锚垫板  -φ15.2  6")
    @ExcelCell(index = 36, value = ExcelCellType.CELL_TYPE_STRING)
    private String edPartsAAP6;

    /**
     * 预埋件  锚具/锚垫板  -φ15.2  7
     */
    @ApiParam("预埋件  锚具/锚垫板  -φ15.2  7")
    @ExcelCell(index = 37, value = ExcelCellType.CELL_TYPE_STRING)
    private String edPartsAAP7;

    /**
     * 预埋件  压浆料 /m³
     */
    @ApiParam("预埋件  压浆料 /m³")
    @ExcelCell(index = 38, value = ExcelCellType.CELL_TYPE_STRING)
    private String edPartsGroutingMaterial;

    /**
     * 钢筋工程  设计  HPB8
     */
    @ApiParam("钢筋工程  设计  HPB8")
    @ExcelCell(index = 39, value = ExcelCellType.CELL_TYPE_STRING)
    private String reinEngDesignHPB8;

    /**
     * 钢筋工程  设计  HPB10
     */
    @ApiParam("钢筋工程  设计  HPB10")
    @ExcelCell(index = 40, value = ExcelCellType.CELL_TYPE_STRING)
    private String reinEngDesignHPB10;

    /**
     * 钢筋工程  设计  HRB12
     */
    @ApiParam("钢筋工程  设计  HRB12")
    @ExcelCell(index = 41, value = ExcelCellType.CELL_TYPE_STRING)
    private String reinEngDesignHRB12;

    /**
     * 钢筋工程  设计  HRB16
     */
    @ApiParam("钢筋工程  设计  HRB16")
    @ExcelCell(index = 42, value = ExcelCellType.CELL_TYPE_STRING)
    private String reinEngDesignHRB16;

    /**
     * 钢筋工程  设计  HRB28
     */
    @ApiParam("钢筋工程  设计  HRB28")
    @ExcelCell(index = 43, value = ExcelCellType.CELL_TYPE_STRING)
    private String reinEngDesignHRB28;

    /**
     * 钢筋工程  设计  HRB32
     */
    @ApiParam("钢筋工程  设计  HRB32")
    @ExcelCell(index = 44, value = ExcelCellType.CELL_TYPE_STRING)
    private String reinEngDesignHRB32;

    /**
     * 钢筋工程  套筒  φ32
     */
    @ApiParam("钢筋工程  套筒  φ32")
    @ExcelCell(index = 45, value = ExcelCellType.CELL_TYPE_STRING)
    private String reinEngSleeve32;

    /**
     * 钢筋工程  套筒  φ16
     */
    @ApiParam("钢筋工程  套筒  φ16")
    @ExcelCell(index = 46, value = ExcelCellType.CELL_TYPE_STRING)
    private String reinEngSleeve16;

    /**
     * 钢筋工程  制梁台座
     */
    @ApiParam("钢筋工程  制梁台座")
    @ExcelCell(index = 47, value = ExcelCellType.CELL_TYPE_STRING)
    private String reinEngBeamPedestal;

    /**
     * 钢筋工程  绑扎完成时间
     */
    @ApiParam("钢筋工程  绑扎完成时间")
    @ExcelCell(index = 48, value = ExcelCellType.CELL_TYPE_STRING)
    private String reinEngCplBindTime;

    /**
     * 钢筋工程  合模时间
     */
    @ApiParam("钢筋工程  合模时间")
    @ExcelCell(index = 49, value = ExcelCellType.CELL_TYPE_STRING)
    private String reinEngMoldTime;

    /**
     * 浇筑时间  开始
     */
    @ApiParam("浇筑时间  开始")
    @ExcelCell(index = 50, value = ExcelCellType.CELL_TYPE_STRING)
    private String pouringTimeB;

    /**
     * 浇筑时间  结束
     */
    @ApiParam("浇筑时间  结束")
    @ExcelCell(index = 51, value = ExcelCellType.CELL_TYPE_STRING)
    private String pouringTimeE;

    /**
     * 坍落度/cm
     */
    @ApiParam("坍落度/cm")
    @ExcelCell(index = 52, value = ExcelCellType.CELL_TYPE_STRING)
    private String slump;

    /**
     * 入模温度/℃
     */
    @ApiParam("入模温度/℃")
    @ExcelCell(index = 53, value = ExcelCellType.CELL_TYPE_STRING)
    private String entryModelTemperature;

    /**
     * 设计
     */
    @ApiParam("设计")
    @ExcelCell(index = 54, value = ExcelCellType.CELL_TYPE_STRING)
    private String designTemperature;

    /**
     * 实际
     */
    @ApiParam("实际")
    @ExcelCell(index = 55, value = ExcelCellType.CELL_TYPE_STRING)
    private String actualTemperature;

    /**
     * 拆模时间
     */
    @ApiParam("拆模时间")
    @ExcelCell(index = 56, value = ExcelCellType.CELL_TYPE_STRING)
    private String dismantleModelTime;

    /**
     * 预应力张拉及压浆  初张
     */
    @ApiParam("预应力张拉及压浆  初张")
    @ExcelCell(index = 57, value = ExcelCellType.CELL_TYPE_STRING)
    private String presTensGroutBeginTensionTime;

    /**
     * 预应力张拉及压浆  移梁
     */
    @ApiParam("预应力张拉及压浆  移梁")
    @ExcelCell(index = 58, value = ExcelCellType.CELL_TYPE_STRING)
    private String presTensGroutBeamShift;

    /**
     * 预应力张拉及压浆  存梁台座
     */
    @ApiParam("预应力张拉及压浆  存梁台座")
    @ExcelCell(index = 59, value = ExcelCellType.CELL_TYPE_STRING)
    private String presTensGroutBeamStorageNumber;

    /**
     * 预应力张拉及压浆  终张
     */
    @ApiParam("预应力张拉及压浆  终张")
    @ExcelCell(index = 60, value = ExcelCellType.CELL_TYPE_STRING)
    private String presTensGroutEndTensionTime;

    /**
     * 预应力张拉及压浆  压浆
     */
    @ApiParam("预应力张拉及压浆  压浆")
    @ExcelCell(index = 61, value = ExcelCellType.CELL_TYPE_STRING)
    private String presTensGroutPulping;

    /**
     * 是否运出梁场
     */
    @ApiParam("出场")
    @ExcelCell(index = 62, value = ExcelCellType.CELL_TYPE_STRING)
    private String isTransport;

    /**
     * 备注
     */
    @ApiParam("备注")
    @ExcelCell(index = 63, value = ExcelCellType.CELL_TYPE_STRING)
    private String note;
}
