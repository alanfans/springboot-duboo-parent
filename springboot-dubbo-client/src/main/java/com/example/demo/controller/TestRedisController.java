package com.example.demo.controller;

import com.example.demo.api.service.RedisService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
     return ResponseEntity.ok(HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping("/testpublish")
    public ResponseEntity<Object> RedisMQpublish(@RequestParam(value = "主题",required = true) String topic, Integer userId, String name){

        return ResponseEntity.ok(redisService.RedisMQpublish(topic,userId,name));
    }
}
