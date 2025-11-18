package com.wisps.controller;

import com.alibaba.cloud.ai.dashscope.image.DashScopeImageOptions;
import jakarta.annotation.Resource;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/t2i")
public class T2IController {

    @Resource
    private ImageModel imageModel;

    @GetMapping("/img")
    public String img(@RequestParam("msg")String msg){
        return imageModel.call(new ImagePrompt(msg)).getResult().getOutput().getUrl();
    }

}