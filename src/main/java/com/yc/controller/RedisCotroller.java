package com.yc.controller;

import com.google.gson.Gson;
import com.yc.model.RedisEmail;
import com.yc.service.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
public class RedisCotroller {

    @Autowired
    private RedisTemplate redisTemplate;
    // 查找账户
    @RequestMapping( value = "/find", method = RequestMethod.GET)
    public List<RedisEmail> find(String key) {
        Set<String> strs = redisTemplate.opsForZSet().range(key, 0, -1);
        List<RedisEmail> list = new ArrayList<>();
        for (String str : strs){
            RedisEmail email = new RedisEmail();
            Gson go = new Gson();
            email = go.fromJson(str,RedisEmail.class);
            list.add(email);
        }
        return list;
    }

}
