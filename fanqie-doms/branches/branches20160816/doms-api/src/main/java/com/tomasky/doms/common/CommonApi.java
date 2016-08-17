package com.tomasky.doms.common;

/**
 * DESC : 请求数据中心接口
 *
 * @author : 番茄木-ZLin
 * @data : 2015/5/27
 * @version: v1.0.0
 */
public class CommonApi {
    public static String userIp;

    public static String pmsId;

    public static String qunarHostApi;

    public static String qunarUrl;

    public static String signkey;
    public static String version;
    public static String omsCreateOrder;

    public static String operatorGuid;
    public static String operatorName;
    public static String omsQunarBasicOrder;
    public static String omsUpdateQunarOrder;
    public static String otaRoomTypeIdUrl;
    public static String cancelOrderUrl;
    public static String updateQunarOrderStatus;
    public static String omsMainOrderByChannelOrderCode;
    public static String updateOmsOrderStatus;
    public static String omsRoomType;
    public static String omsRoomStatus;
    public static String omsAccountIdUrl;
    public static String omsCtripOrderInfo;


    public static void setQunarUrl(String qunarUrl) {
        CommonApi.qunarUrl = qunarUrl;
    }

    public static String getUserIp() {
        return userIp;
    }


    public void setUserIp(String userIp) {
        CommonApi.userIp = userIp;
    }

    public static String getPmsId() {
        return pmsId;
    }

    public void setPmsId(String pmsId) {
        CommonApi.pmsId = pmsId;
    }

    public static String getQunarHostApi() {
        return qunarHostApi;
    }

    public void setQunarHostApi(String qunarHostApi) {
        CommonApi.qunarHostApi = qunarHostApi;
    }

    public static String getSignkey() {
        return signkey;
    }

    public void setSignkey(String signkey) {
        CommonApi.signkey = signkey;
    }

    public static String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        CommonApi.version = version;
    }

    public static String getQunarUrl() {
        qunarUrl = CommonApi.qunarHostApi + CommonApi.pmsId;
        return qunarUrl;
    }

    public void setOperatorGuid(String operatorGuid) {
        this.operatorGuid = operatorGuid;
    }

    public void setOmsCreateOrder(String omsCreateOrder) {
        this.omsCreateOrder = omsCreateOrder;
    }

    public static String getOmsCreateOrder() {
        return omsCreateOrder;
    }

    public String getOperatorGuid() {
        return operatorGuid;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOmsQunarBasicOrder(String omsQunarBasicOrder) {
        this.omsQunarBasicOrder = omsQunarBasicOrder;
    }

    public static String getOmsQunarBasicOrder() {
        return omsQunarBasicOrder;
    }

    public void setOmsUpdateQunarOrder(String omsUpdateQunarOrder) {
        this.omsUpdateQunarOrder = omsUpdateQunarOrder;
    }

    public static String getOmsUpdateQunarOrder() {
        return omsUpdateQunarOrder;
    }

    public void setOtaRoomTypeIdUrl(String otaRoomTypeIdUrl) {
        this.otaRoomTypeIdUrl = otaRoomTypeIdUrl;
    }

    public static String getOtaRoomTypeIdUrl() {
        return otaRoomTypeIdUrl;
    }

    public void setCancelOrderUrl(String cancelOrderUrl) {
        this.cancelOrderUrl = cancelOrderUrl;
    }

    public static String getCancelOrderUrl() {
        return cancelOrderUrl;
    }

    public void setUpdateQunarOrderStatus(String updateQunarOrderStatus) {
        this.updateQunarOrderStatus = updateQunarOrderStatus;
    }

    public String getUpdateQunarOrderStatus() {
        return updateQunarOrderStatus;
    }

    public void setOmsMainOrderByChannelOrderCode(String omsMainOrderByChannelOrderCode) {
        this.omsMainOrderByChannelOrderCode = omsMainOrderByChannelOrderCode;
    }

    public static String getOmsMainOrderByChannelOrderCode() {
        return omsMainOrderByChannelOrderCode;
    }

    public void setUpdateOmsOrderStatus(String updateOmsOrderStatus) {
        this.updateOmsOrderStatus = updateOmsOrderStatus;
    }

    public static String getUpdateOmsOrderStatus() {
        return updateOmsOrderStatus;
    }

    public void setOmsRoomType(String omsRoomType) {
        this.omsRoomType = omsRoomType;
    }

    public static String getOmsRoomType() {
        return omsRoomType;
    }

    public void setOmsRoomStatus(String omsRoomStatus) {
        this.omsRoomStatus = omsRoomStatus;
    }

    public static String getOmsRoomStatus() {
        return omsRoomStatus;
    }

    public void setOmsAccountIdUrl(String omsAccountIdUrl) {
        this.omsAccountIdUrl = omsAccountIdUrl;
    }

    public static String getOmsAccountIdUrl() {
        return omsAccountIdUrl;
    }

    public void setOmsCtripOrderInfo(String omsCtripOrderInfo) {
        this.omsCtripOrderInfo = omsCtripOrderInfo;
    }

    public static String getOmsCtripOrderInfo() {
        return omsCtripOrderInfo;
    }
}
