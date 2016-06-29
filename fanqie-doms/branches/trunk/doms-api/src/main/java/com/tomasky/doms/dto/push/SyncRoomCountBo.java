package com.tomasky.doms.dto.push;

import com.tomasky.doms.dto.qunar.QunarBase;

/**
 * Create by jame
 * Date: 2016/6/12
 * Version: 1.0
 * Description: 阐述
 */
public class SyncRoomCountBo extends QunarBase{
    private String hotelNo;
    private String fromDate;
    private String toDate;
    private String phyRoomTypeCode;
    private String count;
    private String remark;

    public SyncRoomCountBo(String hotelNo, String fromDate, String toDate, String phyRoomTypeCode, String count, String remark) {
        this.hotelNo = hotelNo;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.phyRoomTypeCode = phyRoomTypeCode;
        this.count = count;
        this.remark = remark;
    }

    public String getHotelNo() {
        return hotelNo;
    }

    public void setHotelNo(String hotelNo) {
        this.hotelNo = hotelNo;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getPhyRoomTypeCode() {
        return phyRoomTypeCode;
    }

    public void setPhyRoomTypeCode(String phyRoomTypeCode) {
        this.phyRoomTypeCode = phyRoomTypeCode;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
