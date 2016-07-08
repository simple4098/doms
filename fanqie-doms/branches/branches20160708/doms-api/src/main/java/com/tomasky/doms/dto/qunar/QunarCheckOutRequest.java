package com.tomasky.doms.dto.qunar;

import com.tomasky.doms.common.CommonApi;
import com.tomasky.doms.common.DomsConstants;
import com.tomasky.doms.model.QunarOrder;

import java.util.List;

/**
 * Created by Administrator on 2016/6/14.
 */
public class QunarCheckOutRequest extends QunarBaseBean {
    //pms酒店id
    private String hotelNo;
    //渠道订单号
    private String channelOrderNo;
    //订单总价
    private String orderPrice;
    //订单客人名称
    private String customerName;
    //订单联系人名字
    private String contactorName;
    //订单联系人电话
    private String contactTelNo;
    //PMS 物理房型代码
    private String phyRoomTypeCode;
    //订单信息
    private List<CheckInOrder> orderList;

    private String channelCode = DomsConstants.channelCode;
    // 操作人 ID String 否 pms 操作人 ID
    private String operatorGuid = CommonApi.operatorGuid;
    private String operatorName = CommonApi.operatorName;

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public String getOperatorGuid() {
        return operatorGuid;
    }

    public void setOperatorGuid(String operatorGuid) {
        this.operatorGuid = operatorGuid;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

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
     * 得到请求离店的参数
     *
     * @param qunarOrder
     * @param qunarUpdateOrderRequest
     * @return
     */
    public static QunarCheckOutRequest getCheckOutOrderParamRequest(QunarOrder qunarOrder, QunarUpdateOrderRequest qunarUpdateOrderRequest) {
        QunarCheckOutRequest qunarCheckOutRequest = new QunarCheckOutRequest();
        qunarCheckOutRequest.setPhyRoomTypeCode(qunarOrder.getRoomTypeCode());
        qunarCheckOutRequest.setOrderPrice(qunarOrder.getOrderPrice());
        qunarCheckOutRequest.setCustomerName(qunarOrder.getCustomerName());
        qunarCheckOutRequest.setContactTelNo(qunarOrder.getContactTelNo());
        qunarCheckOutRequest.setChannelOrderNo(qunarOrder.getChannelOrderNo());
        qunarCheckOutRequest.setContactorName(qunarOrder.getContactorName());
        qunarCheckOutRequest.setHotelNo(qunarOrder.getHotelNo());
        qunarCheckOutRequest.setOrderList(CheckInOrder.getOrderList(qunarOrder, qunarUpdateOrderRequest));
        return qunarCheckOutRequest;
    }
}
