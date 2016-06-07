package com.tomasky.doms.enums;

/**
 * DESC : 渠道枚举
 * @author : 番茄木-ZLin
 * @data : 2016/6/7
 * @version: v1.0.0
 */
public enum OtaCode {
    QUNAR("去哪儿");
    private String value;

    OtaCode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
