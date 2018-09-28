package net.cdsunrise.wm.prophase.entity;


import lombok.Data;


/**
 * Author : WangRui
 * Date : 2018/4/18
 * Describe :
 */
@Data
public class HDStatisticalData {
    /**
     * 房屋总数
     */
    private int houseTotalNumber;

    /**
     * 总面积
     */
    private String houseTotalArea;

    /**
     * 已拆迁数
     */
    private int demolished;

    /**
     * 已拆迁面积
     */
    private String demolishedArea;

    /**
     * 正在拆迁数
     */
    private int demolishing;

    /**
     * 正在拆迁面积
     */
    private String demolishingArea;

    /**
     * 未拆迁数
     */
    private int notDemolished;

    /**
     * 未拆迁面积
     */
    private String notDemolishedArea;
}
