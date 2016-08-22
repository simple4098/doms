package com.tomasky.doms.dto.push;

/**
 * Create by jame
 * Date: 2016/8/19 10:34
 * Version: 1.0
 * Description: 阐述
 */
public class BatchSyncRoomPricePriceListBo {
    private String fromDate;
    private String toDate;
    private String basePrice;//底价
    private String sellPrice;//卖价
    private String commissionPrice;

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
}
