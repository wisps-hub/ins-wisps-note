package com.wisps.controller;

import com.wisps.dto.GoodDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/good")
public class GoodController {

    @GetMapping("/{id}")
    public GoodDto getById(@PathVariable("id") Long id) {
        GoodDto goodDto = new GoodDto();
        goodDto.setId(1L);
        goodDto.setName("一个产品");
        goodDto.setDesc("超详细产品介绍");
        goodDto.setPrice(new BigDecimal("100"));
        return goodDto;
    }
}
