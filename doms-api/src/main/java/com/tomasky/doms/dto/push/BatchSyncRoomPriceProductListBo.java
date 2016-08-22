package com.tomasky.doms.dto.push;

import java.util.List;

/**
 * Create by jame
 * Date: 2016/8/19 10:33
 * Version: 1.0
 * Description: 去呼呼批量同步房价productList   bo
 */
public class BatchSyncRoomPriceProductListBo {
    private String phyRoomTypeCode;
    private String ratePlanCode;
    private List<BatchSyncRoomPricePriceListBo> priceList;

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

    public List<BatchSyncRoomPricePriceListBo> getPriceList() {
        return priceList;
    }

    public void setPriceList(List<BatchSyncRoomPricePriceListBo> priceList) {
        this.priceList = priceList;
    }
}
