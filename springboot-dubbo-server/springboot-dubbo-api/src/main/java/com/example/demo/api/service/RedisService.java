package com.example.demo.api.service;

import org.apache.dubbo.rpc.protocol.rest.support.ContentType;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.Map;
import java.util.concurrent.TimeUnit;

//@Path("redisService") // #1
//@Consumes({ContentType.APPLICATION_JSON_UTF_8, ContentType.APPLICATION_JSON_UTF_8})
//@Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.APPLICATION_JSON_UTF_8})
public interface RedisService {

    void TestRedisLock(String lockString,String unlockString,Integer c);

    Long RedisMQpublish(String topic, Integer userId, String name);

    void submitJob(Map jobParams,String type, Long delay, TimeUnit timeUnit);

    Boolean addUrl2Redis(String type,String url);
}
