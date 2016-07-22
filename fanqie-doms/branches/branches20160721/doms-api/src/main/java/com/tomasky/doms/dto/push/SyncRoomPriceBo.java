package com.tomasky.doms.dto.push;

import com.tomasky.doms.dto.qunar.QunarBase;
import com.tomasky.doms.enums.QunarRatePlanEnum;

/**
 * Create by jame
 * Date: 2016/6/12
 * Version: 1.0
 * Description: 阐述
 */
public class SyncRoomPriceBo extends QunarBase {
    private String hotelNo;
    private String fromDate;
    private String toDate;
    private String phyRoomTypeCode;
    private String ratePlanCode = QunarRatePlanEnum.ratePlanCode.getValue();
    private String basePrice;//底价
    private String sellPrice;//卖价
    private String commissionPrice; //佣金
    private String validWeek = "0,1,2,3,4,5,6";//【0：周日 1：周一 2：周二 3：周三 4：周四 5：周五 6：周六】逗号分隔

    public SyncRoomPriceBo(String hotelNo, String fromDate, String toDate, String phyRoomTypeCode, String ratePlanCode, String basePrice, String sellPrice, String commissionPrice, String validWeek) {
        this.hotelNo = hotelNo;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.phyRoomTypeCode = phyRoomTypeCode;
        this.ratePlanCode = ratePlanCode;
        this.basePrice = basePrice;
        this.sellPrice = sellPrice;
        this.commissionPrice = commissionPrice;
        this.validWeek = validWeek;
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

    public String getRatePlanCode() {
        return ratePlanCode;
    }

    public void setRatePlanCode(String ratePlanCode) {
        this.ratePlanCode = ratePlanCode;
    }

    public String getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(String basePrice) {
        this.basePrice = basePrice;
    }

    public String getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(String sellPrice) {
        this.sellPrice = sellPrice;
    }

    public String getCommissionPrice() {
        return commissionPrice;
    }

    public void setCommissionPrice(String commissionPrice) {
        this.commissionPrice = commissionPrice;
    }

    public String getValidWeek() {
        return validWeek;
    }

    public void setValidWeek(String validWeek) {
        this.validWeek = validWeek;
    }
}
