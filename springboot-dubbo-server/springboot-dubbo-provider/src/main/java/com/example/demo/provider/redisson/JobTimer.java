package com.example.demo.provider.redisson;

import org.redisson.api.RBlockingQueue;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
public class JobTimer {

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private ApplicationContext context;

    public static final String jobsTag = "customer_jobtimer_jobs";

    ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);

    @PostConstruct
    public void StartJobTimer(){
        RBlockingQueue blockingQueue = redissonClient.getBlockingQueue(jobsTag);
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        DelayJob job = (DelayJob) blockingQueue.take();
                        executorService.execute(new ExecutorTask(context, job));
                    } catch (Exception e) {
                        e.printStackTrace();
                        try {
                            TimeUnit.SECONDS.sleep(60);
                        } catch (Exception ex) {
                        }
                    }
                }
            }
        }.start();
    }
    class ExecutorTask implements Runnable {

        private ApplicationContext context;

        private DelayJob delayJob;

        public ExecutorTask(ApplicationContext context, DelayJob delayJob) {
            this.context = context;
            this.delayJob = delayJob;
        }

        @Override
        public void run() {
            ExecuteJob service = (ExecuteJob) context.getBean(delayJob.getNoneClass());
            service.execute(delayJob);
        }
    }
}