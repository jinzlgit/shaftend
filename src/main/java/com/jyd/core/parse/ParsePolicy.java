package com.jyd.core.parse;

import com.jyd.core.constant.CodeEnum;
import com.jyd.core.domain.BaseDTO;
import com.jyd.core.mq.MessageBlockingQueue;
import com.jyd.core.util.SpringUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Zhenlin Jin
 * @date 2021/8/12 22:21
 */
@Slf4j
public class ParsePolicy {
    public static final ParsePolicy INSTANCE = new ParsePolicy();

    public static final ExecutorService PARSE_THREAD = Executors.newSingleThreadExecutor();

    private ParsePolicy() {
    }

    public void parse() {
        PARSE_THREAD.execute(() -> {
            while (true) {
                BaseDTO baseDTO = MessageBlockingQueue.take();
                String beanName = CodeEnum.getBeanNameByCode(baseDTO.getCode());
                IParsePolicy policy = (IParsePolicy) SpringUtil.getBeanByName(beanName);
                try {
                    Optional.ofNullable(policy)
                            .orElseThrow(() -> new ClassNotFoundException("没有配置解析CODE:[" + baseDTO.getCode() + "]的策略"))
                            .parse(baseDTO.getContent());
                } catch (ClassNotFoundException e) {
                    log.error(e.getMessage());
                }
            }
        });
    }

    @Override
    protected void finalize() throws Throwable {
        PARSE_THREAD.shutdown();
    }
}
