package com.tomasky.doms.helper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.tomasky.doms.common.CommonApi;
import com.tomasky.doms.dto.OmsPram;
import com.tomasky.doms.dto.oms.*;
import com.tomasky.doms.dto.qunar.*;
import com.tomasky.doms.exception.DmsException;
import com.tomasky.doms.support.util.JacksonUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2016/6/7
 * @version: v1.0.0
 */
@Component
public class QunarServiceHelper {

    private final  static Logger logger = LoggerFactory.getLogger(QunarServiceHelper.class);

    public  void checkQunarMobile(QunarMobile qunarMobile)throws DmsException{
        try{
            Assert.notNull(qunarMobile.getMobile());
            if (StringUtils.isEmpty(qunarMobile.getUserIp())){
                qunarMobile.setUserIp(CommonApi.userIp);
            }
            logger.info("发送验证码开始 参数列表:"+ JacksonUtil.obj2json(qunarMobile));
        }catch (Exception e){
            throw  new DmsException("发送验证码参数异常,电话号码为空");
        }
    }

    public QunarAccount checkQunarAccount(OmsPram omsPram)throws DmsException {
        try{
            Assert.notNull(omsPram.getOtaId());
            Assert.notNull(omsPram.getPhoneCode());
            Assert.notNull(omsPram.getMobile());
            Assert.notNull(omsPram.getAccountId());
            Assert.notNull(omsPram.getOperatorGuid());
            Assert.notNull(omsPram.getOperatorName());

            QunarAccount qunarAccount = new QunarAccount();
            qunarAccount.setUserAccount(omsPram.getMobile());
            qunarAccount.setVerificationCode(omsPram.getPhoneCode());
            qunarAccount.setOperatorGuid(omsPram.getOperatorGuid());
            qunarAccount.setOperatorName(omsPram.getOperatorName());
            qunarAccount.setHotelNo(omsPram.getAccountId());
            if (StringUtils.isEmpty(omsPram.getUserIp())){
                qunarAccount.setUserIp(CommonApi.userIp);
            }
            logger.info("获取酒店酒店列表参数:"+ JacksonUtil.obj2json(qunarAccount));
            return qunarAccount;
        }catch (Exception e){
            throw  new DmsException("开通渠道参数异常");
        }
    }

    public QunarRemoveAccount checkQunarRemoveAccount(OmsPram omsPram) {
        Assert.notNull(omsPram.getOtaId());
        Assert.notNull(omsPram.getUserAccount());
        Assert.notNull(omsPram.getAccountId());
        Assert.notNull(omsPram.getOperatorGuid());
        Assert.notNull(omsPram.getOperatorName());
        QunarRemoveAccount qunarRemoveAccount = new QunarRemoveAccount();
        qunarRemoveAccount.setHotelNo(omsPram.getAccountId());
        qunarRemoveAccount.setUserAccount(omsPram.getUserAccount());
        qunarRemoveAccount.setOperatorGuid(omsPram.getOperatorGuid());
        qunarRemoveAccount.setOperatorName(omsPram.getOperatorName());
        logger.info("解绑账号参数:"+JSON.toJSONString(qunarRemoveAccount));
        return qunarRemoveAccount;
    }

    public List<QunarDockingHotel> matchQunarHotelParam(OmsPram omsPram){

        List<QunarDockingHotel> list = new ArrayList<>();
        String data = omsPram.getParam();
        Assert.notNull(data);
        logger.info("匹配酒店参数："+data);
        ChannelInfoData channelInfoData = JacksonUtil.json2obj(data, ChannelInfoData.class);
        Assert.notNull(channelInfoData.getOperatorGuid());
        Assert.notNull(channelInfoData.getOperatorName());
        Assert.notNull(channelInfoData.getAccountId());
        QunarDockingHotel qunarDockingHotel = null;
        List<ChannelInfo> channelInfoList = channelInfoData.getChannelInfo();
        for (ChannelInfo channelInfo:channelInfoList){
            String userAccount = channelInfo.getUserAccount();
            List<OmsQunarHotel> channelHotelList = channelInfo.getChannelHotelList();
            for (OmsQunarHotel omsQunarHotel:channelHotelList){
                qunarDockingHotel = new QunarDockingHotel();
                qunarDockingHotel.setUserAccount(userAccount);
                qunarDockingHotel.setHotelNo(channelInfoData.getAccountId());
                qunarDockingHotel.setHotelName(channelInfoData.getInnName());
                qunarDockingHotel.setChannelHotelName(omsQunarHotel.getChannelHotelName());
                qunarDockingHotel.setChannelHotelNo(omsQunarHotel.getChannelHotelNo());
                qunarDockingHotel.setOperatorGuid(channelInfoData.getOperatorGuid());
                qunarDockingHotel.setOperatorName(channelInfoData.getOperatorName());
                list.add(qunarDockingHotel);
            }
        }
        return list;
    }

    public static void main(String[] args) {
        String data="{\"accountId\":\"63866\",\"channelInfo\":[{\"channelHotelList\":[{\"channelHotelName\":\"的的的\",\"channelHotelNo\":\"1000156065\"}],\"userAccount\":\"15281017068\"}],\"hotelName\":\"番茄mumu测试客栈\",\"innId\":\"49463\",\"operatorGuid\":\"54484\",\"operatorName\":\"54484\"}";
        QunarServiceHelper qunarServiceHelper = new QunarServiceHelper();
        OmsPram omsPram = new OmsPram();
        omsPram.setParam(data);
        List<QunarDockingHotel> qunarDockingHotelList = qunarServiceHelper.matchQunarHotelParam(omsPram);
    }
    /**
     * 房型列表参数
     * @param omsPram
     * @return
     */
    public QunarAccountAndHotel checkQunarAccountAndHotel(OmsPram omsPram) {
        String accountId = omsPram.getAccountId();
        Assert.notNull(accountId);
        return new QunarAccountAndHotel(accountId);
    }

    /**
     * 房型解绑参数检测
     * @param omsPram oms参数列表
     * @return
     */
    public List<QunarDockingRemoveHotel> checkQunarDockingRemoveHotel(OmsPram omsPram) {
        String data = omsPram.getParam();
        Assert.notNull(data);
        logger.info("酒店解绑参数:"+data);
        /*Assert.notNull(omsPram.getAccountId());*/
        List<OmsHotel> omsHotelList = JacksonUtil.json2list(data,OmsHotel.class);
        List<QunarDockingRemoveHotel> removeHotelList = new ArrayList<>();
        QunarDockingRemoveHotel qunarDockingRemoveHotel = null;
        for (OmsHotel omsHotel:omsHotelList){
            qunarDockingRemoveHotel = new QunarDockingRemoveHotel();
            qunarDockingRemoveHotel.setHotelNo(omsPram.getAccountId());
            qunarDockingRemoveHotel.setChannelHotelNo(omsHotel.getChannelHotelNo());
            qunarDockingRemoveHotel.setOperatorGuid(omsHotel.getOperatorGuid());
            qunarDockingRemoveHotel.setOperatorName(omsHotel.getOperatorName());
            removeHotelList.add(qunarDockingRemoveHotel);
        }
        return removeHotelList;
    }

    public List<QunarDockingPhyRoomType> checkQunarDockingPhyRoomType(OmsPram omsPram) throws Exception {
        String data = omsPram.getParam();
        Assert.notNull(data);
        Assert.notNull(omsPram.getAccountId());
        //List<OmsSjRoomType> list = JacksonUtil.json2list(data, OmsSjRoomType.class);
        List<OmsSjRoomType> list = JSON.parseObject(data, new TypeReference<List<OmsSjRoomType>>() {
        });
        logger.info("匹配产品(房型)参数JSON:"+data+" accountId:"+omsPram.getAccountId());
        List<QunarDockingPhyRoomType> qunarDockingPhyRoomTypeList = new ArrayList<>();
        QunarDockingPhyRoomType qunarDockingPhyRoomType = null;
        for (OmsSjRoomType omsSjRoomType:list){
            qunarDockingPhyRoomType = new QunarDockingPhyRoomType();
            BeanUtils.copyProperties(qunarDockingPhyRoomType, omsSjRoomType);
            qunarDockingPhyRoomType.setHotelNo(omsPram.getAccountId());
            qunarDockingPhyRoomType.setChannelHotelNo(omsSjRoomType.getChannelHotelNo());
            qunarDockingPhyRoomType.setPhyRoomTypeCode(omsSjRoomType.getRoomTypeId());
            qunarDockingPhyRoomType.setPhyRoomTypeName(omsSjRoomType.getRoomTypeName());
            qunarDockingPhyRoomTypeList.add(qunarDockingPhyRoomType);
         }
        return qunarDockingPhyRoomTypeList;
    }

    public List<QunarDockingRemovePhyRoomType> checkQunarDockingRemovePhyRoomType(OmsPram omsPram) throws Exception {
        String data = omsPram.getParam();
        Assert.notNull(data);
        Assert.notNull(omsPram.getAccountId());
        List<OmsXjRoomType> omsXjRoomTypeList = JacksonUtil.json2list(data, OmsXjRoomType.class);
        List<QunarDockingRemovePhyRoomType> list = new ArrayList<>();
        QunarDockingRemovePhyRoomType qunarDockingRemovePhyRoomType = null;
        for (OmsXjRoomType omsXjRoomType:omsXjRoomTypeList){
            qunarDockingRemovePhyRoomType = new QunarDockingRemovePhyRoomType();
            BeanUtils.copyProperties(qunarDockingRemovePhyRoomType,omsXjRoomType);
            qunarDockingRemovePhyRoomType.setHotelNo(omsPram.getAccountId());
            qunarDockingRemovePhyRoomType.setPhyRoomTypeCode(omsXjRoomType.getRoomTypeId());

            list.add(qunarDockingRemovePhyRoomType);

        }
        return list;
    }

    public List<QunarDeletePhyRoomType> checkQunarDeletePhyRoomType(OmsPram omsPram) {
        String data = omsPram.getParam();
        Assert.notNull(data);
        Assert.notNull(omsPram.getAccountId());
        List<OmsXjRoomType> omsXjRoomTypeList = JSON.parseObject(data, new TypeReference<List<OmsXjRoomType>>() {});
        logger.info("解绑房型（产品）参数"+data+" accountId:"+omsPram.getAccountId());
        List<QunarDeletePhyRoomType> list = new ArrayList<>();
        QunarDeletePhyRoomType qunarDeletePhyRoomType = null;
        for (OmsXjRoomType omsXjRoomType:omsXjRoomTypeList){
            qunarDeletePhyRoomType = new QunarDeletePhyRoomType();
            qunarDeletePhyRoomType.setHotelNo(omsPram.getAccountId());
            qunarDeletePhyRoomType.setPhyRoomTypeCode(omsXjRoomType.getRoomTypeId());
            list.add(qunarDeletePhyRoomType);

        }
        return list;
    }

    public List<QunarRemoveHotel> checkRemoveHotel(OmsPram omsPram) {
        String data = omsPram.getParam();
        Assert.notNull(data);
        Assert.notNull(omsPram.getAccountId());
        List<OmsHotel> omsHotelList = JacksonUtil.json2list(data,OmsHotel.class);
        List<QunarRemoveHotel> removeHotelList = new ArrayList<>();
        QunarRemoveHotel qunarRemoveHotel = null;
        for (OmsHotel omsHotel:omsHotelList){
            qunarRemoveHotel = new QunarRemoveHotel();
            qunarRemoveHotel.setHotelNo(omsPram.getAccountId());
            removeHotelList.add(qunarRemoveHotel);
        }
        return removeHotelList;
    }
}
