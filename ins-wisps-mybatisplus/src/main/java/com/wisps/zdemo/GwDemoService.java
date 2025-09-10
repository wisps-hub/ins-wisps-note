package com.wisps.zdemo;

import com.wisps.zdemo.entity.UserEntity;
import com.wisps.zdemo.mapping.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/demo")
public class GwDemoService {
    @Autowired
    private UserDao userDao;

    @GetMapping("/{id}")
    public UserEntity getById(@PathVariable("id") Long id){
        return userDao.getById(id);
    }

    @GetMapping("/list")
    public List<UserEntity> getById(@RequestParam("ids") List<Long> ids){
        return userDao.getList(ids);
    }
}
