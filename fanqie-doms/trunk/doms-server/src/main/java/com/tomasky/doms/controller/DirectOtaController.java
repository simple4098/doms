package com.tomasky.doms.controller;

import com.tomasky.doms.support.util.JsonModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * DESC : oms 渠道直连控制层
 * @author : 番茄木-ZLin
 * @data : 2016/6/6
 * @version: v1.0.0
 */
@Controller
@RequestMapping("/direct")
public class DirectOtaController {

    @RequestMapping("/sendCode")
    @ResponseBody
    public Object sendCode(){
        JsonModel jsonModel = new JsonModel();

        return jsonModel;
    }
}
