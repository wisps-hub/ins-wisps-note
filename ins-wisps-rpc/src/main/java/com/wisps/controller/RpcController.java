package com.wisps.controller;

import com.wisps.dto.GoodDto;
import com.wisps.helper.UserServiceHelper;
import com.wisps.helper.GoodServiceHelper;
import com.wisps.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rpc")
public class RpcController {

    @Autowired
    private UserServiceHelper userServiceHelper;
    @Autowired
    private GoodServiceHelper goodServiceHelper;

    @GetMapping("/{id}")
    public User demo(@PathVariable("id") Long id) {
        return userServiceHelper.getUser(id);
    }

    @GetMapping("/good/{id}")
    public GoodDto good(@PathVariable("id") Long id) {
        return goodServiceHelper.getById(id);
    }
}