package com.wisps.service;

import com.wisps.vo.User;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Override
    public User getUser(Long id) {
        User user = new User();
        user.setId(1L);
        user.setName("lilas");
        user.setPhone("18899996666");
        user.setAge(18);
        return user;
    }
}
