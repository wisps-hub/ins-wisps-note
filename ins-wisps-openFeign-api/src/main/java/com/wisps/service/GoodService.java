package com.wisps.service;

import com.wisps.dto.GoodDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "ins-wisps-openfeign")
public interface GoodService {

    @GetMapping("/good/{id}")
    GoodDto getById(@PathVariable("id") Long id);

}
