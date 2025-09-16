package com.wisps.controller;

import com.wisps.dto.DemoDto;
import com.wisps.service.DemoService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @DubboReference
    private DemoService demoService;

    @GetMapping("/{id}")
    public DemoDto getById(@PathVariable("id") Long id){
        System.out.println("请求接口 /demo/{id}");
        return demoService.getById(id);
    }

}