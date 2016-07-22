package com.tomasky.doms.dto.qunar;

import com.tomasky.doms.enums.QunarRatePlanEnum;

/**
 * DESC : 产品匹配参数对象
 * @author : 番茄木-ZLin
 * @data : 2016/6/12
 * @version: v1.0.0
 */
public class QunarDockingPhyRoomType extends QunarDockingRemovePhyRoomType{

    //渠道房型名称
    private String channelPhyRoomTypeName;
    //oms房型名称
    private String phyRoomTypeName;
    //oms 价格计划名称
    private String ratePlanName= QunarRatePlanEnum.ratePlanName.getValue();
    //渠道价格计划名称
    private String channelRatePlanName;


    public String getChannelPhyRoomTypeName() {
        return channelPhyRoomTypeName;
    }

    public void setChannelPhyRoomTypeName(String channelPhyRoomTypeName) {
        this.channelPhyRoomTypeName = channelPhyRoomTypeName;
    }

    public String getPhyRoomTypeName() {
        return phyRoomTypeName;
    }

    public void setPhyRoomTypeName(String phyRoomTypeName) {
        this.phyRoomTypeName = phyRoomTypeName;
    }

    public String getRatePlanName() {
        return ratePlanName;
    }

    public void setRatePlanName(String ratePlanName) {
        this.ratePlanName = ratePlanName;
    }

    public String getChannelRatePlanName() {
        return channelRatePlanName;
    }

    public void setChannelRatePlanName(String channelRatePlanName) {
        this.channelRatePlanName = channelRatePlanName;
    }
}
