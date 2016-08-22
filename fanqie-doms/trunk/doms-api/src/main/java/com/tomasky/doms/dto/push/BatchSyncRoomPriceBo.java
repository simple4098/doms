package com.tomasky.doms.dto.push;

import com.tomasky.doms.dto.qunar.QunarBase;

import java.util.List;

/**
 * Create by jame
 * Date: 2016/8/19 10:29
 * Version: 1.0
 * Description: 去呼呼批量同步房价bo
 */
public class BatchSyncRoomPriceBo extends QunarBase {
    private String hotelNo;
    private List<BatchSyncRoomPriceProductListBo> productList;

    public String getHotelNo() {
        return hotelNo;
    }

    public void setHotelNo(String hotelNo) {
        this.hotelNo = hotelNo;
    }

    public List<BatchSyncRoomPriceProductListBo> getProductList() {
        return productList;
    }

    public void setProductList(List<BatchSyncRoomPriceProductListBo> productList) {
        this.productList = productList;
    }
}
