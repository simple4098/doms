package com.tomasky.doms.enums;

/**
 * Created by Administrator on 2016/6/14.
 */
public enum OrderReasonType {
    r3(3, "全部满房"),
    r4(4, "到店时间过晚"),
    r5(5, "价格不符"),
    r6(6, "需要担保"),
    r7(7, "特殊要求无法满足"),
    r8(8, "重复订单"),
    r9(9, "其他原因"),
    r20(20, "酒店装修/停业"),
    r24(24, "部分满房 ");

    OrderReasonType(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public static OrderReasonType getOrderGetReasonType(int key) {
        for (OrderReasonType o : OrderReasonType.values()) {
            if (o.getKey() == key) {
                return o;
            }
        }
        return OrderReasonType.r9;
    }

    private int key;
    private String value;

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
