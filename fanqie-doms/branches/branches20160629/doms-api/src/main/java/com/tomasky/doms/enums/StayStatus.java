package com.tomasky.doms.enums;

/**
 * Created by Administrator on 2016/6/14.
 */
public enum StayStatus {
    s1("01", "未入住"),
    s2("02", "在店"),
    s3("03", "已离店"),
    s4("04", "未入住换房"),
    s5("05", "取消"),
    s6("06", "已入住换房"),
    s7("07", "noshow 失约未到");
    private String key;
    private String value;

    StayStatus(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
