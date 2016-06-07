package com.tomasky.doms.common;

/**
 * DESC : 请求数据中心接口
 * @author : 番茄木-ZLin
 * @data : 2015/5/27
 * @version: v1.0.0
 */
public class CommonApi {
    public static   String userIp;


    public static String getUserIp() {
        return userIp;
    }

    public  void setUserIp(String userIp) {
        CommonApi.userIp = userIp;
    }

}
