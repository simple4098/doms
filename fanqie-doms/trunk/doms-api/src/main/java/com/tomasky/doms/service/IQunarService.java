package com.tomasky.doms.service;

import com.tomasky.doms.dto.OmsPram;
import com.tomasky.doms.dto.qunar.QunarMobile;
import com.tomasky.doms.dto.qunar.response.*;
import com.tomasky.doms.exception.DmsException;

/**
 * DESC : 去哪儿对接接口逻辑层
 * @author : 番茄木-ZLin
 * @data : 2016/6/7
 * @version: v1.0.0
 */
public interface IQunarService {


    /**
     * 去哪儿发送动态验证码
     * @param qunarMobile 调用去哪儿接口参数对象
     */
    QunarResult sendQunarPhoneCode(QunarMobile qunarMobile);

    /**
     * 1 去哪儿开通渠道 2 查询渠道酒店列表
     * @param omsPram oms参数列表
     * @return
     */
    QunarHotelInfo createQunarPmsHotel(OmsPram omsPram) throws DmsException;

    /**
     * 关闭渠道直连
     * @param omsPram oms参数对象
     * @return 请求去呼呼接口返回对象
     */
    QunarResult removeDockingAccount(OmsPram omsPram)throws DmsException;

    /**
     * 匹配去哪儿酒店
     * @param omsPram oms匹配酒店参数
     * @throws DmsException
     */
    QunarResult matchQunarHotel(OmsPram omsPram)throws DmsException;


    /**
     * 获取去哪儿酒店房型列表
     * @param omsPram oms 参数列表
     * @return
     * @throws DmsException
     */
    @Deprecated
    QunarRoomTypeData searchQunarRoomList(OmsPram omsPram) throws DmsException;

    /**
     * 获取去哪儿酒店产品列表列表
     * @param omsPram oms 参数列表
     * @return
     */
    QunarProductionData searchQunarProductList(OmsPram omsPram) throws DmsException;

    /**
     * 匹配渠道产品信息
     * @param omsPram oms参数列表
     */
    QunarDataResult matchQunarProduct(OmsPram omsPram) throws DmsException;

    /**
     *  解除产品匹配
     * @param omsPram oms参数列表
     * @throws DmsException
     */
    QunarResult removeDockingProduct(OmsPram omsPram) throws DmsException;

    /**
     * 10.2 PMS 物理房型删除通知
     * @param omsPram oms参数列表
     */
    QunarResult deletePhyRoomType(OmsPram omsPram) throws DmsException;

    /**
     * 去哪儿酒店解绑
     * @param omsPram oms参数列表
     */
    QunarResult removeDockingHotel(OmsPram omsPram) throws  DmsException;

    /**
     * PMS 酒店删除通知
     * @throws DmsException
     */
    QunarResult deleteHotel(OmsPram omsPram) throws  DmsException;

    /**
     * 去哪儿酒店匹配
     * @param omsPram oms参数列表
     * @throws DmsException
     */
    @Deprecated
    QunarResult matchRoomType(OmsPram omsPram) throws  DmsException;

    /**
     * 解除房型匹配
     * @param omsPram oms参数列表
     * @throws DmsException
     */
    @Deprecated
    QunarResult removeRoomType(OmsPram omsPram) throws  DmsException;



}
