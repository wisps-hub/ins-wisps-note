package com.wisps.service;

import org.apache.dubbo.config.annotation.DubboService;

@DubboService(version = "1.0.0")
public class UserServiceImpl implements UserService{
    @Override
    public String demo() {
        return "dubbo-demo";
    }
}
