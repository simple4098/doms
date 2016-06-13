package com.tomasky.doms.dto.qunar.response;

/**
 * DESC : 去哪儿产品详情
 * @author : 番茄木-ZLin
 * @data : 2016/6/12
 * @version: v1.0.0
 */
public class QunarProductionInfo {
    private String channelPhyRoomTypeCode;
    private String channelPhyRoomTypeName;
    private String channelRatePlanCode;//渠道价格计划代码",
    private String channelRatePlanName;//": "渠道价格计划名称",
    private String channelProductionCode;//": "渠道产品代码",
    private String channelProductionName;//": "渠道产品名称"

    public String getChannelPhyRoomTypeCode() {
        return channelPhyRoomTypeCode;
    }

    public void setChannelPhyRoomTypeCode(String channelPhyRoomTypeCode) {
        this.channelPhyRoomTypeCode = channelPhyRoomTypeCode;
    }

    public String getChannelPhyRoomTypeName() {
        return channelPhyRoomTypeName;
    }

    public void setChannelPhyRoomTypeName(String channelPhyRoomTypeName) {
        this.channelPhyRoomTypeName = channelPhyRoomTypeName;
    }

    public String getChannelRatePlanCode() {
        return channelRatePlanCode;
    }

    public void setChannelRatePlanCode(String channelRatePlanCode) {
        this.channelRatePlanCode = channelRatePlanCode;
    }

    public String getChannelRatePlanName() {
        return channelRatePlanName;
    }

    public void setChannelRatePlanName(String channelRatePlanName) {
        this.channelRatePlanName = channelRatePlanName;
    }

    public String getChannelProductionCode() {
        return channelProductionCode;
    }

    public void setChannelProductionCode(String channelProductionCode) {
        this.channelProductionCode = channelProductionCode;
    }

    public String getChannelProductionName() {
        return channelProductionName;
    }

    public void setChannelProductionName(String channelProductionName) {
        this.channelProductionName = channelProductionName;
    }
}
