package com.tomasky.doms.support.exception;

/**
 * Create by jame
 * Date: 2016/6/7
 * Version: 1.0
 * DOTO: pms提供给去呼呼接口异常
 */
public class ProvidToQunarApiException extends RuntimeException {
    private final static String MSG = "pms提供给去呼呼接口异常：";

    public ProvidToQunarApiException(String msg) {
        super(MSG + msg);
    }

    public ProvidToQunarApiException(String msg, Exception e) {
        super(MSG + msg, e);
    }
}
