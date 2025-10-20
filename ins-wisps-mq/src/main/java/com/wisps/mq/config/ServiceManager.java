package com.wisps.mq.config;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ServiceManager {
    private volatile static boolean isRun = true;

    public static boolean isRun() {
        return isRun;
    }
    @PostConstruct
    public void init() {
        // 添加 JVM 关闭钩子
        Runtime.getRuntime().addShutdownHook(new Thread(this::stop));
    }

    @PreDestroy
    public void stop() {
        isRun = false;
        log.info("ServiceManager: Stopping all services...");
    }
}