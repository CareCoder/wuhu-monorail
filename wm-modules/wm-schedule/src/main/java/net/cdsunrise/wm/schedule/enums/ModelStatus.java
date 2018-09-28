package net.cdsunrise.wm.schedule.enums;

public enum ModelStatus {
    NOT_START("未开始", 1),
    DOING("建设中", 2),
    DONE("完成", 3);

    public String name;
    public int value;

    ModelStatus(String name, int value) {
        this.name = name;
        this.value = value;
    }
}
