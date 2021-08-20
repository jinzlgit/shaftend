package com.jyd.core.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Zhenlin Jin
 * @date 2021/8/19 22:26
 */
@Data
@Component
@ConfigurationProperties(prefix = "shaft.thread")
public class ThreadProperties {
    private int corePoolSize;
    private int maxPoolSize;
    private int keepAliveSeconds;
    private int queueCapacity;
}
