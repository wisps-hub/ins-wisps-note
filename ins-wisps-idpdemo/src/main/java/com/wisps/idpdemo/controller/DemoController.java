package com.wisps.idpdemo.controller;

import com.wisps.idp.config.IdpProperties;
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
public class DemoController {

    @Value("${wisps.idp.topic.demo}")
    private String topic;
    @Autowired
    private IdpProperties idpProperties;
    @Autowired
    private IdpKafkaSender idpKafkaSender;

    @GetMapping("/demo/{id}")
    public String send(@PathVariable("id") String id) {
        System.out.println("属性: " + idpProperties.isEnabled());
        idpKafkaSender.sendMessageToTopic(topic, id,
                MsgBus.buildEventMsgBus("demo", Map.of("k1", "v1", "k2", "v2"))
        );
        return "ok";
    }

}
