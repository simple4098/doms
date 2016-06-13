package com.tomasky.doms.dto.qunar;

/**
 * DESC : PMS 物理房型删除通知对象
 * @author : 番茄木-ZLin
 * @data : 2016/6/13
 * @version: v1.0.0
 */
public class QunarDeletePhyRoomType extends QunarBaseBean {

    private String hotelNo;
    private String phyRoomTypeCode;

    public String getHotelNo() {
        return hotelNo;
    }

    public void setHotelNo(String hotelNo) {
        this.hotelNo = hotelNo;
    }

    public String getPhyRoomTypeCode() {
        return phyRoomTypeCode;
    }

    public void setPhyRoomTypeCode(String phyRoomTypeCode) {
        this.phyRoomTypeCode = phyRoomTypeCode;
    }
}
