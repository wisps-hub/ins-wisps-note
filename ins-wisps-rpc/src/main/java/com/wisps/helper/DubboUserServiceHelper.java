package com.wisps.helper;

import com.wisps.service.UserService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;

@Component
public class DubboUserServiceHelper {

    @DubboReference(version = "1.0.0")
    private UserService userService;

    public String demo() {
        return userService.demo();
    }

}
