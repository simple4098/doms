package com.tomasky.doms.dto.qunar;

import com.tomasky.doms.enums.QunarRatePlanEnum;

/**
 * DESC : 解除产品匹配对象
 * @author : 番茄木-ZLin
 * @data : 2016/6/12
 * @version: v1.0.0
 */
public class QunarDockingRemovePhyRoomType extends QunarBase {
    //oms酒店code
    private String hotelNo;
    //渠道酒店code
    private String channelHotelNo;
    //PMS 本地物理房型 代码
    private String phyRoomTypeCode;
    //渠道物理房型code
    private String channelPhyRoomTypeCode;
    //渠道价格计划code
    private String channelRatePlanCode;
    //oms 房价代码
    private String ratePlanCode= QunarRatePlanEnum.ratePlanCode.getValue();

    public String getHotelNo() {
        return hotelNo;
    }

    public void setHotelNo(String hotelNo) {
        this.hotelNo = hotelNo;
    }

    public String getChannelHotelNo() {
        return channelHotelNo;
    }

    public void setChannelHotelNo(String channelHotelNo) {
        this.channelHotelNo = channelHotelNo;
    }

    public String getPhyRoomTypeCode() {
        return phyRoomTypeCode;
    }

    public void setPhyRoomTypeCode(String phyRoomTypeCode) {
        this.phyRoomTypeCode = phyRoomTypeCode;
    }

    public String getChannelPhyRoomTypeCode() {
        return channelPhyRoomTypeCode;
    }

    public void setChannelPhyRoomTypeCode(String channelPhyRoomTypeCode) {
        this.channelPhyRoomTypeCode = channelPhyRoomTypeCode;
    }

    public String getChannelRatePlanCode() {
        return channelRatePlanCode;
    }

    public void setChannelRatePlanCode(String channelRatePlanCode) {
        this.channelRatePlanCode = channelRatePlanCode;
    }

    public String getRatePlanCode() {
        return ratePlanCode;
    }

    public void setRatePlanCode(String ratePlanCode) {
        this.ratePlanCode = ratePlanCode;
    }
}
