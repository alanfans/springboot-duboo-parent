package com.example.demo.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.example.demo.api.service.RedisService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/redis")
public class TestRedisController {

    @Reference
    RedisService redisService;


    @RequestMapping("/testRedis")
    @ResponseBody
    public ResponseEntity<Object> TestRedis(String lockString,String unlockString,Integer c){
        redisService.TestRedisLock(lockString,unlockString,c);
        ResponseEntity entity =  new ResponseEntity(HttpStatus.OK);
     return entity;
    }
}
