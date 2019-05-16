package com.example.demo.provider.api.service.impl;

import com.example.demo.api.model.DelayJob;
import com.example.demo.api.model.User;
import com.example.demo.api.service.RedisService;
import com.example.demo.provider.redisson.Job;
import com.example.demo.provider.redisson.JobTimer;
import io.swagger.util.Json;
import jodd.util.RandomString;
import org.apache.commons.codec.digest.Md5Crypt;
import org.apache.dubbo.config.annotation.Service;
import org.redisson.api.*;
import org.redisson.api.listener.MessageListener;
import org.redisson.codec.SerializationCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@Service(async = true)
public class RedisServiceImpl implements RedisService {
    private static Logger log = LoggerFactory.getLogger(RedisServiceImpl.class);

   private static String _lockString="lockString:";
   private static String _unlockString = "unlockString:";

    @Resource
    private RedissonClient redissonClient;

    public RedisServiceImpl() {

//        RTopic topic = redissonClient.getTopic("dw",new SerializationCodec());
//        topic.addListener(User.class, new MessageListener<User>() {
//            @Override
//            public void onMessage(CharSequence charSequence, User user) {
//                System.out.println("onMessage:"+charSequence+"; Thread: "+Thread.currentThread().toString());
//                System.out.println(user.getId()+" UserName : "+user.getUser_name());
//            }
//        });
//        RTopic topic1 = redissonClient.getTopic("mq", new SerializationCodec());
//        topic1.publish(new User(120000,"white"));
//        topic1.publish(new User(100000,"black"));
    }

    public void TestRedisLock(String lockString, String unlockString,Integer c) {
        String rS = RandomString.get().randomAlphaNumeric(20);
        String slock = _lockString + lockString + rS;
        String unslock = _unlockString + unlockString + rS;
        RLock lock = redissonClient.getLock(unslock);
        if(c > 0){
            if(lock.tryLock()){
                try{
                    RBucket<Object> bucket = redissonClient.getBucket(slock);
                    bucket.set("bb");
                }finally {
                    lock.unlock();
                }
            }
        }else{
            RBucket<Object> bucket = redissonClient.getBucket(unslock);
            bucket.set("cc");
        }
    }

    public Long RedisMQpublish(String topic,Integer userId,String name) {
        RTopic topic1 = redissonClient.getTopic(topic, new SerializationCodec());
        Long time = topic1.publish(new User(userId,name));
        log.info("发送topic:{},user:{}",topic, Json.pretty(new User(userId,name)));
        return time;
    }

    public void submitJob(Map jobParams,String type, Long delay, TimeUnit timeUnit){
        RBlockingQueue blockingQueue = redissonClient.getBlockingQueue(JobTimer.jobsTag);
        RDelayedQueue delayedQueue = redissonClient.getDelayedQueue(blockingQueue);
        delayedQueue.offer(new DelayJob(jobParams, Job.getValueByCode(type)),delay,timeUnit);
    }

    @PostConstruct
    public void RedisMQsubscribe(){
        RTopic topic = redissonClient.getTopic("a1",new SerializationCodec());

        topic.addListener(User.class, new MessageListener<User>() {
            @Override
            public void onMessage(CharSequence charSequence, User user) {
                System.out.println("onMessage:"+charSequence+"; Thread: "+Thread.currentThread().toString());
                System.out.println(user.getId()+" UserName : "+user.getUser_name());
            }
        });
    }


    public Boolean addUrl2Redis(String type,String url){
        String key = type+":"+ Md5Crypt.md5Crypt(url.getBytes());
        RList rList = redissonClient.getList(key);
        return rList.add(url);
    }
}
