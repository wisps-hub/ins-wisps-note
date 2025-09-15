package com.wisps.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpFeignConfig {

    @Bean
    Logger.Level feignLoggerLevel(){
        //feign日志组件 todo 未生效
        return Logger.Level.FULL;
    }

}
