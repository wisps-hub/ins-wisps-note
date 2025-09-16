package com.wisps.controller;

import com.wisps.dto.DemoDto;
import com.wisps.service.DemoService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/demo")
@RestController
public class DemoController {

    @DubboReference
    private DemoService demoService;

    @GetMapping("/{id}")
    public DemoDto getById(@PathVariable("id") Long id){
        return demoService.getById(id);
    }
}