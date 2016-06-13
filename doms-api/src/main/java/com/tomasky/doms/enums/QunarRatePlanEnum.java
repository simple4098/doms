package com.tomasky.doms.enums;

/**
 * Create by jame
 * Date: 2016/6/6
 * Version: 1.0
 * DOTO:  去哪儿直连价格计划
 */
public enum QunarRatePlanEnum {
    ratePlanCode("QR001"),
    ratePlanName("价格优惠-预付");

    QunarRatePlanEnum(String value) {
        this.value = value;
    }

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
