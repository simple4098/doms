package com.tomasky.doms.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.fanqie.util.DateUtil;
import com.tomasky.doms.common.Constants;
import com.tomasky.doms.dto.OmsPram;
import com.tomasky.doms.dto.qunar.*;
import com.tomasky.doms.dto.qunar.response.QunarHotelInfo;
import com.tomasky.doms.dto.qunar.response.QunarProductionData;
import com.tomasky.doms.dto.qunar.response.QunarResult;
import com.tomasky.doms.dto.qunar.response.QunarRoomTypeData;
import com.tomasky.doms.enums.LogDec;
import com.tomasky.doms.enums.OtaCode;
import com.tomasky.doms.exception.DmsException;
import com.tomasky.doms.helper.QunarHotelInfoHelper;
import com.tomasky.doms.helper.QunarServiceHelper;
import com.tomasky.doms.service.IQunarService;
import com.tomasky.doms.support.util.*;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * DESC : 对接去哪接口实现类
 * 建立去哪与番茄的关系
 * @author : 番茄木-ZLin
 * @data : 2016/6/7
 * @version: v1.0.0
 */
@Service
public class QunarService implements IQunarService {
    private final  static Logger logger = LoggerFactory.getLogger(QunarService.class);

    @Resource
    private QunarServiceHelper qunarServiceHelper;



    @Override
    public QunarResult sendQunarPhoneCode( QunarMobile qunarMobile) {
        try {
            qunarServiceHelper.checkQunarMobile(qunarMobile);
            String httpPost = HttpClientUtil.httpKvPost(QunarUrlUtil.sendCodeUrl(), qunarMobile);
            QunarResult qunarResult = JacksonUtil.json2obj(httpPost, QunarResult.class);
            //成功、失败记日志
            if (QunarResultUtil.isSuccess(httpPost,qunarResult)){
                //todo
                logger.info("发送动态验证码 成功 【"+qunarMobile.getMobile()+"】");
            }else {
                logger.info("发送动态验证码 失败【"+qunarMobile.getMobile()+"】");
                MessageCenterUtils.saveDomsLog(OtaCode.QUNAR,null,null,qunarMobile.getOperatorName(), LogDec.SEND_CODE,"发送动态验证码 失败【"+qunarMobile.getMobile()+"】");
            }
            return qunarResult;
        } catch (Exception e) {
            logger.error("去哪儿发送验证异常",e);
            return  new QunarResult(Constants.ERROR_QUNAR,e.getMessage());
        }
    }

    @Override
    public QunarHotelInfo createQunarPmsHotel(OmsPram omsPram)throws DmsException{
        //OtaInfoDto otaInfo = otaInfoDao.selectByOtaId(omsPram.getOtaId());
        try{
            QunarAccount qunarAccount = qunarServiceHelper.checkQunarAccount(omsPram);
            String httpPost = HttpClientUtil.httpKvPost(QunarUrlUtil.openAccountUrl(), qunarAccount);
            QunarResult qunarResult = JacksonUtil.json2obj(httpPost, QunarResult.class);
            //开通成功
            if (QunarResultUtil.isSuccess(httpPost,qunarResult)){
                //todo 开通成功 记录日志
                //MessageCenterUtils.saveDomsLog(OtaCode.QUNAR,omsPram,null,omsPram.getOperatorName(), LogDec.OPEN_ACCOUNT,LogDec.OPEN_ACCOUNT.getValue()+qunarResult.getMsg());
                QunarHotelInfo qunarHotelInfo = QunarHotelInfoHelper.obtQunarHotelInfo(omsPram.getAccountId());
                if (qunarHotelInfo!=null){
                    logger.info("渠道酒店列表:"+JacksonUtil.obj2json(qunarHotelInfo));
                }
                return qunarHotelInfo;
            }else {
                throw  new DmsException(qunarResult.getMsg());
            }
        }catch (Exception e){
            logger.error("createQunarPmsHotel 异常",e);
            throw  new DmsException("获取渠道酒店列表异常",e);
        }
    }

    @Override
    public QunarResult removeDockingAccount(OmsPram omsPram) throws DmsException {
        QunarRemoveAccount qunarRemoveAccount = qunarServiceHelper.checkQunarRemoveAccount(omsPram);
        String httpPost = null;
        try{
            httpPost = HttpClientUtil.httpKvPost(QunarUrlUtil.closeAccountUrl(), qunarRemoveAccount);
            QunarResult qunarResult = JacksonUtil.json2obj(httpPost, QunarResult.class);
            if (QunarResultUtil.isSuccess(httpPost,qunarResult)){
                //todo 记录日志
                return qunarResult;
            }else {
                throw  new DmsException(qunarResult.getMsg());
            }
        }catch (Exception e){
            throw  new DmsException("关闭渠道直连异常",e);
        }
    }

    @Override
    public QunarResult matchQunarHotel(OmsPram omsPram) throws DmsException {
        List<QunarDockingHotel> qunarDockingHotelList = qunarServiceHelper.matchQunarHotelParam(omsPram);
        QunarResult qunarResult =null;
        String httpPost = null;
        try {
            for (QunarDockingHotel qunarDockingHotel:qunarDockingHotelList){
                httpPost = HttpClientUtil.httpKvPost(QunarUrlUtil.matchHotelUrl(), qunarDockingHotel);
                qunarResult = JacksonUtil.json2obj(httpPost, QunarResult.class);
                if (!QunarResultUtil.isSuccess(httpPost,qunarResult)){
                    throw  new DmsException("酒店匹配异常 innId:"+qunarDockingHotel.getHotelNo()+qunarResult.getMsg());
                }
            }
        } catch (Exception e) {
            throw  new DmsException("酒店匹配异常",e);
        }
        return qunarResult;
    }

    @Override
    public QunarRoomTypeData searchQunarRoomList(OmsPram omsPram) throws DmsException {

        QunarAccountAndHotel qunarAccountAndHotel = qunarServiceHelper.checkQunarAccountAndHotel(omsPram);
        try {
            String httpPost = HttpClientUtil.httpKvPost(QunarUrlUtil.searchRoomTypeUrl(), qunarAccountAndHotel);
            QunarResult qunarResult = JSON.parseObject(httpPost, new TypeReference<QunarResult>() {
            });
            if (  QunarResultUtil.isSuccess(httpPost,qunarResult)){
                return QunarHotelInfoHelper.obtQunarRoomTypeData(httpPost);
            }else {
                throw  new DmsException("查询去哪儿房型列表异常 accountId"+omsPram.getParam());
            }
        } catch (Exception e) {
            logger.error("查询去哪儿房型列表异常 accountId:"+omsPram.getAccountId(),e);
            throw  new DmsException("查询去哪儿房型列表异常 accountId"+omsPram.getAccountId(),e);
        }
    }

    @Override
    public QunarProductionData searchQunarProductList(OmsPram omsPram) throws DmsException {
        QunarAccountAndHotel qunarAccountAndHotel = qunarServiceHelper.checkQunarAccountAndHotel(omsPram);
        try {
            String httpPost = HttpClientUtil.httpKvPost(QunarUrlUtil.productionSearchUrl(), qunarAccountAndHotel);
            QunarResult qunarResult = JSON.parseObject(httpPost, new TypeReference<QunarResult>() {
            });
            if (  QunarResultUtil.isSuccess(httpPost,qunarResult)){
                return QunarHotelInfoHelper.obtQunarProductionData(httpPost);
            }else {
                throw  new DmsException("查询去哪儿产品列表异常 accountId"+omsPram.getAccountId());
            }
        } catch (Exception e) {
            logger.error("查询去哪儿产品列表异常 accountId:"+omsPram.getAccountId(),e);
            throw  new DmsException("查询去哪儿产品列表异常 accountId"+omsPram.getAccountId(),e);
        }
    }

    @Override
    public QunarResult matchQunarProduct(OmsPram omsPram) throws DmsException {

        QunarResult qunarResult = null;
        String httpPost = null;
        try {
            List<QunarDockingPhyRoomType> list = qunarServiceHelper.checkQunarDockingPhyRoomType(omsPram);
            for(QunarDockingPhyRoomType qunarDockingPhyRoomType:list){
                httpPost = HttpClientUtil.httpKvPost(QunarUrlUtil.productionDockingUrl(), qunarDockingPhyRoomType);
                qunarResult = JacksonUtil.json2obj(httpPost, QunarResult.class);
                if (!QunarResultUtil.isSuccess(httpPost,qunarResult)){
                    throw  new DmsException("去哪儿产品匹配异常 innId:"+qunarDockingPhyRoomType.getHotelNo()+qunarResult.getMsg());
                }else {
                    RoomOnOff roomOnOff = new RoomOnOff();
                    BeanUtils.copyProperties(roomOnOff,qunarDockingPhyRoomType);
                    roomOnOff.setFromDate(DateUtil.fromDate(0));
                    roomOnOff.setToDate(DateUtil.fromDate(ResourceBundleUtil.getInt("qunar.day")));
                    String roomOn = HttpClientUtil.httpKvPost(QunarUrlUtil.roomOn(), roomOnOff);
                    qunarResult = JacksonUtil.json2obj(roomOn, QunarResult.class);
                    logger.error("开房结果"+JacksonUtil.obj2json(qunarResult));
                }
            }
            return qunarResult;
        } catch (Exception e) {
            logger.error("去哪儿产品匹配异常", e);
            throw  new DmsException("去哪儿产品匹配异常",e);
        }
    }

    @Override
    public QunarResult removeDockingProduct(OmsPram omsPram) throws DmsException {
        QunarResult qunarResult = null;
        String httpPost = null;
        try {
            List<QunarDockingRemovePhyRoomType> list = qunarServiceHelper.checkQunarDockingRemovePhyRoomType(omsPram);
            for(QunarDockingRemovePhyRoomType qunarDockingRemovePhyRoomType:list){
                httpPost = HttpClientUtil.httpKvPost(QunarUrlUtil.productionRemoveDockingUrl(), qunarDockingRemovePhyRoomType);
                qunarResult = JacksonUtil.json2obj(httpPost, QunarResult.class);
                if (!QunarResultUtil.isSuccess(httpPost,qunarResult)){
                    throw  new DmsException("去哪儿删除产品匹配异常 account:"+qunarDockingRemovePhyRoomType.getHotelNo()+qunarResult.getMsg());
                }else {
                    RoomOnOff roomOnOff = new RoomOnOff();
                    BeanUtils.copyProperties(roomOnOff,qunarDockingRemovePhyRoomType);
                    roomOnOff.setFromDate(DateUtil.fromDate(0));
                    roomOnOff.setToDate(DateUtil.fromDate(ResourceBundleUtil.getInt("qunar.day")));
                    String roomOn = HttpClientUtil.httpKvPost(QunarUrlUtil.roomOff(), roomOnOff);
                    qunarResult = JacksonUtil.json2obj(roomOn, QunarResult.class);
                    logger.error("关房房结果"+JacksonUtil.obj2json(qunarResult));
                }
            }
            return qunarResult;
        } catch (Exception e) {
            logger.error("去哪儿删除产品匹配异常", e);
            throw  new DmsException("去哪儿删除产品匹配异常",e);
        }
    }

    @Override
    public QunarResult deletePhyRoomType(OmsPram omsPram) throws DmsException {

        QunarResult qunarResult = null;
        String httpPost = null;
        try {
            List<QunarDeletePhyRoomType> list = qunarServiceHelper.checkQunarDeletePhyRoomType(omsPram);
            for(QunarDeletePhyRoomType qunarDeletePhyRoomType:list){
                httpPost = HttpClientUtil.httpKvPost(QunarUrlUtil.deletePhyRoomTypeUrl(), qunarDeletePhyRoomType);
                qunarResult = JacksonUtil.json2obj(httpPost, QunarResult.class);
                if (!QunarResultUtil.isSuccess(httpPost,qunarResult)){
                    throw  new DmsException("去哪儿产品匹配异常 innId:"+qunarDeletePhyRoomType.getHotelNo()+qunarResult.getMsg());
                }
            }
            return qunarResult;
        } catch (Exception e) {
            logger.error("去哪儿删除产品匹配异常", e);
            throw  new DmsException("去哪儿删除产品匹配异常",e);
        }
    }

    @Override
    public QunarResult removeDockingHotel(OmsPram omsPram) throws DmsException {
        List<QunarDockingRemoveHotel> qunarDockingRemoveHotel = qunarServiceHelper.checkQunarDockingRemoveHotel(omsPram);
        QunarResult qunarResult = null;
        String httpPost = null;
        try {
            for (QunarDockingRemoveHotel dockingRemoveHotel:qunarDockingRemoveHotel){
               httpPost = HttpClientUtil.httpKvPost(QunarUrlUtil.removeDockingHotelUrl(), dockingRemoveHotel);
                qunarResult = JacksonUtil.json2obj(httpPost, QunarResult.class);
                if (!QunarResultUtil.isSuccess(httpPost,qunarResult)){
                    throw  new DmsException("去哪儿酒店解绑异常 innId:"+dockingRemoveHotel.getHotelNo()+qunarResult.getMsg());
                }
            }
           return qunarResult;
        } catch (Exception e) {
            logger.error("去哪儿酒店解绑异常",e);
            throw  new DmsException("去哪儿酒店解绑异常",e);
        }
    }

    @Override
    public QunarResult matchRoomType(OmsPram omsPram) throws DmsException {
        QunarResult qunarResult = null;
        String httpPost = null;
        try {
            List<QunarDockingPhyRoomType> list = qunarServiceHelper.checkQunarDockingPhyRoomType(omsPram);
            for(QunarDockingPhyRoomType qunarDockingPhyRoomType:list){
                httpPost = HttpClientUtil.httpKvPost(QunarUrlUtil.removeDockingHotelUrl(), qunarDockingPhyRoomType);
                qunarResult = JacksonUtil.json2obj(httpPost, QunarResult.class);
                if (!QunarResultUtil.isSuccess(httpPost,qunarResult)){
                    throw  new DmsException("去哪儿匹配房型异常 innId:"+qunarDockingPhyRoomType.getHotelNo()+qunarResult.getMsg());
                }
            }
            return qunarResult;
        } catch (Exception e) {
            logger.error("去哪儿匹配房型异常", e);
            throw  new DmsException("去哪儿匹配房型异常",e);
        }
    }

    @Override
    public QunarResult removeRoomType(OmsPram omsPram) throws DmsException {
        QunarResult qunarResult = null;
        String httpPost = null;
        try {
            List<QunarDockingRemovePhyRoomType> list = qunarServiceHelper.checkQunarDockingRemovePhyRoomType(omsPram);
            for(QunarDockingRemovePhyRoomType qunarDockingRemovePhyRoomType:list){
                httpPost = HttpClientUtil.httpKvPost(QunarUrlUtil.removeDockingHotelUrl(), qunarDockingRemovePhyRoomType);
                qunarResult = JacksonUtil.json2obj(httpPost, QunarResult.class);
                if (!QunarResultUtil.isSuccess(httpPost,qunarResult)){
                    throw  new DmsException("去哪儿匹配房型异常 innId:"+qunarDockingRemovePhyRoomType.getHotelNo()+qunarResult.getMsg());
                }
            }
            return qunarResult;
        } catch (Exception e) {
            logger.error("去哪儿解除匹配房型异常", e);
            throw  new DmsException("去哪儿解除酒店解绑异常",e);
        }
    }
}
