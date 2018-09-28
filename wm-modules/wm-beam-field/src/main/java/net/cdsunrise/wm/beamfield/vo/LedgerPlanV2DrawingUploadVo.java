package net.cdsunrise.wm.beamfield.vo;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * Author: WangRui
 * Time: 2018/6/3/003
 * Describe:
 */
@Data
public class LedgerPlanV2DrawingUploadVo {

    /**
     * 数据id
     */
    @ApiParam("数据id")
    private Long id;

    /**
     * 支座类型
     */
    @ApiParam("支座类型-1")
    private MultipartFile bearingType;

    /**
     * 端模安装参数
     */
    @ApiParam("端模安装参数-2")
    private MultipartFile endInstallationParameters;


    /**
     * 梁顶底尺寸
     */
    @ApiParam("梁顶底尺寸-3")
    private MultipartFile beamTopBottomSize;


    /**
     * 反拱及超高
     */
    @ApiParam("反拱及超高-4")
    private MultipartFile antiArchAndSuperHigh;


    /**
     * 钢筋间距
     */
    @ApiParam("钢筋间距-5")
    private MultipartFile barSpacing;


    /**
     * 钢束形状及长度
     */
    @ApiParam("钢束形状及长度-6")
    private MultipartFile steelBeamShapeLength;


    /**
     * 波纹管定位数据
     */
    @ApiParam("波纹管定位数据-7")
    private MultipartFile bellowsPositioningData;


    /**
     * 梁体构造图
     */
    @ApiParam("梁体构造图-8")
    private MultipartFile beamBodyStructureDiagram;

    /**
     * 端模尺寸及形状
     */
    @ApiParam("端模尺寸及形状-9")
    private MultipartFile endDieSizeShape;

    /**
     * 钢筋构造图
     */
    @ApiParam("钢筋构造图-10")
    private MultipartFile steelBarStructuralDrawing;

    /**
     * 钢束编号及规格
     */
    @ApiParam("钢束编号及规格-11")
    private MultipartFile steelBeamNumberSpecification;

    /**
     * 预埋件类型及数据量
     */
    @ApiParam("预埋件类型及数据量-12")
    private MultipartFile embeddedPartsTypeData;

    @ApiParam("备注")
    private String note;
}