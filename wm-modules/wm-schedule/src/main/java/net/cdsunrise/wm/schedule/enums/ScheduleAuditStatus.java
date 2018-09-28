package net.cdsunrise.wm.schedule.enums;

/***
 * @author gechaoqing
 * 进度计划审核状态
 */
public enum ScheduleAuditStatus {
    /***
     *
     */
    WAIT_AUDIT("待审核"),
    PASS("审核通过"),
    /***
     *
     */
    UN_PASS("审核不通过");
    ScheduleAuditStatus(String desc){
        this.desc = desc;
    }
    private String desc;
    public String getDesc(){
        return this.desc;
    }
}
