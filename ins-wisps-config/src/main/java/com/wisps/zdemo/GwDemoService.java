package com.wisps.zdemo;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/demo")
public class GwDemoService {

    @GetMapping("/demo")
    public String demo(){
        return "ok";
    }

}