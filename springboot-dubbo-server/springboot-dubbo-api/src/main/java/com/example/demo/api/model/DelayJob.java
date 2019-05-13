package com.example.demo.api.model;

import java.io.Serializable;
import java.util.Map;

/**
 * 延时job
 */

public class DelayJob implements Serializable {

    //job执行参数
    private Map jobParams;

    //具体执行实例实现
    private Class noneClass;

    public DelayJob(Map jobParams, Class noneClass) {
        this.jobParams = jobParams;
        this.noneClass = noneClass;
    }



    public Map getJobParams() {
        return jobParams;
    }

    public void setJobParams(Map jobParams) {
        this.jobParams = jobParams;
    }

    public Class getNoneClass() {
        return noneClass;
    }

    public void setNoneClass(Class noneClass) {
        this.noneClass = noneClass;
    }

    public DelayJob() {
    }
}
