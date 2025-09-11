package com.wisps.controller;

import com.wisps.helper.DubboUserServiceHelper;
import com.wisps.service.UserService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rpc")
public class RpcController {

    @Autowired
    private DubboUserServiceHelper dubboUserServiceHelper;

    @GetMapping("/demo")
    public String demo() {
        return dubboUserServiceHelper.demo();
    }


}
