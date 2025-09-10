package com.wisps.zdemo.mapping.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wisps.zdemo.entity.UserEntity;

import java.util.List;

public interface UserDao extends IService<UserEntity> {
    UserEntity selectById(Long id);
    List<UserEntity> getList(List<Long> ids);
}
