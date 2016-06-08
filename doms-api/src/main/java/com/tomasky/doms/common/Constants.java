package com.tomasky.doms.common;

public class Constants {
    public final static String ERROR_QUNAR = "-1";

    public final static String STATUS = "status";
    public static final boolean ERROR = false;
    /**
     * 返回结果
     */
    public final static String SUCCESS = "success";
    //去哪儿渠道 version号
    public final static String version = "1.0";
    //PMS 代码
    public final static String pmsId = "tomasky";
    // 渠道代码
    public final static String channelCode = "QUNAR";

    public final static String SUCCESS_QUNAR = "0";
    // http获取响应类型(all:所有，responseStr:网页字符串，cookies：网页cookies)
    public final static String HTTP_GET_TYPE_ALL = "all";
    public final static String HTTP_GET_TYPE_STRING = "responseStr";
    public final static String HTTP_GET_TYPE_COOKIES = "cookies";
    public final static int HTTP_SUCCESS = 200; //所有逻辑都正确
    public final static int HTTP_ERROR = 400; //访问正常但逻辑有误,app端要显示message信息
    public final static int HTTP_800 = 800;//订单重复 800
    public final static int HTTP_401 = 401;
    public final static int HTTP_404 = 404;
    public final static int HTTP_500 = 500;
    public final static int HTTP_411 = 411;//请求参数有误
    public final static int HTTP_412 = 412;//请求超时
    public final static int HTTP_413 = 413;//签名有误
}
