package com.tomasky.doms.dto.qunar;

/**
 * DESC :
 *
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
}
