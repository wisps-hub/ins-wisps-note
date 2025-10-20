package com.wisps.mq.consumer;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.StrUtil;
import com.wisps.idp.model.MsgBus;
import com.wisps.mq.biz.ReconsumeMsgBiz;
import com.wisps.mq.consts.Constants;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.kafka.support.Acknowledgment;

/**
 * kafka
 * idp.{服务名称}.{模块名称}.{事件名称}
 *      如 模块公共事件  idp.ins-wisps-user.user.event
 *      如 用户激活事件  idp.ins-wisps-user.user.active
 * {消费者服务名称}-{topicName}-版本号
 *      如 ins-wisps-auth-idp.ins-wisps-user.user.event-event1
 *      如 ins-wisps-chain-idp.ins-wisps-user.user.active-event1
 */

@Slf4j
public abstract class AbstractKafkaConsumer {

    @Resource
    protected ReconsumeMsgBiz docReconsumeMsgBiz;

    @PostConstruct
    public void registerConsumer() {
        docReconsumeMsgBiz.registerConsumer(this);
    }

    protected void processMessageWithCompensation(String message, Acknowledgment ack) {
        String action = "";
        String messageId = "";
        try {
            log.info("receive topic: {} msg: {}", getTopic(), message);
            MsgBus<String> msgBus = MsgBus.fromJson(message, String.class);
            messageId = msgBus.getMessageId();
            MDC.put(Constants.X_TRACE_ID, messageId);
            action = msgBus.getBizType();
            handle(msgBus);
            log.info("consume topic: {} event finish, msgId: {}", getTopic(), messageId);
        } catch (Exception e) {
            if (StrUtil.isBlank(action) || StrUtil.isBlank(messageId)) {
                log.info("consume topic: {} event message error, and action or msgId is blank ,can not reConsume,msg: {}", getTopic(), message);
            } else {
                save2db(message, e, messageId, action);
            }
        } finally {
            ack.acknowledge();
            MDC.clear();
        }
    }

    private void save2db(String message, Exception e, String messageId, String action) {
        if (needRetry(action)) {
            log.warn("consume topic: {} event message error,need to consume again ,msg: {}", getTopic(), message, e);
            docReconsumeMsgBiz.saveReconsumeMsgWithOutException(message,ExceptionUtil.stacktraceToString(e),messageId,action,getTopic());
        } else {
            log.warn("consume topic: {} event message error,not need retry ,msg: {}", getTopic(), message, e);
        }
    }

    protected abstract boolean needRetry(String action);

    public abstract void handle(MsgBus<String> msgBus);

    public abstract String getTopic();
}
