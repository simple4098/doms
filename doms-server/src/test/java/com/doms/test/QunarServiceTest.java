
package com.doms.test;

import com.tomasky.doms.dao.IOtaInnDao;
import com.tomasky.doms.dto.OmsPram;
import com.tomasky.doms.dto.qunar.QunarMobile;
import com.tomasky.doms.dto.qunar.response.QunarHotelInfo;
import com.tomasky.doms.dto.qunar.response.QunarResult;
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



    @Test
    public void  testSendCode() throws Exception {

        QunarMobile qunarMobile = new QunarMobile("218.89.222.245","fanqie_test","番茄测试","15281017068");
        QunarResult qunarResult = qunarService.sendQunarPhoneCode("999", qunarMobile);
        log.info(JacksonUtil.obj2json(qunarResult));
    }

    @Test
    public void testCreateQunarPmsHotel(){

        String data = "{\"hotelNo\":\"124\",\"accountChannelHotelList\":[{\"userAccount\":\"15281017068\",\"userAccountStatus\":\"0\",\"channelHotelList\":[{\"dockingStatus\":\"2\",\"channelHotelNo\":\"1000156065\",\"channelHotelName\":\"番茄测试酒店\"}]}],\"channelCode\":\"QUNAR\"}";
        QunarHotelInfo qunarHotelInfo = JacksonUtil.json2obj(data, QunarHotelInfo.class);
        OmsPram omsPram = new OmsPram(124,"15281017068","107","805280","番茄测试客栈","fanqie_test","番茄测试");
        //otaInnDao.saveOtaInn(new OtaInn(omsPram.getInnId(),omsPram.getInnName(),null,omsPram.getOtaId(),true,omsPram.getInnCode(), OtaCode.QUNAR));
        try {
            QunarHotelInfo qunarPmsHotel = qunarService.createQunarPmsHotel(omsPram);
            log.info(JacksonUtil.obj2json(qunarPmsHotel));
        } catch (DmsException e) {
            e.printStackTrace();
        }
    }
}

