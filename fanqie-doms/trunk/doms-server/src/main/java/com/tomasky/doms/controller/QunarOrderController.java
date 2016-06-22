package com.tomasky.doms.controller;

import com.alibaba.fastjson.JSON;
import com.tomasky.doms.common.DomsConstants;
import com.tomasky.doms.model.QunarOrder;
import com.tomasky.doms.service.IQunarOrderService;
import com.tomasky.doms.support.util.CommonUtil;
import com.tomasky.doms.support.util.QunarOrderUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/13.
 * 去哪儿订单处理
 */
@Controller
@RequestMapping(value = "/api/tomasky/order")
public class QunarOrderController {
    private static final Logger logger = LoggerFactory.getLogger(QunarOrderController.class);

    @Resource
    private IQunarOrderService qunarOrderService;

    /**
     * 去哪儿创建订单
     *
     * @param qunarOrder
     * @return
     */
    @RequestMapping(value = "/receiveOrder.do")
    @ResponseBody
    public Map<String, Object> receiveOrder(QunarOrder qunarOrder) {
        logger.info("去哪儿创建订单传入参数=>" + JSON.toJSONString(qunarOrder));
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> map = QunarOrderUtil.checkCreateOrderParam(qunarOrder);
        if ((boolean) map.get("status")) {
            result = this.qunarOrderService.createQunarOrderMethod(qunarOrder);
            if (String.valueOf(result.get("status")).equals(DomsConstants.STATUS200)) {
                result.put("code", 0);
            } else {
                result.put("code", -10001);
            }
            if (String.valueOf(result.get("status")).equals(String.valueOf(DomsConstants.HTTP_SUCCESS))) {
                result.put("message", "处理成功");
            } else {
                result.put("message", "处理失败");
            }
        } else {
            result.put("code", -10001);
            if (StringUtils.isEmpty(String.valueOf(map.get("message")))) {
                result.put("message", "处理失败");
            } else {
                result.put("message", map.get("message"));
            }
        }
        logger.info("去哪儿创建订单返回值=>" + JSON.toJSONString(result));
        return CommonUtil.getOrderResult(result);
    }

}
