package com.tomasky.doms.support.util;

import com.tomasky.doms.common.CommonApi;

/**
 * DESC : 去哪儿url工具类
 * @author : 番茄木-ZLin
 * @data : 2016/6/7
 * @version: v1.0.0
 */
public class QunarUrlUtil {
    private QunarUrlUtil(){};

    /**
     * 去哪儿发送短信接口地址
     */
    public static String sendCodeUrl(){
        String sendUrl = ResourceBundleUtil.getString("qunar.sendCode.uri");
        return CommonApi.qunarUrl+sendUrl;
    }

    /**
     * 去哪儿开通渠道接口地址
     */
    public static String openAccountUrl(){
        String sendUrl = ResourceBundleUtil.getString("qunar.openAccount.uri");
        return CommonApi.qunarUrl+sendUrl;
    }

    /**
     * 去哪儿关闭渠道接口地址
     */
    public static String closeAccountUrl(){
        String sendUrl = ResourceBundleUtil.getString("qunar.closeAccount.uri");
        return CommonApi.qunarUrl+sendUrl;
    }
    /**
     * 去哪儿查询渠道酒店列表接口地址
     */
    public static String searchHotelListUrl(){
        String sendUrl = ResourceBundleUtil.getString("qunar.searchHotelList.uri");
        return CommonApi.qunarUrl+sendUrl;
    }
}
