package com.tomasky.doms.dto.qunar;

import com.tomasky.doms.enums.StayStatus;

/**
 * Created by Administrator on 2016/6/14.
 */
public class Stay {
    //入住客人姓名
    private String guestName;
    //真实入住时间
    private String realCheckInTime;
    //真实离店时间
    private String realCheckOutTime;
    //PMS 物理房型代码
    private String phyRoomTypeCode;
    //入住房间号
    private String roomNo;
    //入住状态代码
    private String stayStatus;
    //备注信息
    private String remark;

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public String getRealCheckInTime() {
        return realCheckInTime;
    }

    public void setRealCheckInTime(String realCheckInTime) {
        this.realCheckInTime = realCheckInTime;
    }

    public String getRealCheckOutTime() {
        return realCheckOutTime;
    }

    public void setRealCheckOutTime(String realCheckOutTime) {
        this.realCheckOutTime = realCheckOutTime;
    }

    public String getPhyRoomTypeCode() {
        return phyRoomTypeCode;
    }

    public void setPhyRoomTypeCode(String phyRoomTypeCode) {
        this.phyRoomTypeCode = phyRoomTypeCode;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public String getStayStatus() {
        return stayStatus;
    }

    public void setStayStatus(String stayStatus) {
        this.stayStatus = stayStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
