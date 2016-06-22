package com.tomasky.doms.dto.oms.response;

/**
 * Create by jame
 * Date: 2016/6/7
 * Version: 1.0
 * TODO: 调用oms接口返回model
 */
public class OmsResult {
    private Object data;
    private Integer status;
    private String type;
    private String message;

    public OmsResult() {
        super();
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
