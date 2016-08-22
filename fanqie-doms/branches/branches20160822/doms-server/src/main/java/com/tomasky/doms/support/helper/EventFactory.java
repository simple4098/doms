package com.tomasky.doms.support.helper;

import com.alibaba.fastjson.JSONObject;
import com.tomasky.doms.common.DomsConstants;
import com.tomasky.doms.service.IQunarOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * DESC :
 *
 * @author : 番茄木-ZLin
 * @data : 2016/3/15
 * @version: v1.0.0
 */
@Component
public class EventFactory {
    private static final Logger log = LoggerFactory.getLogger(EventFactory.class);

    @Resource
    private IQunarOrderService qunarOrderService;

    public void pushEvent(JSONObject jsonObject) {
        String bizType = jsonObject.getString("bizType");
        String content = jsonObject.getString("content");
        /**
         * 监听pms订单状态同步
         */
        if (DomsConstants.UPDATE_ORDER_STATUS.equals(bizType)) {
            //订单状态更新
            log.info("=========监听订单状态同步===============参数：" + content);
            this.qunarOrderService.eventUpdateOrderStatus(content);

        }
    }
}
