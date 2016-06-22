package com.tomasky.doms.dto.qunar;

import com.tomasky.doms.model.QunarOrder;

import java.util.List;

/**
 * Created by Administrator on 2016/6/14.
 */
public class QunarCheckInRequest extends QunarBase {
    //pms酒店id
    private String hotelNo;
    //渠道订单号
    private String channelOrderNo;
    //订单金额
    private String orderPrice;
    //订单客人姓名
    private String customerName;
    //订单联系人姓名
    private String contactorName;
    //订单联系人手机号码
    private String contactTelNo;
    //PMS 物理房型代码
    private String phyRoomTypeCode;
    //订单信息
    private List<CheckInOrder> orderList;

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

    public String getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(String orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getContactorName() {
        return contactorName;
    }

    public void setContactorName(String contactorName) {
        this.contactorName = contactorName;
    }

    public String getContactTelNo() {
        return contactTelNo;
    }

    public void setContactTelNo(String contactTelNo) {
        this.contactTelNo = contactTelNo;
    }

    public String getPhyRoomTypeCode() {
        return phyRoomTypeCode;
    }

    public void setPhyRoomTypeCode(String phyRoomTypeCode) {
        this.phyRoomTypeCode = phyRoomTypeCode;
    }

    public List<CheckInOrder> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<CheckInOrder> orderList) {
        this.orderList = orderList;
    }

    /**
     * 得到去哪儿入住请求参数
     *
     * @param qunarOrder
     * @return
     */
    public static QunarCheckInRequest getCheckInOrderParamRequest(QunarOrder qunarOrder) {
        QunarCheckInRequest qunarCheckInRequest = new QunarCheckInRequest();
        qunarCheckInRequest.setHotelNo(qunarOrder.getHotelNo());
        qunarCheckInRequest.setChannelOrderNo(qunarOrder.getChannelOrderNo());
        qunarCheckInRequest.setContactorName(qunarOrder.getContactorName());
        qunarCheckInRequest.setContactTelNo(qunarOrder.getContactTelNo());
        qunarCheckInRequest.setCustomerName(qunarOrder.getCustomerName());
        qunarCheckInRequest.setOrderPrice(qunarOrder.getOrderPrice());
        qunarCheckInRequest.setPhyRoomTypeCode(qunarOrder.getRoomTypeCode());
        qunarCheckInRequest.setOrderList(CheckInOrder.getOrderList(qunarOrder));
        return qunarCheckInRequest;
    }
}
