package com.wisps.zdemo.config;

import com.wisps.zdemo.dto.UserDto;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Value("${wisps.name}")
    private String name;

    private UserDto userDto;

    @PostConstruct
    public void User(){
        userDto = new UserDto();
        userDto.setName(name);
        System.out.println("UserDto 构建成功: " + userDto);
    }

    @Bean
    public UserDto userDto(){
        userDto = new UserDto();
        userDto.setName(name);
        System.out.println("UserDto bean 构建成功: " + userDto);
        return userDto;
    }

}