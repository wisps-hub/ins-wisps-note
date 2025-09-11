package com.wisps.zdemo;

import com.wisps.zdemo.config.ServerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/demo")
public class GwDemoService {

    @Autowired
    private ServerProperties serverProperties;

    @GetMapping("/demo")
    public String demo(){
        System.out.println(serverProperties.getServerUrl());
        System.out.println(serverProperties.getServerBackupUrl());
        return "ok";
    }

}