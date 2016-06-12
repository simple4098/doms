package com.tomasky.doms.service;

import com.tomasky.doms.dto.OmsPram;
import com.tomasky.doms.dto.qunar.QunarMobile;
import com.tomasky.doms.dto.qunar.response.QunarHotelInfo;
import com.tomasky.doms.dto.qunar.response.QunarResult;
import com.tomasky.doms.dto.qunar.response.QunarRoomTypeData;
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
     * @param otaId 渠道id
     * @param qunarMobile 调用去哪儿接口参数对象
     */
    QunarResult sendQunarPhoneCode(String otaId,QunarMobile qunarMobile);

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
    QunarRoomTypeData searchQunarRoomList(OmsPram omsPram) throws DmsException;

    /**
     * 去哪儿酒店解绑
     * @param omsPram oms参数列表
     */
    QunarResult removeDockingHotel(OmsPram omsPram) throws  DmsException;

    /**
     * 去哪儿酒店匹配
     * @param omsPram oms参数列表
     * @throws DmsException
     */
    QunarResult matchRoomType(OmsPram omsPram) throws  DmsException;

    /**
     * 解除房型匹配
     * @param omsPram oms参数列表
     * @throws DmsException
     */
    QunarResult removeRoomType(OmsPram omsPram) throws  DmsException;

}
