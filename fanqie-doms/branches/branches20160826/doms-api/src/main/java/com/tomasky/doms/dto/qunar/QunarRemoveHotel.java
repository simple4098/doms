package com.tomasky.doms.dto.qunar;

/**
 * DESC : 解除酒店通知
 * @author : 番茄木-ZLin
 * @data : 2016/6/7
 * @version: v1.0.0
 */
public class QunarRemoveHotel extends QunarBaseBean {
    private String hotelNo;// PMS 酒店代码 String 否

    public String getHotelNo() {
        return hotelNo;
    }

    public void setHotelNo(String hotelNo) {
        this.hotelNo = hotelNo;
    }
}
