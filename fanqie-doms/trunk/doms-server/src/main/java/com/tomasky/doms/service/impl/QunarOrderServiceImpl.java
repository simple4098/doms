package com.tomasky.doms.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.fanqie.core.domain.OMSOrder;
import com.fanqie.core.dto.OrderParamDto;
import com.fanqie.util.HttpClientUtil;
import com.tomasky.doms.common.CommonApi;
import com.tomasky.doms.common.DomsConstants;
import com.tomasky.doms.dto.qunar.QunarUpdateOrderRequest;
import com.tomasky.doms.enums.EnumOta;
import com.tomasky.doms.model.QunarOrder;
import com.tomasky.doms.service.IQunarOrderHelperService;
import com.tomasky.doms.service.IQunarOrderService;
import com.tomasky.doms.support.util.QunarOrderUtil;
import com.tomasky.doms.support.util.ResourceBundleUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/13.
 */
@Service
public class QunarOrderServiceImpl implements IQunarOrderService {
    private static final Logger logger = LoggerFactory.getLogger(QunarOrderServiceImpl.class);
    @Resource
    private IQunarOrderHelperService qunarOrderHelperService;

    @Override
    public Map<String, Object> createQunarOrderMethod(QunarOrder qunarOrder) {
        Map<String, Object> result = new HashMap<>();
        try {
            OMSOrder omsOrder = QunarOrderUtil.getOmsOrderObject(qunarOrder);
            OrderParamDto orderParamDto = qunarOrder.getOrderParamDto(omsOrder, ResourceBundleUtil.getString("qunar_conn_ota_user_code"), ResourceBundleUtil.getString("qunar_conn_ota_user_pwd"));
            logger.info("请求oms下单接口，请求地址=>" + CommonApi.getOmsCreateOrder() + "参数=>" + JSON.toJSONString(orderParamDto));
            String response = HttpClientUtil.httpPostOrder(CommonApi.getOmsCreateOrder(), orderParamDto);
            logger.info("请求oms下单接口，响应值=>" + response);
            //解析响应值
            Map<String, Object> responseResult = QunarOrderUtil.dealOrderRequestResponse(response);
            if (StringUtils.isNotEmpty((String) responseResult.get("orderNo"))) {
                qunarOrder.setOmsOrderNo((String) responseResult.get("orderNo"));
            }
            //调用oms接口保存去哪儿原始订单信息
            logger.info("请求oms创建去哪儿初始订单，请求地址=>" + CommonApi.getOmsQunarBasicOrder() + "参数=>" + JSON.toJSONString(QunarOrderUtil.toNameValuePair(qunarOrder)));
            String baseOrderResponse = HttpClientUtil.httpPostQunarBasicOrder(CommonApi.getOmsQunarBasicOrder(), QunarOrderUtil.toNameValuePair(qunarOrder));
            logger.info("请求oms创建去哪儿初始订单，返回值=>" + baseOrderResponse);
            //处理创建订单返回值
            return responseResult;
        } catch (Exception e) {
            logger.error("下单到oms接口异常", e);
            result.put("status", DomsConstants.STATUS400);
            result.put("message", "请求oms下单接口异常");
            return result;
        }
    }

    @Override
    public void eventUpdateOrderStatus(String content) {
        logger.info("事件监听pms同步订单状态，传入参数=>" + content);
        QunarUpdateOrderRequest qunarUpdateOrderRequest = JSON.parseObject(content, new TypeReference<QunarUpdateOrderRequest>() {
        });
        //根据传入参数判断是否为去哪儿的订单
        String otaId = String.valueOf(EnumOta.qunar_conn.getValue());
        if (otaId.equals(qunarUpdateOrderRequest.getOtaId())) {
            try {
                logger.info("事件调用oms更新去哪儿订单信息，请求地址=>" + CommonApi.getOmsUpdateQunarOrder() + "参数=>" + JSON.toJSONString(qunarUpdateOrderRequest));
                String response = HttpClientUtil.httpPostQunarBasicOrder(CommonApi.getOmsUpdateQunarOrder(), QunarOrderUtil.getUpdateQunarOrderStatusParm(qunarUpdateOrderRequest));
                logger.info("事件调用oms更新去哪儿订单信息，响应值=>" + response);
                //解析响应值
                if (QunarOrderUtil.dealObjectResponse(response)) {
                    JSONObject jsonObject = JSONObject.parseObject(response);
                    QunarOrder qunarOrder = (QunarOrder) jsonObject.get("qunarOrder");
                    if (null != qunarOrder) {
                        this.qunarOrderHelperService.pushOrderStatusToQunar(qunarOrder, qunarUpdateOrderRequest);
                    }
                } else {
                    logger.info("调用oms接口，响应非200，不执行同步操作");
                }
            } catch (Exception e) {
                logger.error("调用oms同步去哪儿订单状态异常" + e);
            }
        } else {
            logger.info("此订单不是去哪儿订单，不执行操作!");
        }
    }
}
