package com.wisps.controller;

import com.wisps.biz.DemoBiz;
import com.wisps.dto.DemoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/demo")
@RestController
public class DemoController {

    @Autowired
    private DemoBiz demoBiz;

    @GetMapping("/get/{id}")
    public DemoDto getById(@PathVariable("id") Long id){
        return new DemoDto(id, "name", "desc");
    }

    @GetMapping("/create/{id}")
    public DemoDto create(@PathVariable("id") Long id){
        return demoBiz.create(id);
    }

}