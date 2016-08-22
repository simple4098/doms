package com.tomasky.doms.enums;

/**
 * Created by wangdayin on 2016/1/13.
 * 众荟相应的type
 */
public enum ResponseType {
    R24("24"), R10("10");
    private String text;

    ResponseType(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
