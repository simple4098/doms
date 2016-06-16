package com.tomasky.doms.controller;

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

    @RequestMapping("index")
    public String index(){
        return "welcome";
    }
}
