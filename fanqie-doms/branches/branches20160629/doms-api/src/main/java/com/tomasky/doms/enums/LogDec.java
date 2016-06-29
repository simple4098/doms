package com.tomasky.doms.enums;

/**
 * DESC : 日志类型(直连渠道日志)
 * @author : 番茄木-ZLin
 * @data : 2016/1/28
 * @version: v1.0.0
 */
public enum LogDec {
    SEND_CODE(107,101,"发送验证码"),OPEN_ACCOUNT(107,102,"开通渠道");
    //父id
    private Integer pId;
    //子id
    private Integer logTypeId;
    //描述
    private String value;

    LogDec(Integer pId, Integer logTypeId, String value) {
        this.pId = pId;
        this.logTypeId = logTypeId;
        this.value = value;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public Integer getLogTypeId() {
        return logTypeId;
    }

    public void setLogTypeId(Integer logTypeId) {
        this.logTypeId = logTypeId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
