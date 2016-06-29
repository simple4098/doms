package com.tomasky.doms.dto.oms;

/**
 * DESC : oms酒店解绑
 * @author : 番茄木-ZLin
 * @data : 2016/6/12
 * @version: v1.0.0
 */
public class OmsHotel {

    private String channelHotelNo;
    //private String innId;
    private String operatorGuid;
    private String operatorName;
    //private String otaId;
    private String accountId;


    public String getChannelHotelNo() {
        return channelHotelNo;
    }

    public void setChannelHotelNo(String channelHotelNo) {
        this.channelHotelNo = channelHotelNo;
    }


    public String getOperatorGuid() {
        return operatorGuid;
    }

    public void setOperatorGuid(String operatorGuid) {
        this.operatorGuid = operatorGuid;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    /*public String getOtaId() {
        return otaId;
    }

    public void setOtaId(String otaId) {
        this.otaId = otaId;
    }*/

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
