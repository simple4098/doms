package com.tomasky.doms.dto.qunar;

/**
 * DESC : 查询渠道酒店
 * @author : 番茄木-ZLin
 * @data : 2016/6/7
 * @version: v1.0.0
 */
public class QunarAccountAndHotel extends QunarBase {

    //pms 酒店代码
    private String hotelNo;

    public String getHotelNo() {
        return hotelNo;
    }

    public void setHotelNo(String hotelNo) {
        this.hotelNo = hotelNo;
    }
}
