package com.wisps.mq.consumer;

import com.wisps.idp.model.MsgBus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DemoConsumer extends AbstractKafkaConsumer{

    @Value("${idp.topic.demo}")
    private String topic;

    @KafkaListener(topics = {"${idp.topic.demo}"}, groupId = "${idp.group.demo}")
    public void handle(String message, Acknowledgment ack) {
        processMessageWithCompensation(message, ack);
    }

    @Override
    public void handle(MsgBus<String> msgBus) {
        //todo kafka
        log.info("消费: {}", msgBus);
    }

    @Override
    protected boolean needRetry(String action) {
        return true;
    }

    @Override
    public String getTopic() {
        return topic;
    }
}