package com.wisps.fallback;

import com.wisps.dto.GoodDto;
import com.wisps.service.GoodService;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class GoodFallBackFactory implements FallbackFactory<GoodService> {
    @Override
    public GoodService create(Throwable cause) {
        return new GoodService(){
            @Override
            public GoodDto getById(Long id) {
                System.out.println("兜底逻辑");
                return new GoodDto();
            }
        };
    }
}
