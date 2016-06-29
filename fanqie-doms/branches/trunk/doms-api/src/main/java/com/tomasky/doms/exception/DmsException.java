package com.tomasky.doms.exception;


public class DmsException extends Exception {


    private static final long serialVersionUID = 1L;

    public DmsException(String message, Throwable cause) {
        super(message, cause);
    }

    public DmsException(String msg) {
        super(msg);
    }

    public DmsException() {
        super("渠道直连出错");
    }

}
