
package com.doms.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * DESC : 单元测试
 * @author : 番茄木-ZLin
 * @data : 2015/6/23
 * @version: v1.0.0
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(value = "dev")
@ContextConfiguration(locations = "classpath:conf/spring/spring-content.xml")
public class CompareTest {
    private static  final Logger log = LoggerFactory.getLogger(CompareTest.class);



    @Test
    public void  testBangCrm(){

    }

}

