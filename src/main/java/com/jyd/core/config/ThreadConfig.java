package com.jyd.core.config;

import com.jyd.core.config.properties.ThreadProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author Zhenlin Jin
 * @date 2021/8/19 21:55
 */
@Configuration
public class ThreadConfig {

    @Autowired
    private ThreadProperties properties;

    @Bean(name = "shaftThreadPool")
    public ThreadPoolTaskExecutor shaftThreadPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(properties.getCorePoolSize());
        executor.setMaxPoolSize(properties.getMaxPoolSize());
        executor.setKeepAliveSeconds(properties.getKeepAliveSeconds());
        executor.setQueueCapacity(properties.getQueueCapacity());
        executor.setThreadNamePrefix("shaft-pool-");
        return executor;
    }

}
