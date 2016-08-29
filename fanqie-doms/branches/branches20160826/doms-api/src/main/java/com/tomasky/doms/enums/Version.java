package com.tomasky.doms.enums;

/**
 * Created by wangdayin on 2016/1/12.
 */
public enum Version {
    V22("2.2"), v1003("1.003");
    private String text;

    Version(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
