package com.tomasky.doms.dto.qunar.response;

/**
 * Create by jame
 * Date: 2016/6/7
 * Version: 1.0
 * DOTO: data
 */
public class QunarDataResult extends QunarResult {
    private Object data;

    public QunarDataResult() {
    }

    public QunarDataResult(Integer code,String msg,Object data) {
        super();
        this.setCode(code.toString());
        this.setMsg(msg);
        this.setData(data);
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
