package com.tomasky.doms.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2016/6/16
 * @version: v1.0.0
 */
@Controller("/")
public class IndexController {
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
    @RequestMapping("")
    public String index(){
        logger.info("=============");
        logger.debug("=------------------");
        logger.error("=------------------");
        return "welcome";
    }
}
