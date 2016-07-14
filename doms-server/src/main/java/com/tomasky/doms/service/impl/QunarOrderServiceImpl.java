package com.tomasky.doms.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.fanqie.core.domain.OMSOrder;
import com.fanqie.core.dto.CancelOrderParamDto;
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
        if (qunarOrder.getStatusCode().equals("1")) {
            //创建订单：待确定订单
            return createOrderMethod(qunarOrder, result, true);
        } else if (qunarOrder.getStatusCode().equals("3")) {
            //取消订单
            return cancelOrderMethod(qunarOrder, result);
        } else if (qunarOrder.getStatusCode().equals("2")) {
            //确认订单
            return confirmQunarOrderMethod(qunarOrder, result);
        } else if (qunarOrder.getStatusCode().equals("4")) {
            //拒单
            return refuseQunarOrderMethod(qunarOrder, result);
        } else {
            logger.info("去哪儿请求的订单状态为=>" + qunarOrder.getStatusCode());
            result.put("status", DomsConstants.HTTP_SUCCESS);
            result.put("message", "处理成功");
        }
        return result;
    }

    /**
     * 拒单
     *
     * @param qunarOrder
     * @param result
     * @return
     */
    private Map<String, Object> refuseQunarOrderMethod(QunarOrder qunarOrder, Map<String, Object> result) {
        try {
            //拒单
            logger.info("去哪儿拒绝订单，查询oms订单信息，请求地址=>" + CommonApi.getOmsMainOrderByChannelOrderCode() + "请求参数=>" + JSON.toJSONString(qunarOrder));
            String response = HttpClientUtil.httpKvPost(CommonApi.getOmsMainOrderByChannelOrderCode(), JSON.toJSONString(qunarOrder));
            logger.info("去哪儿拒绝订单，查询oms订单信息，返回值=>" + response);
            JSONObject jsonObject = JSONObject.parseObject(response);
            if (jsonObject.containsKey("omsOrderId") && StringUtils.isNotEmpty(jsonObject.get("omsOrderId").toString())) {
                //订单存在，更新订单状态
                qunarOrder.setOmsOrderNo(jsonObject.get("omsOrderId").toString());
                logger.info("去哪儿拒绝订单，同步订单状态，请求地址=>" + CommonApi.getUpdateOmsOrderStatus() + "请求参数=>" + JSON.toJSONString(qunarOrder));
                String updateOrderStatusResponse = HttpClientUtil.httpKvPost(CommonApi.getUpdateOmsOrderStatus(), JSON.toJSONString(qunarOrder));
                logger.info("去哪儿拒绝订单，同步订单状态，返回值=>" + updateOrderStatusResponse);
                JSONObject orderStatusObject = JSONObject.parseObject(updateOrderStatusResponse);
                result.put("status", orderStatusObject.get("status"));
                if (orderStatusObject.get("status").toString().equals(DomsConstants.STATUS200)) {
                    //更新订单状态成功
                    result.put("message", "处理成功");
                } else {
                    result.put("message", "处理失败");
                }
            }
            return result;
        } catch (Exception e) {
            logger.error("去哪儿拒绝订单，调用oms接口异常", e);
            result.put("status", DomsConstants.STATUS400);
            result.put("message", "请求拒绝订单接口异常");
            return result;
        }
    }

    /**
     * 去哪儿确认订单
     *
     * @param qunarOrder
     * @param result
     * @return
     */
    private Map<String, Object> confirmQunarOrderMethod(QunarOrder qunarOrder, Map<String, Object> result) {
        try {
            logger.info("去哪儿确认订单，查询oms订单信息，请求地址=>" + CommonApi.getOmsMainOrderByChannelOrderCode() + "请求参数=>" + JSON.toJSONString(qunarOrder));
            String response = HttpClientUtil.httpKvPost(CommonApi.getOmsMainOrderByChannelOrderCode(), JSON.toJSONString(qunarOrder));
            logger.info("去哪儿确认订单，查询oms订单信息，返回值=>" + response);
            JSONObject jsonObject = JSONObject.parseObject(response);
            if (jsonObject.containsKey("omsOrderId") && StringUtils.isNotEmpty(jsonObject.get("omsOrderId").toString())) {
                //订单存在，更新订单状态
                qunarOrder.setOmsOrderNo(jsonObject.get("omsOrderId").toString());
                logger.info("去哪儿确认订单，同步订单状态，请求地址=>" + CommonApi.getUpdateOmsOrderStatus() + "请求参数=>" + JSON.toJSONString(qunarOrder));
                String updateOrderStatusResponse = HttpClientUtil.httpKvPost(CommonApi.getUpdateOmsOrderStatus(), JSON.toJSONString(qunarOrder));
                logger.info("去哪儿确认订单，同步订单状态，返回值=>" + updateOrderStatusResponse);
                JSONObject orderStatusObject = JSONObject.parseObject(updateOrderStatusResponse);
                result.put("status", orderStatusObject.get("status"));
                if (orderStatusObject.get("status").toString().equals(DomsConstants.STATUS200)) {
                    //更新订单状态成功
                    result.put("message", "处理成功");
                } else {
                    result.put("message", "处理失败");
                }
                return result;
            } else {
                //订单不存在,调用oms下单方法
                Map<String, Object> orderMethod = createOrderMethod(qunarOrder, result, false);
                result.put("status", orderMethod.get("status"));
                if (orderMethod.get("status").toString().equals(String.valueOf(200))) {
                    result.put("message", "确认订单成功");
                } else {
                    result.put("message", "确认订单失败");
                }
                return result;
            }

        } catch (Exception e) {
            logger.error("去哪儿确认订单，调用oms接口异常", e);
            result.put("status", DomsConstants.STATUS400);
            result.put("message", "请求确认订单接口异常");
            return result;
        }
    }

    /**
     * 去哪儿取消订单
     *
     * @param qunarOrder
     * @param result
     * @return
     */
    private Map<String, Object> cancelOrderMethod(QunarOrder qunarOrder, Map<String, Object> result) {
        //取消订单
        try {
            CancelOrderParamDto cancelOrderParamDto = qunarOrder.getCancelOrderParam(qunarOrder);
            String response = HttpClientUtil.httpGetCancelOrder(CommonApi.getCancelOrderUrl(), cancelOrderParamDto);
            JSONObject jsonObject = JSONObject.parseObject(response);
            if (!jsonObject.get("status").toString().equals(String.valueOf(200))) {
                logger.info("oms取消订单失败");
                result.put("status", DomsConstants.STATUS400);
                result.put("message", "取消订单失败");
            } else {
                //取消订单成功，同步订单状态到oms基本订单表
                String updateResponse = com.tomasky.doms.support.util.HttpClientUtil.httpKvPost(CommonApi.getOmsUpdateQunarOrder(), qunarOrder);
                logger.info("同步取消订单状态到oms去哪儿订单，返回值=>" + updateResponse);
                result.put("status", DomsConstants.HTTP_SUCCESS);
                result.put("message", "取消订单处理成功");
            }
            return result;
        } catch (Exception e) {
            logger.error("取消订单，同步oms接口异常", e);
            result.put("status", DomsConstants.STATUS400);
            result.put("message", "请求取消订单接口异常");
            return result;
        }
    }

    /**
     * 下单到oms
     *
     * @param qunarOrder
     * @param result
     * @return
     */
    private Map<String, Object> createOrderMethod(QunarOrder qunarOrder, Map<String, Object> result, boolean needConfirm) {
        try {
            OMSOrder omsOrder = QunarOrderUtil.getOmsOrderObject(qunarOrder, needConfirm);
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
        if (otaId.equals(String.valueOf(qunarUpdateOrderRequest.getOtaId()))) {
            try {
                logger.info("事件调用oms更新去哪儿订单信息，请求地址=>" + CommonApi.getOmsUpdateQunarOrder() + "参数=>" + JSON.toJSONString(qunarUpdateOrderRequest));
                String response = HttpClientUtil.httpPostQunarBasicOrder(CommonApi.getOmsUpdateQunarOrder(), QunarOrderUtil.getUpdateQunarOrderStatusParm(qunarUpdateOrderRequest));
                logger.info("事件调用oms更新去哪儿订单信息，响应值=>" + response);
                //解析响应值
                if (QunarOrderUtil.dealObjectResponse(response)) {
                    JSONObject jsonObject = JSONObject.parseObject(response);
                    QunarOrder qunarOrder = JSON.parseObject(String.valueOf(jsonObject.get("qunarOrder")), new TypeReference<QunarOrder>() {
                    });
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
