package com.wisps.mq.biz;

import com.wisps.mq.consumer.AbstractKafkaConsumer;

public interface ReconsumeMsgBiz {
    void saveReconsumeMsgWithOutException(String message, String stacktraceToString, String messageId, String action, String topic);

    void registerConsumer(AbstractKafkaConsumer consumer);

    void reConsume(String kafkaTopic, String msg);
}
