package com.jyd.core.parse;

import com.jyd.core.domain.BaseDTO;
import com.jyd.core.mq.MessageBlockingQueue;
import com.jyd.core.pool.PoolManager;
import com.jyd.core.pool.factory.ThreadFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Zhenlin Jin
 * @date 2021/8/12 22:21
 */
@Slf4j
@Component
public class ParsePolicy {
    public static final ExecutorService PARSE_THREAD = Executors.newSingleThreadExecutor();

    @Autowired
    private MessageBlockingQueue messageBlockingQueue;

    @Autowired
    private PoolManager poolManager;

    public void parse() {
        PARSE_THREAD.execute(() -> {
            while (true) {
                BaseDTO baseDTO = messageBlockingQueue.take();
                if (baseDTO != null) {
                    poolManager.execute(ThreadFactory.parseTask(baseDTO));
                }
            }
        });
    }

    @PreDestroy
    public void destroy() {
        PARSE_THREAD.shutdown();
    }
}
