package net.cdsunrise.wm.schedule.enums;

/***
 * @author gechaoqing
 * 计划分类
 */
public enum SchedulePlanCategory {
    /***
     * 年计划
     */
    YEAR("年计划"),
    /***
     * 月计划
     */
    MONTH("月计划"),
    /***
     * 周计划
     */
    WEEK("周计划"),
    /***
     * 总计划
     */
    MASTER("总计划");
    SchedulePlanCategory(String disc){
        this.disc = disc;
    }
    private String disc;

    public String getDisc() {
        return disc;
    }
}
