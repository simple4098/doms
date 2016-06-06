package com.fanqielaile.toms.exception;


public class DmsException extends Exception{


	private static final long serialVersionUID = 1L;
	
	public DmsException(String msg) {
		super(msg);
	}
	
	public DmsException() {
		super("请求携程出错");
	}

}
