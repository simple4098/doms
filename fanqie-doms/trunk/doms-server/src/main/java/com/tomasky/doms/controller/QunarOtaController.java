package com.tomasky.doms.controller;

import com.tomasky.doms.common.DomsConstants;
import com.tomasky.doms.dto.OmsPram;
import com.tomasky.doms.dto.qunar.QunarMobile;
import com.tomasky.doms.dto.qunar.response.QunarHotelInfo;
import com.tomasky.doms.dto.qunar.response.QunarProductionData;
import com.tomasky.doms.dto.qunar.response.QunarResult;
import com.tomasky.doms.exception.DmsException;
import com.tomasky.doms.service.IQunarService;
import com.tomasky.doms.support.util.JacksonUtil;
import com.tomasky.doms.support.util.JsonModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * DESC : oms 渠道直连控制层
 * @author : 番茄木-ZLin
 * @data : 2016/6/6
 * @version: v1.0.0
 */
@Controller
@RequestMapping("/direct")
public class QunarOtaController {

    private static final Logger logger = LoggerFactory.getLogger(QunarOtaController.class);
    @Resource
    private IQunarService qunarService;

    /**
     * 发送验证码
     * @return
     */
    @RequestMapping("/sendCode")
    @ResponseBody
    public Object sendCode(QunarMobile qunarMobile){
        JsonModel jsonModel = null;
        QunarResult qunarResult = qunarService.sendQunarPhoneCode( qunarMobile);
        if (DomsConstants.SUCCESS_QUNAR.equals(qunarResult.getCode())){
            jsonModel =  new JsonModel(DomsConstants.STATUS200,qunarResult.getMsg());
        }else {
            jsonModel =  new JsonModel(DomsConstants.STATUS400,qunarResult.getMsg());
        }
        return jsonModel;
    }

    /**
     * 酒店列表
     * @return
     */
    @RequestMapping("/hotelList")
    @ResponseBody
    public Object hotelList(OmsPram omsPram){
        JsonModel jsonModel = null;
        try {
            QunarHotelInfo qunarPmsHotel = qunarService.createQunarPmsHotel(omsPram);
            jsonModel =  new JsonModel(DomsConstants.STATUS200, DomsConstants.HANDLE_SUCCESS);
            jsonModel.setData(qunarPmsHotel);
        } catch (Exception e) {
            logger.error("开通渠道、获取酒店列表异常",e);
            jsonModel =  new JsonModel(DomsConstants.STATUS400,e.getMessage());
        }
        return jsonModel;
    }

    /**
     * 匹配渠道酒店
     * @param omsPram
     * @return
     */
    @RequestMapping("/matchHotel")
    @ResponseBody
    public Object matchHotel(OmsPram omsPram){
        JsonModel jsonModel = new JsonModel(DomsConstants.STATUS200, DomsConstants.HANDLE_SUCCESS);
        try {
            QunarResult qunarResult = qunarService.matchQunarHotel(omsPram);
            if (!DomsConstants.SUCCESS_QUNAR.equals(qunarResult.getCode())){
                jsonModel = new JsonModel(DomsConstants.STATUS400,qunarResult.getMsg());
            }
        } catch (Exception e) {
            logger.error("匹配酒店异常",e);
            jsonModel = new JsonModel(DomsConstants.STATUS400,e.getMessage());
        }
        return jsonModel;
    }

    /**
     * 酒店解绑
     * @return
     */
    @RequestMapping("/relieveHotel")
    @ResponseBody
    public Object relieveHotel(OmsPram omsPram){
        JsonModel jsonModel = new JsonModel(DomsConstants.STATUS200, DomsConstants.HANDLE_SUCCESS);
        try {
            QunarResult qunarResult = qunarService.removeDockingHotel(omsPram);
            //QunarResult qunarResult = qunarService.deleteHotel(omsPram);
            if (!DomsConstants.SUCCESS_QUNAR.equals(qunarResult.getCode())){
                jsonModel = new JsonModel(DomsConstants.STATUS400,qunarResult.getMsg());
            }
        } catch (DmsException e) {
            jsonModel = new JsonModel(DomsConstants.STATUS400,e.getMessage());
            logger.error("酒店解绑失败",e);
        }
        return jsonModel;
    }
    /**
     * 账号解绑
     */
    @RequestMapping("/relieveAccount")
    @ResponseBody
    public Object relieveAccount(OmsPram omsPram){
        JsonModel jsonModel = new JsonModel(DomsConstants.STATUS200, DomsConstants.HANDLE_SUCCESS);
        try {
            QunarResult qunarResult = qunarService.removeDockingAccount(omsPram);
            if (!DomsConstants.SUCCESS_QUNAR.equals(qunarResult.getCode())){
                jsonModel = new JsonModel(DomsConstants.STATUS400,qunarResult.getMsg());
            }
        } catch (DmsException e) {
            jsonModel = new JsonModel(DomsConstants.STATUS400,e.getMessage());
            logger.error("解绑账号异常",e);
        }
        return jsonModel;
    }

    /**
     * 根据匹配的酒店查询去哪儿所有房型信息
     * @param omsPram
     * @return
     */
    @RequestMapping("/channelRoomType")
    @ResponseBody
    public Object channelRoomType(OmsPram omsPram){
        JsonModel jsonModel = new JsonModel(DomsConstants.STATUS200, DomsConstants.HANDLE_SUCCESS);
        try {
            QunarProductionData qunarProductionData = qunarService.searchQunarProductList(omsPram);
            jsonModel.setData(qunarProductionData);
        } catch (DmsException e) {
            logger.error("匹配房型异常",e);
            jsonModel = new JsonModel(DomsConstants.STATUS400,e.getMessage());
        }
        return jsonModel;
    }

    /**
     * 匹配房型信息
     * @return
     */
    @RequestMapping("/matchRoomType")
    @ResponseBody
    public Object matchRoomType(OmsPram omsPram){

        JsonModel jsonModel = new JsonModel(DomsConstants.STATUS200, DomsConstants.HANDLE_SUCCESS);
        try {
            QunarResult qunarResult = qunarService.matchQunarProduct(omsPram);
            if (!DomsConstants.SUCCESS_QUNAR.equals(qunarResult.getCode())){
                jsonModel = new JsonModel(DomsConstants.STATUS400,qunarResult.getMsg());
            }
        } catch (Exception e) {
            logger.error("匹配酒店异常",e);
            jsonModel = new JsonModel(DomsConstants.STATUS400,e.getMessage());
        }
        return jsonModel;
    }
    /**
     * 解除房型匹配
     */
    @RequestMapping("/relieveRoomType")
    @ResponseBody
    public Object relieveRoomType(OmsPram omsPram){
        JsonModel jsonModel = new JsonModel(DomsConstants.STATUS200, DomsConstants.HANDLE_SUCCESS);
        try {
            //QunarResult qunarResult = qunarService.removeDockingProduct(omsPram);
            QunarResult qunarResult = qunarService.deletePhyRoomType(omsPram);
            if (!DomsConstants.SUCCESS_QUNAR.equals(qunarResult.getCode())){
                jsonModel = new JsonModel(DomsConstants.STATUS400,qunarResult.getMsg());
            }
        } catch (DmsException e) {
            logger.error("匹配酒店异常",e);
            jsonModel = new JsonModel(DomsConstants.STATUS400,e.getMessage());
        }
        return jsonModel;
    }
}
