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
        return CommonApi.getQunarUrl()+sendUrl;
    }

    /**
     * 去哪儿开通渠道接口地址
     */
    public static String openAccountUrl(){
        String sendUrl = ResourceBundleUtil.getString("qunar.openAccount.uri");
        return CommonApi.getQunarUrl()+sendUrl;
    }

    /**
     * 去哪儿关闭渠道接口地址
     */
    public static String closeAccountUrl(){
        String sendUrl = ResourceBundleUtil.getString("qunar.closeAccount.uri");
        return CommonApi.getQunarUrl()+sendUrl;
    }
    /**
     * 去哪儿查询渠道酒店列表接口地址
     */
    public static String searchHotelListUrl(){
        String sendUrl = ResourceBundleUtil.getString("qunar.searchHotelList.uri");
        return CommonApi.getQunarUrl()+sendUrl;
    }
    /**
     * 去哪儿查询渠道酒店列表接口地址
     */
    public static String matchHotelUrl(){
        String sendUrl = ResourceBundleUtil.getString("qunar.dockingHotel.uri");
        return CommonApi.getQunarUrl()+sendUrl;
    }

    /**
     * 去哪儿房型列表接口地址
     * @return
     */
    public static String searchRoomTypeUrl(){
        String sendUrl = ResourceBundleUtil.getString("qunar.roomType.uri");
        return CommonApi.getQunarUrl()+sendUrl;
    }

    /**
     * 解除酒店匹配接口地址
     * @return
     */
    public static String removeDockingHotelUrl(){
        String sendUrl = ResourceBundleUtil.getString("qunar.removeDockingHotelUrl.uri");
        return CommonApi.getQunarUrl()+sendUrl;
    }

    /**
     * 匹配房型接口地址
     * @return
     */
    public static String matchRoomTypeUrl(){
        String sendUrl = ResourceBundleUtil.getString("qunar.dockingPhyRoomType.uri");
        return CommonApi.getQunarUrl()+sendUrl;
    }

    /**
     * 解除房型匹配接口地址
     */
    public static String removeMatchRoomTypeUrl(){
        String sendUrl = ResourceBundleUtil.getString("qunar.removeDockingPhyRoomType.uri");
        return CommonApi.getQunarUrl()+sendUrl;
    }

    /**
     * 5.1 渠道产品信息查询
     */
    public static String productionSearchUrl(){
        String sendUrl = ResourceBundleUtil.getString("qunar.searchChannelProduction.uri");
        return CommonApi.getQunarUrl()+sendUrl;
    }

    /**
     * #5.2 匹配渠道产品信息
     */
    public static String productionDockingUrl(){
        String sendUrl = ResourceBundleUtil.getString("qunar.dockingProduction.uri");
        return CommonApi.getQunarUrl()+sendUrl;
    }
    /**
     * #5.3 查询产品级信息匹配关系
     */
    public static String productionDockingSearchUrl(){
        String sendUrl = ResourceBundleUtil.getString("qunar.searchProductionDocking.uri");
        return CommonApi.getQunarUrl()+sendUrl;
    }

    /**
     * #5.4 解除产品匹配
     */
    public static String productionRemoveDockingUrl(){
        String sendUrl = ResourceBundleUtil.getString("qunar.removeDockingProduction.uri");
        return CommonApi.getQunarUrl()+sendUrl;
    }

    /**
     * #10.2 PMS 物理房型删除通知
     */
    public static String deletePhyRoomTypeUrl(){
        String sendUrl = ResourceBundleUtil.getString("qunar.deletePhyRoomType.uri");
        return CommonApi.getQunarUrl()+sendUrl;
    }

    /**
     * 开房接口地址
     * @return
     */
    public static String roomOn(){
        String roomOnUri = ResourceBundleUtil.getString("qunar.roomOn.uri");
        return CommonApi.getQunarUrl()+roomOnUri;
    }

    /**
     * 关房接口地址
     * @return
     */
    public static String roomOff(){
        String roomOffUri = ResourceBundleUtil.getString("qunar.roomOff.uri");
        return CommonApi.getQunarUrl()+roomOffUri;
    }
}
