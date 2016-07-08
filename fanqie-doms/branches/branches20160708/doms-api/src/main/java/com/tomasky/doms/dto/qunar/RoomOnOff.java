package com.tomasky.doms.dto.qunar;

/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2016/6/14
 * @version: v1.0.0
 */
public class RoomOnOff extends QunarDockingRemovePhyRoomType{

    //开始时间
    private String fromDate;
    //结束时间
    private String toDate;

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
}
