package net.cdsunrise.wm.schedule.enums;

/***
 * @author gechaoqing
 * 进度计划状态
 */
public enum SchedulePlanStatus {
    //未开工
    NOT_START("未开工"),
    //已开工
    PROCESSING("已开工"),
    //审核中
    AUDITING("审核中"),
    //已完工
    COMPLETE("已完工"),
    //已删除
    DELETE("已删除");
    SchedulePlanStatus(String desc){
        this.desc = desc;
    }
    private String desc;

    public String getDesc() {
        return desc;
    }
}
