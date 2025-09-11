package com.wisps.helper;

import com.wisps.dto.GoodDto;
import com.wisps.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GoodServiceHelper {

    @Autowired
    private GoodService goodService;

    public GoodDto getById(Long id) {
        return goodService.getById(id);
    }
}
