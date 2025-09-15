package com.wisps.third;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "third-client", url = "http://localhost:8888")
public interface ThirdService {

    @PostMapping("/third/app/path/{id}")
    void getThirdDate(@RequestHeader("token") String token,
                      @RequestParam("scene") String scene,
                      @PathVariable("id") String id,
                      @RequestBody ThirdReq req);

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class ThirdReq{
        private String param1;
        private String param2;
    }
}
