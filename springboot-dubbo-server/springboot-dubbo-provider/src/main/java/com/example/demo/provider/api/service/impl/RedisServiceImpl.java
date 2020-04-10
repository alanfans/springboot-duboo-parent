package com.example.demo.provider.api.service.impl;

import com.example.demo.api.model.Bookurl;
import com.example.demo.api.model.Categories;
import com.example.demo.api.model.DelayJob;
import com.example.demo.api.model.User;
import com.example.demo.api.service.CategoriesService;
import com.example.demo.api.service.RedisService;
import com.example.demo.provider.dao.BookurlMapper;
import com.example.demo.provider.redisson.Job;
import com.example.demo.provider.redisson.JobTimer;
import io.swagger.util.Json;
import jodd.util.RandomString;
import org.apache.commons.codec.digest.Md5Crypt;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.common.json.JSON;
import org.apache.dubbo.config.annotation.Service;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.redisson.api.*;
import org.redisson.api.listener.MessageListener;
import org.redisson.codec.SerializationCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static java.util.stream.Collectors.toList;


@Service(async = true)
public class RedisServiceImpl implements RedisService {
    private static Logger log = LoggerFactory.getLogger(RedisServiceImpl.class);

   private static String _lockString="lockString:";
   private static String _unlockString = "unlockString:";

    @Resource
    private RedissonClient redissonClient;
    @Autowired
    private CategoriesService categoriesService;
    @Autowired
    private BookurlMapper bookurlMapper;

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
                //System.out.println(user.getId()+" UserName : "+user.getUser_name());
            }
        });
    }


    public Boolean addUrl2Redis(String type,String url){
        String key = type+":"+ Md5Crypt.md5Crypt(url.getBytes());
        RList rList = redissonClient.getList(key);
        return rList.add(url);
    }

    ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()*4);

    public String addUrl2Redis(Categories categories){
        List<Categories> categoriesList = categoriesService.selectByType(categories);
        categoriesList.forEach( categories1 -> {
            executorService.execute( () -> {
                for (Integer i = 1; i < Integer.MAX_VALUE; i++) {
                    String Url = CategoriesService.ALLITEBOOKS_URL + categories1.getName() +"/page/"+i;
                    try {
                        Document doc = getDoc(Url);
                        String no_book = doc.select("h1[class=page-title]").text();
                        if(StringUtils.equals("No Posts Found.",no_book)){
                            break;
                        }
                        //get Url
                        List<String> books_url = execute(doc);
                        books_url.forEach( url ->  addUrl2Redis(categories1.getName(),url) );
                        books_url.forEach( url -> {
                            Bookurl bookurl = new Bookurl();
                            bookurl.setUrl(url);
                            bookurl.setBak(categories1.getName());
                            bookurl.setCreateBy("1");
                            bookurl.setUpdateBy("1");
                            bookurl.setGmtCreate(new Date());
                            bookurl.setGmtModified(new Date());
                            bookurl.setIsDelete(0L);
                            bookurlMapper.insert(bookurl);
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                        break;


                    }
                }
            });
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        return "ok";
    }


    private List<String> execute(Document doc) {
        Elements elements = doc.select("h2[class=entry-title] a");
        List<String> bookUrls = elements.stream().map( bookurl -> bookurl.attr("href")).collect(toList());
        try {
            System.out.println(JSON.json(bookUrls));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bookUrls;
    }


    private Document getDoc(String Url) throws IOException {
        try{
            Document doc = Jsoup.parse(new URL(Url),90000);
            return doc;
        }catch (SocketTimeoutException se){
            getDoc(Url);
        }
        return null;
    }
}
