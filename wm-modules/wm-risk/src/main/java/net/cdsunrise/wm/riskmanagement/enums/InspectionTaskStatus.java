package net.cdsunrise.wm.riskmanagement.enums;

/**
 * @author lijun
 * @date 2018-04-20.
 * @descritpion
 */
public enum InspectionTaskStatus {
    COMPLETED("已完成"),
    UNDONE("未完成");

    private String desc;

    InspectionTaskStatus(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
