package com.tomasky.doms.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.fanqie.util.DateUtil;
import com.tomasky.doms.common.DomsConstants;
import com.tomasky.doms.dto.OmsPram;
import com.tomasky.doms.dto.qunar.*;
import com.tomasky.doms.dto.qunar.response.QunarDataResult;
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
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
            return  new QunarResult(DomsConstants.ERROR_QUNAR,e.getMessage());
        }
    }

    @Override
    public QunarHotelInfo createQunarPmsHotel(OmsPram omsPram)throws DmsException{
        try{
            QunarAccount qunarAccount = qunarServiceHelper.checkQunarAccount(omsPram);
            String httpPost = HttpClientUtil.httpKvPost(QunarUrlUtil.openAccountUrl(), qunarAccount);
            QunarResult qunarResult = JacksonUtil.json2obj(httpPost, QunarResult.class);
            logger.info("渠道开通返回:"+JSON.toJSONString(qunarResult));
            //开通成功
            if (QunarResultUtil.isSuccess(httpPost,qunarResult)){
                MessageCenterUtils.saveDomsLog(OtaCode.QUNAR,Integer.valueOf(omsPram.getAccountId()),null,omsPram.getOperatorName(), LogDec.OPEN_ACCOUNT,LogDec.OPEN_ACCOUNT.getValue()+qunarResult.getMsg());
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
            throw  new DmsException("获取渠道酒店列表异常"+e.getMessage());
        }
    }

    @Override
    public QunarResult removeDockingAccount(OmsPram omsPram) throws DmsException {
        QunarRemoveAccount qunarRemoveAccount = qunarServiceHelper.checkQunarRemoveAccount(omsPram);
        String httpPost = null;
        try{
            httpPost = HttpClientUtil.httpKvPost(QunarUrlUtil.closeAccountUrl(), qunarRemoveAccount);
            QunarResult qunarResult = JacksonUtil.json2obj(httpPost, QunarResult.class);
            logger.info("removeDockingAccount删除渠道返回:"+JSON.toJSONString(qunarResult));
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
                logger.info("匹配酒店返回:"+JSON.toJSONString(qunarResult));
                if (!QunarResultUtil.isSuccess(httpPost,qunarResult)){
                    throw  new DmsException("酒店匹配异常 account:"+qunarDockingHotel.getHotelNo()+qunarResult.getMsg());
                }
            }
        } catch (Exception e) {
            throw  new DmsException("酒店匹配异常"+e.getMessage());
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
            logger.info("搜索渠道房型列表返回:"+JSON.toJSONString(qunarResult));
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
            logger.info("搜索渠道产品列表返回:"+JSON.toJSONString(qunarResult));
            if (  QunarResultUtil.isSuccess(httpPost,qunarResult)){
                return QunarHotelInfoHelper.obtQunarProductionData(httpPost);
            }else {
                throw  new DmsException("查询去哪儿产品列表异常 accountId"+omsPram.getAccountId()+qunarResult.getMsg());
            }
        } catch (Exception e) {
            logger.error("查询去哪儿产品列表异常 accountId:"+omsPram.getAccountId(),e);
            throw  new DmsException("查询去哪儿产品列表异常 accountId"+omsPram.getAccountId(),e);
        }
    }

    @Override
    public QunarDataResult matchQunarProduct(OmsPram omsPram) throws DmsException {
        QunarDataResult qunarResult = null;
        String httpPost = null;
        try {
            List<QunarDockingPhyRoomType> list = qunarServiceHelper.checkQunarDockingPhyRoomType(omsPram);
            Set<QunarDockingPhyRoomType> errorList = new HashSet<>();
            Set<QunarDockingPhyRoomType> successList = new HashSet<>();
            for(QunarDockingPhyRoomType qunarDockingPhyRoomType:list){
                logger.info("匹配产品(房型)参数："+JacksonUtil.obj2json(qunarDockingPhyRoomType));
                try{
                    httpPost = HttpClientUtil.httpKvPost(QunarUrlUtil.productionDockingUrl(), qunarDockingPhyRoomType);
                    logger.info("匹配产品返回值=>" + httpPost);
                    qunarResult = JacksonUtil.json2obj(httpPost, QunarDataResult.class);
                    logger.info("匹配产品列表返回:"+JSON.toJSONString(qunarResult));
                    if (!QunarResultUtil.isSuccess(httpPost, qunarResult) && !qunarResult.getCode().equals(-2019)) {
                        //errorList.add(qunarDockingPhyRoomType);
                        throw  new DmsException("去哪儿产品匹配异常"+qunarDockingPhyRoomType.getHotelNo()+qunarResult.getMsg());
                    }else {
                        successList.add(qunarDockingPhyRoomType);
                        qunarServiceHelper.roomOff(qunarDockingPhyRoomType);
                    }
                }catch (Exception e){
                    errorList.add(qunarDockingPhyRoomType);
                }
            }
            //匹配失败的房型 再重新去请求一次
            if (CollectionUtils.isNotEmpty(errorList)){
                Iterator<QunarDockingPhyRoomType> iterator = errorList.iterator();
                while (iterator.hasNext()){
                    QunarDockingPhyRoomType qunarDockingPhyRoomType = iterator.next();
                    logger.info("重新匹配产品参数:"+JSON.toJSONString(qunarDockingPhyRoomType));
                    try {
                        httpPost = HttpClientUtil.httpKvPost(QunarUrlUtil.productionDockingUrl(), qunarDockingPhyRoomType);
                        qunarResult = JacksonUtil.json2obj(httpPost, QunarDataResult.class);
                        logger.info("重新匹配产品返回:"+JSON.toJSONString(qunarResult));
                        if (QunarResultUtil.isSuccess(httpPost,qunarResult)){
                            qunarServiceHelper.roomOff(qunarDockingPhyRoomType);
                            successList.add(qunarDockingPhyRoomType);
                        }
                        errorList.clear();
                    }catch (Exception e){
                        logger.error("重新匹配失败的房型失败",e);
                    }

                }

            }
            if (CollectionUtils.isNotEmpty(successList)){
                logger.info("返回oms正确产品集合:"+JSON.toJSONString(successList));
                qunarResult.setData(JSON.toJSONString(successList));
            }
            return new QunarDataResult(Integer.valueOf(qunarResult.getCode()), qunarResult.getMsg(), qunarResult.getData());
        } catch (Exception e) {
            logger.error("去哪儿产品匹配异常", e);
            throw  new DmsException("去哪儿产品匹配异常"+e.getMessage());
        }
    }

    @Override
    public QunarResult removeDockingProduct(OmsPram omsPram) throws DmsException {
        QunarResult qunarResult = null;
        String httpPost = null;
        RoomOnOff roomOnOff = null;
        try {
            List<QunarDockingRemovePhyRoomType> list = qunarServiceHelper.checkQunarDockingRemovePhyRoomType(omsPram);
            for(QunarDockingRemovePhyRoomType qunarDockingRemovePhyRoomType:list){
                logger.info("解绑产品(房型)参数："+JacksonUtil.obj2json(qunarDockingRemovePhyRoomType));

                roomOnOff = new RoomOnOff();
                BeanUtils.copyProperties(roomOnOff,qunarDockingRemovePhyRoomType);
                roomOnOff.setFromDate(DateUtil.fromDate(0));
                roomOnOff.setToDate(DateUtil.fromDate(ResourceBundleUtil.getInt("qunar.day")));
                logger.info("关房参数:"+JacksonUtil.obj2json(roomOnOff));
                String roomOn = HttpClientUtil.httpKvPost(QunarUrlUtil.roomOff(), roomOnOff);
                qunarResult = JacksonUtil.json2obj(roomOn, QunarResult.class);
                logger.error("关房房结果"+JacksonUtil.obj2json(qunarResult));

                httpPost = HttpClientUtil.httpKvPost(QunarUrlUtil.productionRemoveDockingUrl(), qunarDockingRemovePhyRoomType);
                qunarResult = JacksonUtil.json2obj(httpPost, QunarResult.class);
                logger.info("删除渠道产品列表返回:"+JSON.toJSONString(qunarResult));
                if (!QunarResultUtil.isSuccess(httpPost,qunarResult)){
                    throw  new DmsException("去哪儿删除产品匹配异常 account:"+qunarDockingRemovePhyRoomType.getHotelNo()+qunarResult.getMsg());
                }
            }
            return qunarResult;
        } catch (Exception e) {
            logger.error("去哪儿删除产品匹配异常", e);
            throw  new DmsException("去哪儿删除产品匹配异常"+e.getMessage());
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
                logger.info("解绑房型（产品）返回:"+JacksonUtil.obj2json(qunarResult));
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
               logger.info("解绑酒店参数："+JacksonUtil.obj2json(dockingRemoveHotel));
               httpPost = HttpClientUtil.httpKvPost(QunarUrlUtil.removeDockingHotelUrl(), dockingRemoveHotel);
               qunarResult = JacksonUtil.json2obj(httpPost, QunarResult.class);
                logger.info("解绑酒店返回:"+JSON.toJSONString(qunarResult));
               if (!QunarResultUtil.isSuccess(httpPost,qunarResult)){
                  throw  new DmsException("去哪儿酒店解绑异常 innId:"+dockingRemoveHotel.getHotelNo()+qunarResult.getMsg());
               }
            }
           return qunarResult;
        } catch (Exception e) {
            logger.error("去哪儿酒店解绑异常",e);
            throw  new DmsException("去哪儿酒店解绑异常"+e.getMessage());
        }
    }

    @Override
    public QunarResult deleteHotel(OmsPram omsPram) throws DmsException {
        List<QunarRemoveHotel> list = qunarServiceHelper.checkRemoveHotel(omsPram);
        QunarResult qunarResult = null;
        String httpPost = null;
        try {
            for (QunarRemoveHotel qunarRemoveHotel:list){
                httpPost = HttpClientUtil.httpKvPost(QunarUrlUtil.removeHotelUrl(), qunarRemoveHotel);
                qunarResult = JacksonUtil.json2obj(httpPost, QunarResult.class);
                if (!QunarResultUtil.isSuccess(httpPost,qunarResult)){
                    throw  new DmsException("去哪儿酒店解绑异常 innId:"+qunarRemoveHotel.getHotelNo()+qunarResult.getMsg());
                }
            }
            return qunarResult;
        } catch (Exception e) {
            logger.error("去哪儿酒店解绑异常",e);
            throw  new DmsException("去哪儿酒店解绑异常"+e.getMessage());
        }
    }

    @Override
    public QunarResult matchRoomType(OmsPram omsPram) throws DmsException {
        QunarResult qunarResult = null;
        String httpPost = null;
        try {
            List<QunarDockingPhyRoomType> list = qunarServiceHelper.checkQunarDockingPhyRoomType(omsPram);
            for(QunarDockingPhyRoomType qunarDockingPhyRoomType:list){
                logger.info("匹配房型参数："+JacksonUtil.obj2json(qunarDockingPhyRoomType));
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
                logger.info("删除房型=参数："+JacksonUtil.obj2json(qunarDockingRemovePhyRoomType));
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
