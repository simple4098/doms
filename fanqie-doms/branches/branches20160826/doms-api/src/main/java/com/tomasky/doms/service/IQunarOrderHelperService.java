package com.tomasky.doms.service;

import com.tomasky.doms.dto.qunar.QunarUpdateOrderRequest;
import com.tomasky.doms.model.QunarOrder;

/**
 * Created by Administrator on 2016/6/15.
 */
public interface IQunarOrderHelperService {
    /**
     * 同步订单状态到去哪儿
     *
     * @param qunarOrder
     * @param qunarUpdateOrderRequest
     */
    void pushOrderStatusToQunar(QunarOrder qunarOrder, QunarUpdateOrderRequest qunarUpdateOrderRequest);
}
