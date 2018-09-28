package net.cdsunrise.wm.hiddentrouble.enums;

/**
 * @author lijun
 * @date 2018-04-26.
 * @descritpion
 */
public enum HiddenTroubleStatus {

    UNPROCESSED("未处理"),
    PROCESSED("已处理"),
    ACCEPTED("已验收");

    private String text;

    HiddenTroubleStatus(String text) {
        this.text = text;
    }
}
