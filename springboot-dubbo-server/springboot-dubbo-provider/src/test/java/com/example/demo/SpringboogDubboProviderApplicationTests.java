package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringboogDubboProviderApplicationTests {

	@Test
	public void contextLoads() {
	}

	public static void main(String[] args) {
		com.alibaba.dubbo.container.Main.main(args);
	}

	@Resource
	private RedissonClient redissonClient;

	@Test
	public void RedissionTest(){
		String user_id="1";
		String key=user_id+"_key";

		RLock lock = redissonClient.getLock(key);
		lock.lock();

		RBucket<Object> bucket = redissonClient.getBucket("a");
		bucket.set("bb");
		lock.unlock();
	}

}
