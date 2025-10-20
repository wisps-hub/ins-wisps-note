package com.wisps.mq.biz;

import com.wisps.idp.config.IdpProperties;
import com.wisps.idp.model.MsgBus;
import com.wisps.mq.config.ServiceManager;
import com.wisps.mq.consts.RedisCacheKey;
import com.wisps.mq.consumer.AbstractKafkaConsumer;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class ReconsumeMsgBizImpl implements ReconsumeMsgBiz {

    @Resource
    RedissonClient redissonClient;
    @Autowired
    private IdpProperties properties;

    private final Map<String, AbstractKafkaConsumer> consumerMap = new ConcurrentHashMap<>();

    @Override
    public void saveReconsumeMsgWithOutException(String message, String stacktraceToString, String messageId, String action, String topic) {
        log.info("kafka消费失败 save2db, message: {}", message);
    }

    @Override
    public void registerConsumer(AbstractKafkaConsumer consumer) {
        String topic = consumer.getTopic();
        synchronized (topic) {
            if (consumerMap.containsKey(topic)) {
                log.warn("Consumer for topic {} is already registered.", topic);
            } else {
                consumerMap.put(topic, consumer);
                log.info("Consumer for topic {} registered successfully.", topic);
                startReConsumeThread(topic, consumer);
            }
        }
    }

    @Override
    public void reConsume(String kafkaTopic, String msg) {
        AbstractKafkaConsumer consumer = consumerMap.get(kafkaTopic);
        if (consumer == null) {
            log.warn("consumer for topic {} not found", kafkaTopic);
            return;
        }
        consumer.handle(MsgBus.fromJson(msg, String.class));
    }

    private void startReConsumeThread(String topic, AbstractKafkaConsumer consumer) {
        new Thread(() -> {
            log.info("start thread reConsume topic: {} msg", topic);
            while (ServiceManager.isRun()) {
                RLock lock = null;
                String lockKey = RedisCacheKey.getLockKey("reConsumeKafkaMsg:" + topic);
                try {
                    lock = redissonClient.getLock(lockKey);
                    if (lock.tryLock(0, 30, TimeUnit.SECONDS)) {
                        log.info("Acquired lock: {} for topic: {}", lockKey, topic);
                        //todo wisps 重复消费

                    } else {
                        log.info("Failed to acquire lock: {} for topic: {}, waiting for next cycle", lockKey, topic);
                    }
                } catch (Exception e) {
                    log.warn("reConsumeMsgFromDb lock:{} fail", lockKey, e);
                } finally {
                    if (lock != null) {
                        try {
                            if (lock.isHeldByCurrentThread()) {
                                lock.unlock();
                            }
                        } catch (Exception e) {
                            log.info("unlock fail lock:{}", lockKey, e);
                        }
                    }
                }
                // 在每次循环结束时休眠
                try {
                    Thread.sleep(TimeUnit.SECONDS.toMillis(properties.getRetryIntervalSecond()));
                } catch (InterruptedException e) {
                    log.info("thread Interrupted");
                    Thread.currentThread().interrupt();
                }
            }
        }, topic + "-ReConsume-Thread").start();
    }
}