package com.wisps.service;

import com.wisps.cache.ICache;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RedisClientTest {

    @Autowired
    private ICache cache;

    @Test
    public void redisTest(){
        cache.set("test", "test_001");
        String val = cache.getString("test");
        System.out.println("test 值为：" + val);
    }
}