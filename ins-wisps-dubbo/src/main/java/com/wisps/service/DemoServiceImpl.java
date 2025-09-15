package com.wisps.service;

import com.wisps.dto.DemoDto;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService
public class DemoServiceImpl implements DemoService{
    @Override
    public DemoDto getById(Long id) {
        return DemoDto.builder().id(id).name("demo").desc("desc").build();
    }
}
