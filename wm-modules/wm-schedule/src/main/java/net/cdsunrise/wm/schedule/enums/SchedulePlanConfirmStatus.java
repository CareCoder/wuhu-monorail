package net.cdsunrise.wm.schedule.enums;

/***
 * @author gechaoqing
 * 进度计划编制后确认状态
 */
public enum SchedulePlanConfirmStatus {
    /**
     * 计划可行
     */
    OK,
    /***
     * 计划不可行
     */
    NOT_OK,
    /***
     * 无状态，待确认
     */
    NONE,
    /***
     * 草稿状态
     */
    DRAFT
}
