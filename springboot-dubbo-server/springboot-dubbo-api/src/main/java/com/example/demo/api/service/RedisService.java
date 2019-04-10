package com.example.demo.api.service;

public interface RedisService {

    void TestRedisLock(String lockString,String unlockString,Integer c);
}
