package com.example.demo.provider.redisson;

/**
 * 延时job执行器接口
 */
public interface ExecuteJob {

    void execute(DelayJob job);
}
