package com.tomasky.doms.dto.qunar;

import com.tomasky.doms.common.CommonApi;
import com.tomasky.doms.common.DomsConstants;
import com.tomasky.doms.model.QunarOrder;

/**
 * Created by Administrator on 2016/6/14.
 */
public class QunarConfirmOrderRequest extends QunarBaseBean {

    //pms酒店code
    private String hotelNo;
    //渠道订单号
    private String channelOrderNo;
    //确认号
    private String confirmNo;
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
        //订单确认号，设置为渠道订单号
        qunarConfirmOrderRequest.setConfirmNo(qunarOrder.getChannelOrderNo());
        return qunarConfirmOrderRequest;
    }
}
