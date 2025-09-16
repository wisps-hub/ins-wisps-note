package com.wisps.service.impl;

import com.wisps.service.ConsumerDemoService;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService
public class ConsumerDemoServiceImpl implements ConsumerDemoService {

    @Override
    public String demo(){
        return "demo is ok";
    }


}
