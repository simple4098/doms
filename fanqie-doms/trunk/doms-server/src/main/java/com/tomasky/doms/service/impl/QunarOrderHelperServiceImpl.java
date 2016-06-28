package com.tomasky.doms.service.impl;

import com.alibaba.fastjson.JSON;
import com.tomasky.doms.common.CommonApi;
import com.tomasky.doms.dto.qunar.*;
import com.tomasky.doms.model.QunarOrder;
import com.tomasky.doms.service.IQunarOrderHelperService;
import com.tomasky.doms.support.util.HttpClientUtil;
import com.tomasky.doms.support.util.JacksonUtil;
import com.tomasky.doms.support.util.QunarUrlUtil;
import com.tomasky.doms.support.util.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by Administrator on 2016/6/15.
 */
@Service
public class QunarOrderHelperServiceImpl implements IQunarOrderHelperService {
    private static final Logger logger = LoggerFactory.getLogger(QunarOrderHelperServiceImpl.class);

    @Override
    public void pushOrderStatusToQunar(QunarOrder qunarOrder, QunarUpdateOrderRequest qunarUpdateOrderRequest) {
        //根据传入参数同步不同状态
        // 渠道订单状态（0:未处理、1:已接受（已分房）、2:已拒绝、3:已取消、4:验证失败、5：已接受（未分房）、6：入住、7：离店）
        try {

            if ("2".equals(qunarUpdateOrderRequest.getOrderStatus()) || "3".equals(qunarUpdateOrderRequest.getOrderStatus()) || "4".equals(qunarUpdateOrderRequest.getOrderStatus())) {
                //调用去哪儿拒绝订单
                QunarRefuseOrderRequest qunarRefuseOrderRequest = QunarRefuseOrderRequest.getRefuseOrderParamRequest(qunarOrder, qunarUpdateOrderRequest);
                String obj2json = JacksonUtil.obj2json(qunarRefuseOrderRequest);
                Map<String, String> param = JacksonUtil.json2map(obj2json);
                String hmac = SecurityUtil.buildMyHMAC(param, CommonApi.signkey);
                qunarRefuseOrderRequest.setHmac(hmac);
                logger.info("调用去哪儿同步拒绝订单状态，请求地址=>" + QunarUrlUtil.getRefuseOrderUrl() + "参数=>" + JSON.toJSONString(qunarRefuseOrderRequest));
                String response = HttpClientUtil.httpJsonPost(QunarUrlUtil.getRefuseOrderUrl(), JSON.toJSONString(qunarRefuseOrderRequest));
                logger.info("调用去哪儿同步拒绝订单状态，返回值=>" + response);
            } else if ("1".equals(qunarUpdateOrderRequest.getOrderStatus())) {
                //调用去哪儿接收订单
                QunarConfirmOrderRequest qunarConfirmOrderRequest = QunarConfirmOrderRequest.getConfirmOrderParamRequest(qunarOrder);
                String obj2json = JacksonUtil.obj2json(qunarConfirmOrderRequest);
                Map<String, String> param = JacksonUtil.json2map(obj2json);
                String hmac = SecurityUtil.buildMyHMAC(param, CommonApi.signkey);
                qunarConfirmOrderRequest.setHmac(hmac);
                logger.info("调用去哪儿同步接收订单状态，请求地址=>" + QunarUrlUtil.getConfirmOrderUrl() + "参数=>" + JSON.toJSONString(qunarConfirmOrderRequest));
                String response = HttpClientUtil.httpJsonPost(QunarUrlUtil.getConfirmOrderUrl(), JSON.toJSONString(qunarConfirmOrderRequest));
                logger.info("调用去哪儿同步接收订单状态，返回值=>" + response);
            } else if ("6".equals(qunarUpdateOrderRequest.getOrderStatus())) {
                //调用去哪儿入住
                QunarCheckInRequest qunarCheckInRequest = QunarCheckInRequest.getCheckInOrderParamRequest(qunarOrder);
                String obj2json = JacksonUtil.obj2json(qunarCheckInRequest);
                Map<String, String> param = JacksonUtil.json2map(obj2json);
                String hmac = SecurityUtil.buildMyHMAC(param, CommonApi.signkey);
                qunarCheckInRequest.setHmac(hmac);
                logger.info("调用去哪儿同步入住订单状态，请求地址=>" + QunarUrlUtil.getLiveInOrderUrl() + "参数=>" + JSON.toJSONString(qunarCheckInRequest));
                String response = HttpClientUtil.httpJsonPost(QunarUrlUtil.getLiveInOrderUrl(), JSON.toJSONString(qunarCheckInRequest));
                logger.info("调用去哪儿同步入住订单状态，返回值=>" + response);
            } else if ("7".equals(qunarUpdateOrderRequest.getOrderStatus())) {
                //调用去哪儿离店
                QunarCheckOutRequest qunarCheckOutRequest = QunarCheckOutRequest.getCheckOutOrderParamRequest(qunarOrder);
                String obj2json = JacksonUtil.obj2json(qunarCheckOutRequest);
                Map<String, String> param = JacksonUtil.json2map(obj2json);
                String hmac = SecurityUtil.buildMyHMAC(param, CommonApi.signkey);
                qunarCheckOutRequest.setHmac(hmac);
                logger.info("调用去哪儿同步离店订单状态，请求地址=>" + QunarUrlUtil.getLeaveOrderUrl() + "参数=>" + JSON.toJSONString(qunarCheckOutRequest));
                String response = HttpClientUtil.httpJsonPost(QunarUrlUtil.getLeaveOrderUrl(), JSON.toJSONString(qunarCheckOutRequest));
                logger.info("调用去哪儿同步离店订单状态，返回值=>" + response);
            } else {
                //暂时不做任何操作
            }
        } catch (Exception e) {
            logger.error("请求去哪儿同步订单状态，调用去哪儿接口异常", e);
        }
    }
}
