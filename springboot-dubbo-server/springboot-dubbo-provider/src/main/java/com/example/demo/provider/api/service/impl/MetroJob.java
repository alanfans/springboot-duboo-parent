package com.example.demo.provider.api.service.impl;

import com.example.demo.api.model.DelayJob;
import com.example.demo.api.service.ExecuteJob;
import org.apache.dubbo.config.annotation.Service;

@Service
public class MetroJob implements ExecuteJob {
    @Override
    public void execute(DelayJob job) {
        System.out.println("MetroJob:"+job.getJobParams());
    }
}
