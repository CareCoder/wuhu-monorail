package net.cdsunrise.wm.schedule.enums;

/***
 * @author gechaoqing
 * 进度计划前置任务关系
 */
public enum SchedulePlanFrontRel {
    /**
     * 开始-开始
     */
    SS,
    /***
     * 开始-完成
     */
    SF,
    /***
     * 完成-完成
     */
    FF,
    /***
     * 完成-开始
     */
    FS
}
