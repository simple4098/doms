package com.tomasky.doms.controller;

import com.tomasky.doms.common.Constants;
import com.tomasky.doms.support.util.JsonModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    @ResponseBody
    public Object index(){
        return new JsonModel(Constants.STATUS200,Constants.HANDLE_SUCCESS);
    }
}
