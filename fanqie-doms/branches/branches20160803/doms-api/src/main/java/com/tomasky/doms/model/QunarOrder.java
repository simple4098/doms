package com.tomasky.doms.model;

import com.fanqie.core.domain.OMSOrder;
import com.fanqie.core.dto.CancelOrderParamDto;
import com.fanqie.core.dto.OrderParamDto;
import com.fanqie.util.ResourceBundleUtil;
import com.tomasky.doms.enums.EnumOta;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Administrator on 2016/6/13.
 * 去哪儿订单信息
 */
public class QunarOrder {
    //渠道订单号
    private String channelOrderNo;
    //渠道酒店名称
    private String channelHotelName;
    //渠道酒店代码
    private String channelHotelNo;
    //渠道房型名称
    private String channelRoomTypeName;
    //渠道房型代码
    private String channelRoomTypeCode;
    //渠道价格计划代码
    private String channelRatePlanCode;
    //入住时间
    private String checkInTime;
    //离店时间
    private String checkOutTime;
    //预定时间
    private String orderDate;
    //渠道代码
    private String channelCode;
    //订单状态码
    private String statusCode;
    //订单状态描述
    private String statusDesc;
    //订单总价
    private String orderPrice;
    //入住人姓名
    private String customerName;
    //联系人姓名
    private String contactorName;
    //联系人电话
    private String contactTelNo;
    //房间数
    private String roomNum;
    //担保类型
    private String guaranteeType;
    //担保金额-1：无担保类型，1：非担保，2： 担保
    private String guaranteeAmount;
    //预付类型 0：预付，1：现付
    private String payType;
    //预付金额
    private String prepaidAmount;
    //订单备注
    private String remark;
    //PMS 酒店代码
    private String hotelNo;
    //PMS 房型代码
    private String roomTypeCode;
    //是否预留房订单  0：否，1：是
    private String preservedOrder;
    //是否现付转预付订单 0：否，1：是
    private String ocncOrder;
    //礼盒信息
    private String giftInfo;
    //订单确认号
    private String confirmNo;
    //每日价格
    private String priceDetail;
    //到店时间
    private String arrivalTime;
    //到店时间提示
    private String arrivalTimeTips;
    //订单付款提示
    private String paymentTips;
    //订单佣金
    private String commissionPrice;
    //返现金额
    private String cashbackPrice;
    //是否买断房  false：否，true：是
    private String buyOut;
    //PMS 房价代码
    private String ratePlanCode;
    //货币 人民币CNY
    private String currency;
    //oms订单号
    private String omsOrderNo;
    //oms订单状态
    private String omsOrderStatus;
    //实际入住时间
    private String liveInDate;
    //实际离店时间
    private String leaveOutDate;
    //房间号
    private String roomNo;
    //房间名称
    private String roomName;

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getOmsOrderStatus() {
        return omsOrderStatus;
    }

    public void setOmsOrderStatus(String omsOrderStatus) {
        this.omsOrderStatus = omsOrderStatus;
    }

    public String getLiveInDate() {
        return liveInDate;
    }

    public void setLiveInDate(String liveInDate) {
        this.liveInDate = liveInDate;
    }

    public String getLeaveOutDate() {
        return leaveOutDate;
    }

    public void setLeaveOutDate(String leaveOutDate) {
        this.leaveOutDate = leaveOutDate;
    }

    public String getOmsOrderNo() {
        return omsOrderNo;
    }

    public void setOmsOrderNo(String omsOrderNo) {
        this.omsOrderNo = omsOrderNo;
    }

    public String getChannelOrderNo() {
        return channelOrderNo;
    }

    public void setChannelOrderNo(String channelOrderNo) {
        this.channelOrderNo = channelOrderNo;
    }

    public String getChannelHotelName() {
        return channelHotelName;
    }

    public void setChannelHotelName(String channelHotelName) {
        this.channelHotelName = channelHotelName;
    }

    public String getChannelHotelNo() {
        return channelHotelNo;
    }

    public void setChannelHotelNo(String channelHotelNo) {
        this.channelHotelNo = channelHotelNo;
    }

    public String getChannelRoomTypeName() {
        return channelRoomTypeName;
    }

    public void setChannelRoomTypeName(String channelRoomTypeName) {
        this.channelRoomTypeName = channelRoomTypeName;
    }

    public String getChannelRoomTypeCode() {
        return channelRoomTypeCode;
    }

    public void setChannelRoomTypeCode(String channelRoomTypeCode) {
        this.channelRoomTypeCode = channelRoomTypeCode;
    }

    public String getChannelRatePlanCode() {
        return channelRatePlanCode;
    }

    public void setChannelRatePlanCode(String channelRatePlanCode) {
        this.channelRatePlanCode = channelRatePlanCode;
    }

    public String getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(String checkInTime) {
        this.checkInTime = checkInTime;
    }

    public String getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(String checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
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

    public String getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(String roomNum) {
        this.roomNum = roomNum;
    }

    public String getGuaranteeType() {
        return guaranteeType;
    }

    public void setGuaranteeType(String guaranteeType) {
        this.guaranteeType = guaranteeType;
    }

    public String getGuaranteeAmount() {
        return guaranteeAmount;
    }

    public void setGuaranteeAmount(String guaranteeAmount) {
        this.guaranteeAmount = guaranteeAmount;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPrepaidAmount() {
        return prepaidAmount;
    }

    public void setPrepaidAmount(String prepaidAmount) {
        this.prepaidAmount = prepaidAmount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getHotelNo() {
        return hotelNo;
    }

    public void setHotelNo(String hotelNo) {
        this.hotelNo = hotelNo;
    }

    public String getRoomTypeCode() {
        return roomTypeCode;
    }

    public void setRoomTypeCode(String roomTypeCode) {
        this.roomTypeCode = roomTypeCode;
    }

    public String getPreservedOrder() {
        return preservedOrder;
    }

    public void setPreservedOrder(String preservedOrder) {
        this.preservedOrder = preservedOrder;
    }

    public String getOcncOrder() {
        return ocncOrder;
    }

    public void setOcncOrder(String ocncOrder) {
        this.ocncOrder = ocncOrder;
    }

    public String getGiftInfo() {
        return giftInfo;
    }

    public void setGiftInfo(String giftInfo) {
        this.giftInfo = giftInfo;
    }

    public String getConfirmNo() {
        return confirmNo;
    }

    public void setConfirmNo(String confirmNo) {
        this.confirmNo = confirmNo;
    }

    public String getPriceDetail() {
        return priceDetail;
    }

    public void setPriceDetail(String priceDetail) {
        this.priceDetail = priceDetail;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getArrivalTimeTips() {
        return arrivalTimeTips;
    }

    public void setArrivalTimeTips(String arrivalTimeTips) {
        this.arrivalTimeTips = arrivalTimeTips;
    }

    public String getPaymentTips() {
        return paymentTips;
    }

    public void setPaymentTips(String paymentTips) {
        this.paymentTips = paymentTips;
    }

    public String getCommissionPrice() {
        return commissionPrice;
    }

    public void setCommissionPrice(String commissionPrice) {
        this.commissionPrice = commissionPrice;
    }

    public String getCashbackPrice() {
        return cashbackPrice;
    }

    public void setCashbackPrice(String cashbackPrice) {
        this.cashbackPrice = cashbackPrice;
    }

    public String getBuyOut() {
        return buyOut;
    }

    public void setBuyOut(String buyOut) {
        this.buyOut = buyOut;
    }

    public String getRatePlanCode() {
        return ratePlanCode;
    }

    public void setRatePlanCode(String ratePlanCode) {
        this.ratePlanCode = ratePlanCode;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * 得到oms创建订单请求对象
     *
     * @param omsOrder
     * @param password
     * @param userName
     * @return
     */
    public OrderParamDto getOrderParamDto(OMSOrder omsOrder, String userName, String password) {
        OrderParamDto orderParamDto = new OrderParamDto();
        orderParamDto.setOrder(omsOrder);
        orderParamDto.setOtaId(EnumOta.qunar_conn.getValue());
        orderParamDto.setvName(userName);
        orderParamDto.setvPWD(password);
        return orderParamDto;
    }

    /**
     * 得到请求取消订单参数
     *
     * @param qunarOrder
     * @return
     */
    public CancelOrderParamDto getCancelOrderParam(QunarOrder qunarOrder) {
        CancelOrderParamDto cancelOrderParamDto = new CancelOrderParamDto();
        cancelOrderParamDto.setOtaId(EnumOta.qunar_conn.getValue());
        cancelOrderParamDto.setOtaOrderNo(qunarOrder.getChannelOrderNo());
        //取消订单传入已付金额为0
        cancelOrderParamDto.setPaidAmount(BigDecimal.valueOf(0));
        cancelOrderParamDto.setvName(ResourceBundleUtil.getString("qunar_conn_ota_user_code"));
        cancelOrderParamDto.setvPWD(ResourceBundleUtil.getString("qunar_conn_ota_user_pwd"));
        return cancelOrderParamDto;
    }
}
