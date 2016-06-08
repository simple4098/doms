package com.tomasky.doms.common;

/**
 * DESC : 请求数据中心接口
 * @author : 番茄木-ZLin
 * @data : 2015/5/27
 * @version: v1.0.0
 */
public class CommonApi {
    public static String userIp;

    public static String pmsId;

    public static String qunarHostApi;

    public static String qunarUrl ;

    public static String signkey;
    public static String version;


    public static String getUserIp() {
        return userIp;
    }

    public  void setUserIp(String userIp) {
        CommonApi.userIp = userIp;
    }

    public static String getPmsId() {
        return pmsId;
    }

    public  void setPmsId(String pmsId) {
        CommonApi.pmsId = pmsId;
    }

    public static String getQunarHostApi() {
        return qunarHostApi;
    }

    public  void setQunarHostApi(String qunarHostApi) {
        CommonApi.qunarHostApi = qunarHostApi;
    }

    public static String getSignkey() {
        return signkey;
    }

    public  void setSignkey(String signkey) {
        CommonApi.signkey = signkey;
    }

    public static String getVersion() {
        return version;
    }

    public  void setVersion(String version) {
        CommonApi.version = version;
    }

    public static String getQunarUrl() {
        qunarUrl =  CommonApi.qunarHostApi+CommonApi.pmsId;
        return qunarUrl;
    }


}
