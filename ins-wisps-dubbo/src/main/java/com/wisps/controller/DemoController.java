package com.wisps.controller;

import com.wisps.dto.DemoDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/demo")
@RestController
public class DemoController {

    @GetMapping("/{id}")
    public DemoDto getById(@PathVariable("id") Long id){
        return new DemoDto(id, "name", "desc");
    }

}