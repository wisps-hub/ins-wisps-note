package com.wisps.zdemo.mapping.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wisps.zdemo.entity.UserEntity;
import com.wisps.zdemo.mapping.mapper.UserMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDaoImpl extends ServiceImpl<UserMapper, UserEntity> implements UserDao {

    @Override
    public UserEntity selectById(Long id) {
        return getById(id);
    }

    @Override
    public List<UserEntity> getList(List<Long> ids) {
        return baseMapper.getList(ids);
    }
}
