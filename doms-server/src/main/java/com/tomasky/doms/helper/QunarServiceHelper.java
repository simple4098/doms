package com.tomasky.doms.helper;

import com.tomasky.doms.common.CommonApi;
import com.tomasky.doms.dto.OmsPram;
import com.tomasky.doms.dto.oms.*;
import com.tomasky.doms.dto.qunar.*;
import com.tomasky.doms.exception.DmsException;
import com.tomasky.doms.support.util.JacksonUtil;
import org.apache.commons.lang3.StringUtils;
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

    public  void checkQunarMobile(QunarMobile qunarMobile)throws DmsException{
        try{
            Assert.notNull(qunarMobile.getMobile());
            if (StringUtils.isEmpty(qunarMobile.getUserIp())){
                qunarMobile.setUserIp(CommonApi.userIp);
            }
        }catch (Exception e){
            throw  new DmsException("发送验证码参数异常,电话号码为空");
        }
    }

    public QunarAccount checkQunarAccount(OmsPram omsPram)throws DmsException {
        try{
            Assert.notNull(omsPram.getOtaId());
            Assert.notNull(omsPram.getPhoneCode());
            Assert.notNull(omsPram.getMobile());
            Assert.notNull(omsPram.getInnId());
            Assert.notNull(omsPram.getOperatorGuid());
            Assert.notNull(omsPram.getOperatorName());

            QunarAccount qunarAccount = new QunarAccount();
            qunarAccount.setUserAccount(omsPram.getMobile());
            qunarAccount.setVerificationCode(omsPram.getPhoneCode());
            qunarAccount.setOperatorGuid(omsPram.getOperatorGuid());
            qunarAccount.setOperatorName(omsPram.getOperatorName());
            qunarAccount.setHotelNo(omsPram.getInnId().toString());
            if (StringUtils.isEmpty(omsPram.getUserIp())){
                qunarAccount.setUserIp(CommonApi.userIp);
            }
            return qunarAccount;
        }catch (Exception e){
            throw  new DmsException("开通渠道参数异常");
        }
    }

    public QunarRemoveAccount checkQunarRemoveAccount(OmsPram omsPram) {
        Assert.notNull(omsPram.getOtaId());
        Assert.notNull(omsPram.getMobile());
        Assert.notNull(omsPram.getInnId());
        Assert.notNull(omsPram.getOperatorGuid());
        Assert.notNull(omsPram.getOperatorName());

        QunarRemoveAccount qunarRemoveAccount = new QunarRemoveAccount();
        qunarRemoveAccount.setHotelNo(omsPram.getInnId().toString());
        qunarRemoveAccount.setUserAccount(omsPram.getMobile());
        qunarRemoveAccount.setOperatorGuid(omsPram.getOperatorGuid());
        qunarRemoveAccount.setOperatorName(omsPram.getOperatorName());
        return qunarRemoveAccount;
    }

    public List<QunarDockingHotel> matchQunarHotelParam(OmsPram omsPram){

        List<QunarDockingHotel> list = new ArrayList<>();
        String data = omsPram.getData();
        Assert.notNull(data);
        ChannelInfoData channelInfoData = JacksonUtil.json2obj(data, ChannelInfoData.class);
        Assert.notNull(channelInfoData.getOperatorGuid());
        Assert.notNull(channelInfoData.getOperatorName());
        Assert.notNull(channelInfoData.getInnId());
        QunarDockingHotel qunarDockingHotel = null;
        List<ChannelInfo> channelInfoList = channelInfoData.getChannelInfo();
        for (ChannelInfo channelInfo:channelInfoList){
            String userAccount = channelInfo.getUserAccount();
            List<OmsQunarHotel> channelHotelList = channelInfo.getChannelHotelList();
            for (OmsQunarHotel omsQunarHotel:channelHotelList){
                qunarDockingHotel = new QunarDockingHotel();
                qunarDockingHotel.setUserAccount(userAccount);
                qunarDockingHotel.setHotelNo(channelInfoData.getInnId().toString());
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

    /**
     * 房型列表参数
     * @param omsPram
     * @return
     */
    public QunarAccountAndHotel checkQunarAccountAndHotel(OmsPram omsPram) {
        Integer innId = omsPram.getInnId();
        Assert.notNull(innId);
        return new QunarAccountAndHotel(innId.toString());
    }

    /**
     * 房型解绑参数检测
     * @param omsPram oms参数列表
     * @return
     */
    public List<QunarDockingRemoveHotel> checkQunarDockingRemoveHotel(OmsPram omsPram) {
        String data = omsPram.getData();
        Assert.notNull(data);
        List<OmsHotel> omsHotelList = JacksonUtil.json2list(data,OmsHotel.class);
        List<QunarDockingRemoveHotel> removeHotelList = new ArrayList<>();
        QunarDockingRemoveHotel qunarDockingRemoveHotel = null;
        for (OmsHotel omsHotel:omsHotelList){
            qunarDockingRemoveHotel = new QunarDockingRemoveHotel();
            qunarDockingRemoveHotel.setHotelNo(omsHotel.getInnId().toString());
            qunarDockingRemoveHotel.setChannelHotelNo(omsHotel.getChannelHotelNo());
            qunarDockingRemoveHotel.setOperatorGuid(omsHotel.getOperatorGuid());
            qunarDockingRemoveHotel.setOperatorName(omsHotel.getOperatorName());
            removeHotelList.add(qunarDockingRemoveHotel);
        }
        return removeHotelList;
    }

    public List<QunarDockingPhyRoomType> checkQunarDockingPhyRoomType(OmsPram omsPram) throws Exception {
        String data = omsPram.getData();
        Assert.notNull(data);
        List<OmsSjRoomType> list = JacksonUtil.json2list(data, OmsSjRoomType.class);
        List<QunarDockingPhyRoomType> qunarDockingPhyRoomTypeList = new ArrayList<>();
        QunarDockingPhyRoomType qunarDockingPhyRoomType = null;
        for (OmsSjRoomType omsSjRoomType:list){
            qunarDockingPhyRoomType = new QunarDockingPhyRoomType();
            //BeanUtils.copyProperties(qunarDockingPhyRoomType,omsSjRoomType);
            qunarDockingPhyRoomType.setHotelNo(omsSjRoomType.getInnId());
            qunarDockingPhyRoomType.setChannelHotelNo(omsSjRoomType.getChannelHotelNo());
            qunarDockingPhyRoomType.setOperatorGuid(omsSjRoomType.getOperatorGuid());
            qunarDockingPhyRoomType.setOperatorName(omsSjRoomType.getOperatorName());
            qunarDockingPhyRoomType.setChannelPhyRoomTypeName(omsSjRoomType.getChannelPhyRoomTypeName());
            qunarDockingPhyRoomType.setChannelPhyRoomTypeCode(omsSjRoomType.getChannelPhyRoomTypeCode());
            qunarDockingPhyRoomType.setPhyRoomTypeName(omsSjRoomType.getRoomTypeName());
            qunarDockingPhyRoomType.setPhyRoomTypeCode(omsSjRoomType.getRoomTypeId());
            qunarDockingPhyRoomTypeList.add(qunarDockingPhyRoomType);
         }
        return qunarDockingPhyRoomTypeList;
    }

    public List<QunarDockingRemovePhyRoomType> checkQunarDockingRemovePhyRoomType(OmsPram omsPram) throws Exception {
        String data = omsPram.getData();
        Assert.notNull(data);
        List<OmsXjRoomType> omsXjRoomTypeList = JacksonUtil.json2list(data, OmsXjRoomType.class);
        List<QunarDockingRemovePhyRoomType> list = new ArrayList<>();
        QunarDockingRemovePhyRoomType qunarDockingRemovePhyRoomType = null;
        for (OmsXjRoomType omsXjRoomType:omsXjRoomTypeList){
            qunarDockingRemovePhyRoomType = new QunarDockingRemovePhyRoomType();
            //BeanUtils.copyProperties(qunarDockingRemovePhyRoomType,omsXjRoomType);
            qunarDockingRemovePhyRoomType.setHotelNo(omsXjRoomType.getInnId());
            qunarDockingRemovePhyRoomType.setChannelHotelNo(omsXjRoomType.getChannelHotelNo());
            qunarDockingRemovePhyRoomType.setOperatorGuid(omsXjRoomType.getOperatorGuid());
            qunarDockingRemovePhyRoomType.setOperatorName(omsXjRoomType.getOperatorName());
            qunarDockingRemovePhyRoomType.setChannelPhyRoomTypeCode(omsXjRoomType.getChannelPhyRoomTypeCode());
            qunarDockingRemovePhyRoomType.setPhyRoomTypeCode(omsXjRoomType.getRoomTypeId());
            list.add(qunarDockingRemovePhyRoomType);

        }
        return list;
    }
}
