
package com.doms.test;

import com.alibaba.dubbo.common.json.JSON;
import com.tomasky.doms.dto.OmsPram;
import com.tomasky.doms.dto.qunar.QunarMobile;
import com.tomasky.doms.dto.qunar.response.QunarHotelInfo;
import com.tomasky.doms.dto.qunar.response.QunarResult;
import com.tomasky.doms.exception.DmsException;
import com.tomasky.doms.service.IQunarService;
import com.tomasky.doms.support.util.HttpClientUtil;
import com.tomasky.doms.support.util.JacksonUtil;
import com.tomasky.doms.support.util.SecurityUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Map;


/**
 * DESC : 单元测试
 * @author : 番茄木-ZLin
 * @data : 2015/6/23
 * @version: v1.0.0
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(value = "dev")
@ContextConfiguration(locations = "classpath:conf/spring/spring-content.xml")
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
        OmsPram omsPram = new OmsPram(124,"15281017068","999","123456","fanqie_test","番茄测试");
        try {
            QunarHotelInfo qunarPmsHotel = qunarService.createQunarPmsHotel(omsPram);
        } catch (DmsException e) {
            e.printStackTrace();
        }
    }
}

