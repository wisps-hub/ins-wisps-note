package com.wisps.config;

import com.wisps.cache.ICache;
import com.wisps.cache.RedisCacheImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfig {

    @Value("${spring.data.redis.host:localhost}")
    private String host;

    @Value("${spring.data.redis.port:6379}")
    private Integer port;

    @Bean("redisClient")
    public ICache redisClient(){
        return new RedisCacheImpl(host, port);
    }

}
