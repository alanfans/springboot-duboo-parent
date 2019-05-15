package com.example.demo.controller;

import com.example.demo.api.service.ExecuteJob;
import com.example.demo.api.model.User;
import com.example.demo.api.service.RedisService;
import com.google.common.collect.Maps;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/redis")
public class TestRedisController {

    @Reference
    RedisService redisService;


    @PostMapping("/testRedis")
    @ResponseBody
    public ResponseEntity<Object> TestRedis(String lockString,String unlockString,Integer c){
        redisService.TestRedisLock(lockString,unlockString,c);
     return ResponseEntity.ok(HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping("/testpublish")
    public ResponseEntity<Object> RedisMQpublish(@RequestParam(value = "主题",required = true) String topic, Integer userId, String name){

        return ResponseEntity.ok(redisService.RedisMQpublish(topic,userId,name));
    }

    @ResponseBody
    @PostMapping("/addUrl2Redis")
    public ResponseEntity<Object> AddUrl2Redis(String type,String url){

        return ResponseEntity.ok(redisService.addUrl2Redis( type, url));
    }

    @ResponseBody
    @PostMapping("/testQueue")
    public ResponseEntity<Object> RedisRestQueue(@RequestParam(value = "主题",required = true) String topic, Integer userId, String name,Long delay,String type){
        Map jobParams = Maps.newHashMap();
        jobParams.put("user",new User(userId,name));
        redisService.submitJob( jobParams,  type, delay, TimeUnit.SECONDS);
        return ResponseEntity.ok("ok");
    }
}
