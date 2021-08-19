package com.jyd.core.mq;

import com.jyd.core.domain.BaseDTO;
import com.jyd.core.parse.ParsePolicy;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Zhenlin Jin
 * @date 2021/8/19 17:44
 */
@Slf4j
public class MessageBlockingQueue {
    private static final BlockingQueue<BaseDTO> MQ = new LinkedBlockingQueue<>();
    public static AtomicBoolean firstOffer = new AtomicBoolean(true);
    private static ParsePolicy parsePolicy = ParsePolicy.INSTANCE;

    public static void offer(BaseDTO baseDTO) {
        try {
            boolean offer = MQ.offer(baseDTO, 1000, TimeUnit.MILLISECONDS);
            if (firstOffer.get() && offer) {
                firstOffer.set(false);
                parsePolicy.parse();
            }
        } catch (Exception e) {
            log.error("报文入队列出错:{}", e.getMessage());
        }
    }

    public static BaseDTO take() {
        BaseDTO e = null;
        try {
            e = MQ.take();
        } catch (Exception ex) {
            log.error("报文出队列出错:{}", ex.getMessage());
        }
        return e;
    }
}
