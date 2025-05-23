package com.siddhu.Journal.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    void testRedis() {
//        redisTemplate.opsForValue().set("name", "siddhu");

        Object o = redisTemplate.opsForValue().get("name");
        System.out.println(o.toString());
    }
}
