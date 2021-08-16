package com.jyd.core.domain;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Zhenlin Jin
 * @date 2021/8/12 23:50
 */
@Slf4j
@Data
@Builder
public class Online {
    private static final Map<String, Online> GROUP = new ConcurrentHashMap<>();
    private static final AtomicInteger COUNT = new AtomicInteger(0);

    private final String channelId;
    private final String number;

    public static void on(String channelId, String number) {
        Online online = GROUP.putIfAbsent(channelId, Online.builder().number(number).build());
        if (Optional.ofNullable(online).isPresent()) return;
        COUNT.incrementAndGet();
        log.info("设备:[{}]上线，在线数:[{}]", number, COUNT.get());
    }

    public static void off(String channelId) {
        Online online = GROUP.get(channelId);
        if (!Optional.ofNullable(online).isPresent()) return;
        log.info("设备:[{}]下线", online.getNumber());
        GROUP.remove(channelId);
        COUNT.decrementAndGet();
    }
}
