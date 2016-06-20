package com.tomasky.doms.controller;

import com.tomasky.doms.dto.qunar.QunarStatusCode;
import com.tomasky.doms.dto.qunar.response.QunarDataResult;
import com.tomasky.doms.service.impl.ProvidToQunarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Create by jame
 * Date: 2016/6/7
 * Version: 1.0
 * DOTO: 番茄PMS提供给去呼呼的接口
 */
@Controller
@RequestMapping("/api")
public class ProvidToQunarApiController {
    private Logger log = LoggerFactory.getLogger(ProvidToQunarApiController.class);

    @Autowired
    ProvidToQunarService providToQunarService;

    /**
     * 查询酒店列表服务
     *
     * @param hotelNos
     * @return
     */
    @RequestMapping("/tomasky/hotel/queryList.do")
    @ResponseBody
    public QunarDataResult queryHotelList(String hotelNos) {
        log.debug("=====hotelNos====" + hotelNos);
        QunarDataResult result;
        try {
            result = providToQunarService.getHotelList(hotelNos);
        } catch (Exception e) {
            log.error("====查询酒店列表服务=====queryHotelList===方法异常", e);
            result = new QunarDataResult(QunarStatusCode.ERROR_10001, "获取酒店列表出错", null);
        }
        return result;
    }

    /**
     * 查询酒店房型列表服务
     *
     * @param hotelNos
     * @param roomTypeCodes
     * @return
     */
    @RequestMapping("/tomasky/roomType/queryList.do")
    @ResponseBody
    public QunarDataResult queryRoomTypeList(String hotelNos, String roomTypeCodes) {
        log.debug("=====hotelNos====" + hotelNos);
        QunarDataResult result;
        try {
            if (StringUtils.isEmpty(hotelNos)) {
                result = new QunarDataResult(QunarStatusCode.ERROR_1002, "酒店代码参数错误", null);
            } else {
                result = providToQunarService.getRoomTypeList(hotelNos, roomTypeCodes);
            }
        } catch (Exception e) {
            log.error("====查询酒店房型列表服务=====queryRoomTypeList===方法异常", e);
            result = new QunarDataResult(QunarStatusCode.ERROR_10001, "获取酒店房型列表出错", null);
        }
        return result;
    }

    /**
     * 查询价格计划服务
     *
     * @param hotelNos
     * @param
     * @return
     */
    @RequestMapping("/tomasky/rateCode/queryRateCode.do")
    @ResponseBody
    public QunarDataResult queryRateCode(String hotelNos) {
        log.debug("=====hotelNos====" + hotelNos);
        QunarDataResult result;
        try {
            if (StringUtils.isEmpty(hotelNos)) {
                result = new QunarDataResult(QunarStatusCode.ERROR_1002, "酒店代码参数错误", null);
            } else {
                result = providToQunarService.getRateCode(hotelNos);
            }
        } catch (Exception e) {
            log.error("====查询价格计划服务=====queryRateCode===方法异常", e);
            result = new QunarDataResult(QunarStatusCode.ERROR_10001, "获取价格计划列表出错", null);
        }
        return result;
    }

    /**
     * 查询实时房量服务
     *
     * @param hotelNos
     * @param
     * @return
     */
    @RequestMapping("/tomasky/roomStatus/queryRoomCount.do")
    @ResponseBody
    public QunarDataResult queryRoomCount(String hotelNos, String roomTypeCodes, String checkInDate, String checkOutDate) {
        QunarDataResult result;
        try {
            result = providToQunarService.getRoomTemplateResult(hotelNos, roomTypeCodes, checkInDate, checkOutDate, "count");
        } catch (Exception e) {
            log.error("====查询实时房量服务=====queryRoomCount===方法异常", e);
            result = new QunarDataResult(QunarStatusCode.ERROR_10001, "查询实时房量服务出错", null);
        }
        return result;
    }

    /**
     * 查询实时房态服务
     *
     * @param hotelNos
     * @param
     * @return
     */
    @RequestMapping("/tomasky/roomStatus/queryRoomStatus.do")
    @ResponseBody
    public QunarDataResult queryRoomStatus(String hotelNos, String phyRoomTypeCode, String checkInDate, String checkOutDate) {
        QunarDataResult result;
        try {
            result = providToQunarService.getRoomTemplateResult(hotelNos, phyRoomTypeCode, checkInDate, checkOutDate, "status");
        } catch (Exception e) {
            log.error("====查询实时房态服务=====queryRoomStatus===方法异常", e);
            result = new QunarDataResult(QunarStatusCode.ERROR_10001, "查询实时房态服务出错", null);
        }
        return result;
    }

    /**
     * 查询实时房价服务
     *
     * @param hotelNos
     * @param
     * @return
     */
    @RequestMapping("/tomasky/roomStatus/queryRoomPrice.do")
    @ResponseBody
    public QunarDataResult queryRoomPrice(String hotelNos, String phyRoomTypeCode, String checkInDate, String checkOutDate) {
        QunarDataResult result;
        try {
            result = providToQunarService.getRoomTemplateResult(hotelNos, phyRoomTypeCode, checkInDate, checkOutDate, "price");
        } catch (Exception e) {
            log.error("====查询实时房价服务=====queryRoomPrice===方法异常", e);
            result = new QunarDataResult(QunarStatusCode.ERROR_10001, "查询实时房价服务出错", null);
        }
        return result;
    }

    /**
     * 查询订单入驻信息服务
     *
     * @param hotelNo
     * @param channelOrderNos
     * @return
     */
    @RequestMapping("/tomasky/order/queryOrderStatus.do")
    @ResponseBody
    public QunarDataResult queryOrderStatus(String hotelNo, String channelOrderNos) {
        log.debug("=====hotelNos====" + hotelNo);
        QunarDataResult result;
        try {
            if (StringUtils.isEmpty(hotelNo)) {
                result = new QunarDataResult(QunarStatusCode.ERROR_1002, "酒店代码参数错误", null);
            } else {
                result = providToQunarService.getOderStatus(hotelNo, channelOrderNos);
            }
        } catch (Exception e) {
            log.error("====查询订单入住信息出错=====queryOrderStatus===方法异常", e);
            result = new QunarDataResult(QunarStatusCode.ERROR_10001, "查询订单入住信息出错", null);
        }
        return result;
    }

}
