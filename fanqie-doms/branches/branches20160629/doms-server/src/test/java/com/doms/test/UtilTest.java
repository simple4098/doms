package com.doms.test;

import com.fanqie.util.DateUtil;
import com.tomasky.doms.support.util.ResourceBundleUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2016/6/14
 * @version: v1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(value = "test")
@ContextConfiguration(locations = "classpath:/conf/spring-content-test.xml")
public class UtilTest {
    private static  final Logger log = LoggerFactory.getLogger(UtilTest.class);
    @Test
    public void  testFromDate(){
        String fromDate = DateUtil.fromDate(0);
        String date = DateUtil.fromDate(ResourceBundleUtil.getInt("qunar.day"));
        log.info(fromDate+"---"+date);
    }
}
