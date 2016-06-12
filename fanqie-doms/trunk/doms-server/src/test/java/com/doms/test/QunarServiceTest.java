
package com.doms.test;

import com.tomasky.doms.dao.IOtaInnDao;
import com.tomasky.doms.dto.OmsPram;
import com.tomasky.doms.dto.oms.ChannelInfo;
import com.tomasky.doms.dto.oms.ChannelInfoData;
import com.tomasky.doms.dto.oms.OmsQunarHotel;
import com.tomasky.doms.dto.qunar.QunarMobile;
import com.tomasky.doms.dto.qunar.response.QunarHotelInfo;
import com.tomasky.doms.dto.qunar.response.QunarResult;
import com.tomasky.doms.dto.qunar.response.QunarRoomTypeData;
import com.tomasky.doms.enums.OtaCode;
import com.tomasky.doms.exception.DmsException;
import com.tomasky.doms.model.OtaInn;
import com.tomasky.doms.service.IQunarService;
import com.tomasky.doms.support.util.JacksonUtil;
import com.tomato.mq.client.support.SystemConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


/**
 * DESC : 单元测试
 * @author : 番茄木-ZLin
 * @data : 2015/6/23
 * @version: v1.0.0
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(value = "test")
@ContextConfiguration(locations = "classpath:/conf/spring-content-test.xml")
public class QunarServiceTest {
    private static  final Logger log = LoggerFactory.getLogger(QunarServiceTest.class);

    @Resource
    private IQunarService qunarService;


    /**
     * 发送短信
     * @throws Exception
     */
    @Test
    public void  testSendCode() throws Exception {

        QunarMobile qunarMobile = new QunarMobile("218.89.222.245","fanqie_test","番茄测试","15281017068");
        QunarResult qunarResult = qunarService.sendQunarPhoneCode("999", qunarMobile);
        log.info(JacksonUtil.obj2json(qunarResult));
    }

    /**
     *开通直连并查询酒店列表
     */
    @Test
    public void testCreateQunarPmsHotel(){

        String data = "{\"hotelNo\":\"124\",\"accountChannelHotelList\":[{\"userAccount\":\"15281017068\",\"userAccountStatus\":\"0\",\"channelHotelList\":[{\"dockingStatus\":\"2\",\"channelHotelNo\":\"1000156065\",\"channelHotelName\":\"番茄测试酒店\"}]}],\"channelCode\":\"QUNAR\"}";
        String s = "{\"hotelNo\":\"124\",\"channelCode\":\"QUNAR\",\"accountChannelHotelList\":[{\"userAccount\":\"15281017068\",\"userAccountStatus\":\"0\",\"channelHotelList\":[{\"channelHotelNo\":\"1000156065\",\"channelHotelName\":\"番茄测试酒店\",\"dockingStatus\":\"2\",\"otherRelationHotelNo\":null,\"otherRelationHotelName\":null}]}]}";
        //QunarHotelInfo qunarHotelInfo = JacksonUtil.json2obj(data, QunarHotelInfo.class);
        OmsPram omsPram = new OmsPram(124,"15281017068","107","999329","番茄测试客栈","fanqie_test","番茄测试");
        //otaInnDao.saveOtaInn(new OtaInn(omsPram.getInnId(),omsPram.getInnName(),null,omsPram.getOtaId(),true,omsPram.getInnCode(), OtaCode.QUNAR));
        try {
            QunarHotelInfo qunarPmsHotel = qunarService.createQunarPmsHotel(omsPram);
            log.info(JacksonUtil.obj2json(qunarPmsHotel));
        } catch (DmsException e) {
            e.printStackTrace();
        }
    }

    /**
     *关闭渠道直连
     */
    @Test
    public void testRemoveAccount(){
        OmsPram omsPram = new OmsPram(124,"15281017068","107","805280","番茄测试客栈","fanqie_test","番茄测试");
        try {
            QunarResult qunarResult = qunarService.removeDockingAccount(omsPram);
            log.info("去呼呼返回结果:"+JacksonUtil.obj2json(qunarResult));
        } catch (DmsException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void matchHotel(){
        ChannelInfoData channelInfoData = new ChannelInfoData(124,"番茄测试客栈","fanqie_test","番茄测试");
        List<ChannelInfo> channelInfo = new ArrayList<>();
        ChannelInfo channelInfo1 = new ChannelInfo("15281017068");
        //ChannelInfo channelInfo2 = new ChannelInfo("15281017062");
        OmsQunarHotel omsQunarHotel = new OmsQunarHotel("番茄测试酒店","1000156065");
        //OmsQunarHotel omsQunarHotel1 = new OmsQunarHotel("去哪儿酒店名称1","qunar-code-hotel1");
        List<OmsQunarHotel> omsQunarHotelList = new ArrayList<>();
        omsQunarHotelList.add(omsQunarHotel);
        //omsQunarHotelList.add(omsQunarHotel1);
        channelInfo1.setChannelHotelList(omsQunarHotelList);
        //channelInfo2.setChannelHotelList(omsQunarHotelList);
        channelInfo.add(channelInfo1);
        //channelInfo.add(channelInfo2);
        channelInfoData.setChannelInfo(channelInfo);
        String obj2json = JacksonUtil.obj2json(channelInfoData);

        OmsPram omsPram = new OmsPram(obj2json);
        try {
            QunarResult qunarResult = qunarService.matchQunarHotel(omsPram);
            log.info("去呼呼匹配结果:"+JacksonUtil.obj2json(qunarResult));
        } catch (DmsException e) {
            e.printStackTrace();
        }
        //log.info("==============:"+obj2json);
    }
    //房型列表
    @Test
    public void testSearchQunarRoomList(){
        OmsPram omsPram = new OmsPram();
        omsPram.setInnId(124);
        try {
            QunarRoomTypeData qunarRoomTypeData = qunarService.searchQunarRoomList(omsPram);
            log.info("返回结果:"+JacksonUtil.obj2json(qunarRoomTypeData));

        } catch (DmsException e) {
            e.printStackTrace();
        }
    }
    //解绑酒店
    @Test
    public void testRemoveDockingHotel(){
        OmsPram omsPram = new OmsPram("json");
        try {
            QunarResult qunarResult = qunarService.removeDockingHotel(omsPram);
            log.info("返回结果:"+JacksonUtil.obj2json(qunarResult));
        } catch (DmsException e) {
            e.printStackTrace();
        }
    }

    //房型匹配
    @Test
    public void  testMatchRoomType(){
        OmsPram omsPram = new OmsPram("json");
        try {
            QunarResult qunarResult = qunarService.matchRoomType(omsPram);
            log.info("testMatchRoomType返回结果:"+JacksonUtil.obj2json(qunarResult));
        } catch (DmsException e) {
            e.printStackTrace();
        }
    }

    //房型解绑匹配
    @Test
    public void  testRemoveRoomType(){
        OmsPram omsPram = new OmsPram("json");
        try {
            QunarResult qunarResult = qunarService.removeRoomType(omsPram);
            log.info("testRemoveRoomType返回结果:"+JacksonUtil.obj2json(qunarResult));
        } catch (DmsException e) {
            e.printStackTrace();
        }
    }

}

