package com.tomasky.doms.dto.qunar;

import com.tomasky.doms.model.QunarOrder;

/**
 * Created by Administrator on 2016/6/14.
 */
public class QunarConfirmOrderRequest extends QunarBase {

    //pms酒店code
    private String hotelNo;
    //渠道订单号
    private String channelOrderNo;
    //确认号
    private String confirmNo;

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

    public String getConfirmNo() {
        return confirmNo;
    }

    public void setConfirmNo(String confirmNo) {
        this.confirmNo = confirmNo;
    }

    /**
     * 得到接收订单请求参数
     *
     * @param qunarOrder
     * @return
     */
    public static QunarConfirmOrderRequest getConfirmOrderParamRequest(QunarOrder qunarOrder) {
        QunarConfirmOrderRequest qunarConfirmOrderRequest = new QunarConfirmOrderRequest();
        qunarConfirmOrderRequest.setHotelNo(qunarOrder.getHotelNo());
        qunarConfirmOrderRequest.setChannelOrderNo(qunarOrder.getChannelOrderNo());
        qunarConfirmOrderRequest.setConfirmNo(qunarOrder.getConfirmNo());
        return qunarConfirmOrderRequest;
    }
}
