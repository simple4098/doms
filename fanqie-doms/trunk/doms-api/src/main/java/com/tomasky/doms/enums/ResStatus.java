package com.tomasky.doms.enums;

/**
 * Created by wangdayin on 2016/7/25.
 * 众荟订单审核同步状态
 */
public enum ResStatus {
    Confirmed("确认订单"), Cancelled("取消"), InHouse("入住"), CheckedOut("离店"), NoShow("未入住");

    private String text;

    ResStatus(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
