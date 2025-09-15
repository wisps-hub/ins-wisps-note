package com.wisps.controller;

import com.wisps.dto.GoodDto;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/good")
public class GoodController {

    @GetMapping("/{id}")
    public GoodDto getById(@PathVariable("id") Long id, @RequestHeader("x_token") String token) throws InterruptedException {
        System.out.println("调用 /good/id 接口 token: " + token);
        if (id == 2) throw new RuntimeException("id == 2");
        TimeUnit.SECONDS.sleep(5);
        GoodDto goodDto = new GoodDto();
        goodDto.setId(1L);
        goodDto.setName("一个产品");
        goodDto.setDesc("超详细产品介绍");
        goodDto.setPrice(new BigDecimal("100"));
        return goodDto;
    }
}
