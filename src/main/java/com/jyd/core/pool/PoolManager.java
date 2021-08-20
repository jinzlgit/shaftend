package com.jyd.core.pool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

/**
 * @author Zhenlin Jin
 * @date 2021/8/20 10:31
 */
@Component
public class PoolManager {
    @Autowired
    private ThreadPoolTaskExecutor shaftThreadPool;

    public void execute(Runnable task) {
        shaftThreadPool.execute(task);
    }

    @PreDestroy
    public void destroy() {
        shaftThreadPool.shutdown();
    }
}
