package com.wisps.zdemo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.wisps.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("org_user")
public class UserEntity extends BaseEntity {
    private String oid;
    private String uid;
    private String realName;
    private String region;
    private String mobile;
    private String avatarUrl;
    private String email;
    private String password;
}
