package com.example.demo.provider.redisson;

import org.redisson.api.ExpiredObjectListener;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.api.listener.MessageListener;
import org.redisson.api.map.event.EntryEvent;
import org.redisson.codec.SerializationCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class RedisExpired {

    @Autowired
    private RedissonClient redissonClient;

    private static String _key = "__key@0__:expired";


//    @PostConstruct
//    public void topic(){
//        RTopic topic = redissonClient.getTopic(_key,new SerializationCodec());
//        topic.addListener( new ExpiredObjectListener()
//        );
//    }
}
