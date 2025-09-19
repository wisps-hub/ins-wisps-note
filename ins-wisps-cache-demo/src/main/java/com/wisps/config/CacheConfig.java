package com.wisps.config;

import com.wisps.cache.ICache;
import com.wisps.cache.RedisCacheImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class CacheConfig {

    @Value("${spring.data.redis.host:localhost}")
    private String host;

    @Value("${spring.data.redis.port:6379}")
    private Integer port;

    @Bean("redisClient")
    public ICache redisClient(){
        log.info("redisClient 初始化.....");
        return new RedisCacheImpl(host, port);
    }

}