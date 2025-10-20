package com.wisps.mq.controller;

import com.wisps.idp.model.MsgBus;
import com.wisps.idp.producer.IdpKafkaSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("/idp")
public class IdpController {

    @Autowired
    private IdpKafkaSender idpSender;

    @Value("${idp.topic.demo}")
    private String topic;

    @GetMapping("/send/{key}")
    public String send(@PathVariable("key") String key){
        idpSender.sendMessageToTopic(topic, key, MsgBus.buildEventMsgBus("demo_event_type", Map.of("k1", "v1", "k2", "v2")));
        return "ok";
    }

}