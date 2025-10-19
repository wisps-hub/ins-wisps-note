package com.wisps.idpdemo.consumer;

import com.wisps.idp.model.MsgBus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DemoConsumer {

    @Value("${wisps.idp.topic.demo}")
    private String topic;

    @KafkaListener(topics = {"${wisps.idp.topic.demo}"},
            groupId = "${wisps.idp.group.demo}"
            , containerFactory = "normalListenerContainerFactory")
    public void handle(String message, Acknowledgment ack) {
        MsgBus<String> msgBus = MsgBus.fromJson(message, String.class);
        try {
            log.info("msgBus : {}", msgBus);
        } catch (Exception e) {
            log.error("handle err msgBus : {}", msgBus);
        } finally {
            ack.acknowledge();
        }
    }

}