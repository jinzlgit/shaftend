package com.jyd.core.parse;

import com.jyd.core.constant.CodeEnum;
import com.jyd.core.domain.BaseDTO;
import com.jyd.core.mq.MessageBlockingQueue;
import com.jyd.core.util.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Optional;
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
    private ThreadPoolTaskExecutor shaftThreadPool;

    @Autowired
    private MessageBlockingQueue messageBlockingQueue;

    public void parse() {
        PARSE_THREAD.execute(() -> {
            while (true) {
                BaseDTO baseDTO = messageBlockingQueue.take();
                shaftThreadPool.execute(() -> {
                    String beanName = CodeEnum.getBeanNameByCode(baseDTO.getCode());
                    IParsePolicy policy = (IParsePolicy) SpringUtil.getBeanByName(beanName);
                    try {
                        Optional.ofNullable(policy)
                                .orElseThrow(() -> new ClassNotFoundException("没有配置解析CODE:[" + baseDTO.getCode() + "]的策略"))
                                .parse(baseDTO.getContent());
                    } catch (ClassNotFoundException e) {
                        log.error(e.getMessage());
                    }
                });
            }
        });
    }

    @Override
    protected void finalize() throws Throwable {
        PARSE_THREAD.shutdown();
    }
}
