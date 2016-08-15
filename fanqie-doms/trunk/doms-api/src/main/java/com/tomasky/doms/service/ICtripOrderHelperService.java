package com.tomasky.doms.service;

import com.tomasky.doms.dto.qunar.QunarUpdateOrderRequest;

import javax.xml.bind.JAXBException;

/**
 * Created by wangdayin on 2016/7/28.
 */
public interface ICtripOrderHelperService {
    /**
     * 同步携程订单状态
     *
     * @param qunarUpdateOrderRequest
     * @param otaRoomTypeId
     */
    void pushOrderStatus(QunarUpdateOrderRequest qunarUpdateOrderRequest, String otaRoomTypeId) throws JAXBException;
}
