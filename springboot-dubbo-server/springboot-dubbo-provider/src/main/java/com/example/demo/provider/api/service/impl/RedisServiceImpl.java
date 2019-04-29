package com.example.demo.provider.api.service.impl;

import com.example.demo.api.service.RedisService;
import jodd.util.RandomString;
import org.apache.dubbo.config.annotation.Service;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import javax.annotation.Resource;

@Service
public class RedisServiceImpl implements RedisService {

   private static String _lockString="lockString:";
   private static String _unlockString = "unlockString:";

    @Resource
    private RedissonClient redissonClient;

    @Override
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
}
