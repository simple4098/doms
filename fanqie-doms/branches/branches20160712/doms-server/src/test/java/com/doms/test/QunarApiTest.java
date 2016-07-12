
package com.doms.test;

import com.alibaba.dubbo.common.json.JSON;
import com.tomasky.doms.dto.qunar.QunarMobile;
import com.tomasky.doms.support.util.SecurityUtil;
import com.tomasky.doms.support.util.HttpClientUtil;
import com.tomasky.doms.support.util.JacksonUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
public class QunarApiTest {
    private static  final Logger log = LoggerFactory.getLogger(QunarApiTest.class);



    @Test
    public void  testSendCode() throws Exception {

        QunarMobile qunarMobile = new QunarMobile("218.89.222.245","fanqie_test","番茄测试","15281017068");

        String obj2json = JacksonUtil.obj2json(qunarMobile);
        Map<String,String> param = JacksonUtil.json2map(obj2json);
        String hmac = SecurityUtil.buildMyHMAC(param, "srT4Vrx0LWRT8LjbE6ajanIjigGRx8iR");
        qunarMobile.setHmac(hmac);
        String json = JSON.json(qunarMobile);
        String httpPost = HttpClientUtil.httpKvPost("http://link.beta.quhuhu.com/api/tomasky/docking/sendVerificationCode.do", qunarMobile);


    }

}

