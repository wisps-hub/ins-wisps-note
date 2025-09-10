package com.wisps.zdemo.mapping.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wisps.zdemo.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<UserEntity> {
    List<UserEntity> getList(List<Long> ids);
}
