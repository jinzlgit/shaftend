package com.jyd.core.pool.factory;

import com.jyd.core.domain.BaseDTO;
import com.jyd.core.pool.task.ParseTask;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Zhenlin Jin
 * @date 2021/8/20 10:30
 */
@Slf4j
public class ThreadFactory {

    public static Runnable parseTask(BaseDTO baseDTO) {
        return new ParseTask(baseDTO);
    }
}
