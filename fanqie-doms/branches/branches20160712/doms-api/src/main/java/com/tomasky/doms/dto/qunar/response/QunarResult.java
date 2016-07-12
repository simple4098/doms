package com.tomasky.doms.dto.qunar.response;

/**
 * DESC :去哪儿返回数据
 * @author : 番茄木-ZLin
 * @data : 2016/6/6
 * @version: v1.0.0
 */
public class QunarResult {

    private String code;

    private String msg;

    public QunarResult() {
    }

    public QunarResult(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
