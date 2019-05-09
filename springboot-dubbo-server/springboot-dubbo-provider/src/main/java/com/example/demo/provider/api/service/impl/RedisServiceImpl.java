package com.example.demo.provider.api.service.impl;

import com.example.demo.api.model.User;
import com.example.demo.api.service.RedisService;
import jodd.util.RandomString;
import org.apache.dubbo.config.annotation.Service;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.api.listener.MessageListener;
import org.redisson.codec.SerializationCodec;

import javax.annotation.Resource;

@Service
public class RedisServiceImpl implements RedisService {

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

    public Long RedisMQpublish(String topic,Integer userId,String name){
        RTopic topic1 = redissonClient.getTopic(topic, new SerializationCodec());
        Long time = topic1.publish(new User(userId,name));

        return time;
    }

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
}
