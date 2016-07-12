package com.tomasky.doms.service;

import com.tomasky.doms.model.QunarOrder;

import java.util.Map;

/**
 * Created by Administrator on 2016/6/13.
 */
public interface IQunarOrderService {
    /**
     * 创建订单去哪儿
     *
     * @param qunarOrder
     * @return
     */
    Map<String, Object> createQunarOrderMethod(QunarOrder qunarOrder);

    /**
     * 监听pms订单状态更新事件
     *
     * @param content
     */
    void eventUpdateOrderStatus(String content);
}
