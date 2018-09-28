package net.cdsunrise.wm.beamfield.entity;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import net.cdsunrise.wm.base.annotation.ExcelCell;
import net.cdsunrise.wm.base.annotation.ExcelCellType;
import net.cdsunrise.wm.base.hibernate.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Author : WangRui
 * Date : 2018/4/19
 * Describe :
 */
@Data
@Entity
@Table(name = "wm_leger_plan")
public class LedgerPlan extends BaseEntity {

    /**
     * 梁号
     */
    @ApiParam("梁号")
    @ExcelCell(index = 1, value = ExcelCellType.CELL_TYPE_STRING)
    private String beamNumber;

    /**
     * 梁长
     */
    @ApiParam("梁长")
    private String beamLength;

    /**
     * 墩高
     */
    @ApiParam("墩高")
    private String pierHeight;

    /**
     * 线路
     */
    @ApiParam("线路")
    @ExcelCell(index = 2, value = ExcelCellType.CELL_TYPE_STRING)
    private String line;

    /**
     * 工点/线路区间
     */
    @ApiParam("工点/线路区间")
    @ExcelCell(index = 3, value = ExcelCellType.CELL_TYPE_STRING)
    private String lineInterval;

    /**
     * 左、右线
     */
    @ApiParam("左、右线")
    @ExcelCell(index = 4, value = ExcelCellType.CELL_TYPE_STRING)
    private String leftOrRightLine;

    /**
     * 结构类型
     */
    @ApiParam("梁型 --结构类型")
    @ExcelCell(index = 5, value = ExcelCellType.CELL_TYPE_STRING)
    private String beamType;

    /**
     * 跨度
     */
    @ApiParam("跨度")
    @ExcelCell(index = 6, value = ExcelCellType.CELL_TYPE_STRING)
    private Double beamSpan;

    /**
     * 直、曲线
     */
    @ApiParam("直、曲线")
    @ExcelCell(index = 7, value = ExcelCellType.CELL_TYPE_STRING)
    private String straightCurve;

    /**
     * 曲线半径
     */
    @ApiParam("曲线半径")
    @ExcelCell(index = 8, value = ExcelCellType.CELL_TYPE_STRING)
    private BigDecimal radiusOfCurve;

    /**
     * 钢筋种类
     */
    @ApiParam("钢筋种类")
    @ExcelCell(index = 9, value = ExcelCellType.CELL_TYPE_STRING)
    private String steelBarType;

    /**
     * 钢筋数量
     */
    @ApiParam("钢筋数量")
    @ExcelCell(index = 10, value = ExcelCellType.CELL_TYPE_STRING)
    private BigDecimal steelBarNumber;

    /**
     * PVC管
     */
    @ApiParam("PVC管")
    @ExcelCell(index = 11, value = ExcelCellType.CELL_TYPE_STRING)
    private String pvc;

    /**
     * 支座
     */
    @ApiParam("支座")
    @ExcelCell(index = 12, value = ExcelCellType.CELL_TYPE_STRING)
    private String support;

    /**
     * 吊点钢管
     */
    @ApiParam("吊点钢管")
    @ExcelCell(index = 13, value = ExcelCellType.CELL_TYPE_STRING)
    private String hangPointSteelPipe;

    /**
     * 波纹管规格
     */
    @ApiParam("波纹管规格")
    @ExcelCell(index = 14, value = ExcelCellType.CELL_TYPE_STRING)
    private String bellowsSpecification;

    /**
     * 内膜面积
     */
    @ApiParam("内膜面积")
    @ExcelCell(index = 15, value = ExcelCellType.CELL_TYPE_STRING)
    private BigDecimal endocardiumArea;

    /**
     * 制梁台座
     */
    @ApiParam("制梁台座")
    @ExcelCell(index = 16, value = ExcelCellType.CELL_TYPE_STRING)
    private String reinEngBeamPedestal;

    /**
     * 锚垫板规格
     */
    @ApiParam("锚垫板规格")
    @ExcelCell(index = 17, value = ExcelCellType.CELL_TYPE_STRING)
    private String specificationOfAnchoragePlate;

    /**
     * 锚垫板数量
     */
    @ApiParam("锚垫板数量")
    @ExcelCell(index = 18, value = ExcelCellType.CELL_TYPE_STRING)
    private BigDecimal anchorPlatesNumber;

    /**
     * 端模适用梁长
     */
    @ApiParam("端模适用梁长")
    @ExcelCell(index = 19, value = ExcelCellType.CELL_TYPE_STRING)
    private BigDecimal applicationOfBeamLength;

    /**
     * 接触轨槽道数量
     */
    @ApiParam("接触轨槽道数量")
    @ExcelCell(index = 20, value = ExcelCellType.CELL_TYPE_STRING)
    private BigDecimal contactRailChannelNumber;

    /**
     * 是否需要指形板板座
     */
    @ApiParam("是否需要指形板板座")
    @ExcelCell(index = 21, value = ExcelCellType.CELL_TYPE_STRING)
    private String isNeedFingerPlateSeat;

    /**
     * 锚具规格
     */
    @ApiParam("锚具规格")
    @ExcelCell(index = 22, value = ExcelCellType.CELL_TYPE_STRING)
    private String anchorageSpecification;

    /**
     * 锚具数量
     */
    @ApiParam("锚具数量")
    @ExcelCell(index = 23, value = ExcelCellType.CELL_TYPE_STRING)
    private BigDecimal anchorageNumber;

    /**
     * 钢丝线数量
     */
    @ApiParam("钢丝线数量")
    @ExcelCell(index = 24, value = ExcelCellType.CELL_TYPE_STRING)
    private BigDecimal wireNumber;

    /**
     * 压浆料数量
     */
    @ApiParam("压浆料数量")
    @ExcelCell(index = 25, value = ExcelCellType.CELL_TYPE_STRING)
    private BigDecimal quantityOfPressure;

    /**
     * 存梁台座号
     */
    @ApiParam("存梁台座号")
    @ExcelCell(index = 26, value = ExcelCellType.CELL_TYPE_STRING)
    private String beamStorageNumber;

    /**
     * 台座编号
     */
    @ApiParam("台座编号")
    @ExcelCell(index = 27, value = ExcelCellType.CELL_TYPE_STRING)
    private String pedestalNumber;

    /**
     * 钢筋绑扎完成时间
     */
    @ApiParam("钢筋绑扎完成时间")
    @ExcelCell(index = 28, value = ExcelCellType.CELL_TYPE_DATE)
    private Date reinEngCplBindTime;

    /**
     * 末班完成时间
     */
    @ApiParam("末班完成时间")
    @ExcelCell(index = 29, value = ExcelCellType.CELL_TYPE_DATE)
    private Date finalCompletionTime;

    /**
     * 浇筑时间
     */
    @ApiParam("浇筑时间")
    @ExcelCell(index = 30, value = ExcelCellType.CELL_TYPE_DATE)
    private Date pouringTime;

    /**
     * 张拉压浆时间
     */
    @ApiParam("张拉压浆时间")
    @ExcelCell(index = 31, value = ExcelCellType.CELL_TYPE_DATE)
    private Date presTensGroutPulping;

    /**
     * 用梁时间
     */
    @ApiParam("用梁时间")
    @ExcelCell(index = 32, value = ExcelCellType.CELL_TYPE_DATE)
    private Date useTime;

    /**
     * 状态  1未开始制梁 2开始制梁
     */
    @ApiParam("1未开始制梁 2开始制梁")
    @ExcelCell(index = 33, value = ExcelCellType.CELL_TYPE_STRING)
    private Integer status;

    /**
     * 制梁计划审核
     * 制梁计划状态：
     * 计划生成-待总包方审核状态-1
     * 总包方审核通过-待梁场审核状态-2
     * 总包方审核不通过或选择逻辑删除计划-更改为删除状态-5
     * 梁场审核通过-同意生产状态-4
     * 梁场审核不通过改为总包方待审核状态，梁场给出预计生产时间-3
     * 总包方查看梁场意见同意的话就变更用梁时间为梁场预计的梁出场时间，同意生产状态，-4
     */
    @ApiParam("制梁计划审核状态")
    private Integer planExamineStatus;

    /**
     * 梁场预计梁出场时间
     */
    @ApiParam("梁场预计梁出场时间,初始值为计划用梁时间")
    private String beamCompletionTime;

    /**
     * 审核进度
     */
    @ApiParam("工序审核状态,当前审核到哪一步了")
    private Integer examineProgress;

    /**
     * 最后审核时间
     */
    @ApiParam("工序审核最后审核时间")
    private Date examineTime;

    /**
     * 备注
     */
    @ApiParam("备注")
    @ExcelCell(index = 34, value = ExcelCellType.CELL_TYPE_STRING)
    private String note;
}
