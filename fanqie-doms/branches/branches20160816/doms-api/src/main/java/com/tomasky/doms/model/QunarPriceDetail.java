package com.tomasky.doms.model;

/**
 * Created by Administrator on 2016/6/14.
 * 去哪儿每日价格信息
 */
public class QunarPriceDetail {
    private String date;
    private String price;
    private String breakfast;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(String breakfast) {
        this.breakfast = breakfast;
    }
}
