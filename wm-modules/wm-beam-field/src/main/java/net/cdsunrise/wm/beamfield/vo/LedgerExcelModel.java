package net.cdsunrise.wm.beamfield.vo;

import lombok.Data;
import net.cdsunrise.wm.base.annotation.ExcelCell;
import net.cdsunrise.wm.base.annotation.ExcelCellType;

import java.util.Date;

/**
 * Author : WangRui
 * Date : 2018/4/20
 *
 * Describe :
 */
@Data
public class LedgerExcelModel {
    /**
     * 梁号
     */
    @ExcelCell(index = 1,value = ExcelCellType.CELL_TYPE_STRING)
    private String beamNumber;

    /**
     * 梁型线路区间
     */
    @ExcelCell(index = 2,value = ExcelCellType.CELL_TYPE_STRING)
    private String beamTypeLineInterval;

    /**
     * 梁型线路
     */
    @ExcelCell(index = 3,value = ExcelCellType.CELL_TYPE_STRING)
    private String beamTypeLine;

    /**
     * 梁型左、右线
     */
    @ExcelCell(index = 4,value = ExcelCellType.CELL_TYPE_STRING)
    private String beamTypeLeftOrRightLine;

    /**
     * 梁型跨度
     */
    @ExcelCell(index = 5,value = ExcelCellType.CELL_TYPE_NUMERIC)
    private Double beamTypeSpan;

    /**
     * 梁型结构类型
     */
    @ExcelCell(index = 6,value = ExcelCellType.CELL_TYPE_STRING)
    private String beamTypeStructureType;

    /**
     * 梁型直、曲线
     */
    @ExcelCell(index = 7,value = ExcelCellType.CELL_TYPE_STRING)
    private String beamTypeStraightCurve;

    /**
     *预埋件疏散通道
     */
    @ExcelCell(index = 8,value = ExcelCellType.CELL_TYPE_NUMERIC)
    private Double edPartsEscapeRoute;

    /**
     *预埋件接触轨槽道
     */
    @ExcelCell(index = 9,value = ExcelCellType.CELL_TYPE_NUMERIC)
    private Double edPartsContactRailChannel;

    /**
     *预埋件信标
     */
    @ExcelCell(index = 10,value = ExcelCellType.CELL_TYPE_NUMERIC)
    private Double emdPartsBeacon;

    /**
     *预埋件支撑型钢
     */
    @ExcelCell(index = 11,value = ExcelCellType.CELL_TYPE_NUMERIC)
    private Double edPartsSupportingSteel;

    /**
     *预埋件PVC管
     */
    @ExcelCell(index = 12,value = ExcelCellType.CELL_TYPE_NUMERIC)
    private Double edPartsPVC;

    /**
     * 波纹管
     */
    @ExcelCell(index = 13,value = ExcelCellType.CELL_TYPE_NUMERIC)
    private Double corrugatedPipe;

    /**
     * 制梁台座
     */
    @ExcelCell(index = 14,value = ExcelCellType.CELL_TYPE_STRING)
    private String entablement;

    /**
     * 模板
     */
    @ExcelCell(index = 15,value = ExcelCellType.CELL_TYPE_STRING)
    private String template;

    /**
     * 钢筋绑扎完成时间
     */
    @ExcelCell(index = 16,value = ExcelCellType.CELL_TYPE_DATE)
    private Date cplTimeOfSteelBarB;

    /**
     * 合模时间
     */
    @ExcelCell(index = 17,value = ExcelCellType.CELL_TYPE_DATE)
    private Date moldTime;

    /**
     * 砼浇筑开始时间
     */
    @ExcelCell(index = 18,value = ExcelCellType.CELL_TYPE_DATE)
    private Date ctPouringBeginTime;

    /**
     * 砼浇筑结束时间
     */
    @ExcelCell(index = 19,value = ExcelCellType.CELL_TYPE_DATE)
    private Date ctPouringEndTime;

    /**
     * 砼浇筑方量
     */
    @ExcelCell(index = 20,value = ExcelCellType.CELL_TYPE_NUMERIC)
    private Double ctPouringSquare;

    /**
     * 模板拆除时间
     */
    @ExcelCell(index = 21,value = ExcelCellType.CELL_TYPE_DATE)
    private Date templateDismantlingTime;

    /**
     * 初张拉时间
     */
    @ExcelCell(index = 22,value = ExcelCellType.CELL_TYPE_DATE)
    private Date initialTensioningTime;

    /**
     * 移梁时间
     */
    @ExcelCell(index = 23,value = ExcelCellType.CELL_TYPE_DATE)
    private Date beamShiftingTime;

    /**
     *存梁台座号
     */
    @ExcelCell(index = 24,value = ExcelCellType.CELL_TYPE_STRING)
    private String beamStorageNumber;

    /**
     * 终张拉时间
     */
    @ExcelCell(index = 25,value = ExcelCellType.CELL_TYPE_DATE)
    private Date endTensioningTime;

    /**
     * 压浆时间
     */
    @ExcelCell(index = 26,value = ExcelCellType.CELL_TYPE_DATE)
    private Date pulpingTime;

    /**
     * 封端时间
     */
    @ExcelCell(index = 27,value = ExcelCellType.CELL_TYPE_DATE)
    private Date sealingEndTime;

    /**
     *防水层时间
     */
    @ExcelCell(index = 28,value = ExcelCellType.CELL_TYPE_DATE)
    private Date waterproofLayerTime;
}
