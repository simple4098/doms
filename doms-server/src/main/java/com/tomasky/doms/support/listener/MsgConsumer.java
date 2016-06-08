package com.tomasky.doms.support.listener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tomato.mq.client.event.listener.MsgEventListener;
import com.tomato.mq.client.event.model.MsgEvent;
import com.tomato.mq.client.event.publisher.MsgEventPublisher;
import com.tomato.mq.support.message.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * DESC : 事件处理，客栈上、下架
 * @author : 番茄木-ZLin
 * @data : 2016/3/7
 * @version: v1.0.0
 */
public class MsgConsumer implements MsgEventListener {

    private static  final Logger log = LoggerFactory.getLogger(MsgConsumer.class);
    private String systemName;


    public MsgConsumer(String systemName) {
        this.systemName = systemName;
        log.info("=============start listener===================");
        MsgEventPublisher.getInstance().addListener(this, MessageType.SYS_EVENT, systemName);
    }

    @Override
    public void onEvent(MsgEvent msgEvent) {
        JSONObject jsonObject = JSON.parseObject(msgEvent.getSource().toString());

    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

}