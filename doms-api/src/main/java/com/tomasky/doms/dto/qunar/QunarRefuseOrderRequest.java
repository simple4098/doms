package com.tomasky.doms.dto.qunar;

import com.tomasky.doms.enums.OrderReasonType;
import com.tomasky.doms.model.QunarOrder;

/**
 * Created by Administrator on 2016/6/14.
 */
public class QunarRefuseOrderRequest extends QunarBase {
    //pms酒店id
    private String hotelNo;
    //渠道订单号
    private String channelOrderNo;
    //拒绝原因
    private int reasonType;
    //拒绝原因描述
    private String reason;

    public String getHotelNo() {
        return hotelNo;
    }

    public void setHotelNo(String hotelNo) {
        this.hotelNo = hotelNo;
    }

    public String getChannelOrderNo() {
        return channelOrderNo;
    }

    public void setChannelOrderNo(String channelOrderNo) {
        this.channelOrderNo = channelOrderNo;
    }

    public int getReasonType() {
        return reasonType;
    }

    public void setReasonType(int reasonType) {
        this.reasonType = reasonType;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     * 得到请求拒绝订单参数
     *
     * @param qunarOrder
     * @param updateOrderRequest
     * @return
     */
    public static QunarRefuseOrderRequest getRefuseOrderParamRequest(QunarOrder qunarOrder, QunarUpdateOrderRequest updateOrderRequest) {
        QunarRefuseOrderRequest qunarRefuseOrderRequest = new QunarRefuseOrderRequest();
        qunarRefuseOrderRequest.setHotelNo(qunarOrder.getHotelNo());
        qunarRefuseOrderRequest.setChannelOrderNo(qunarOrder.getChannelOrderNo());
        OrderReasonType orderGetReasonType = OrderReasonType.getOrderGetReasonType(Integer.valueOf(updateOrderRequest.getRefuseType()));
        qunarRefuseOrderRequest.setReasonType(orderGetReasonType.getKey());
        qunarRefuseOrderRequest.setReason(orderGetReasonType.getValue());
        return qunarRefuseOrderRequest;
    }
}
