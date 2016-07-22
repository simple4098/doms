package com.tomasky.doms.enums;

/**
 * ota
 */
public enum EnumOta {
    xz(101),//小站
    dx(102),//代销
    xyz(106),//信用住
    kf(111),//开放平台
    wg(1),//用于判断是否外挂业务
    qunar_conn(107),//去哪儿直连
    qunar(1),//去哪儿
    ctrip(2),//携程
    elong(3);//艺龙

    private int value;

    EnumOta(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
