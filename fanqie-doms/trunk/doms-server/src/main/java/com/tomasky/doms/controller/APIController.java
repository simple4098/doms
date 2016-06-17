package com.tomasky.doms.controller;

import com.tomasky.doms.support.util.JsonModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * DESC : 对接OTA ... controller
 * @author : 番茄木-ZLin
 * @data : 2015/6/23
 * @version: v1.0.0
 */
@Controller
@RequestMapping("/api")
public class APIController  {

    private static  final Logger log = LoggerFactory.getLogger(APIController.class);

    @RequestMapping("/hotel/timer")
    @ResponseBody
    public Object hotelTimer() throws IOException {
        log.info("=======start=======");
        JsonModel jsonModel = new JsonModel("200","sdsd");
        /*List<OtaInfoDto> infoDtoList = otaInfoService.findOtaInfoList();
        jsonModel.setMessage(JSON.json(infoDtoList));*/
        log.info("============end============");
        return  jsonModel;
    }





}
