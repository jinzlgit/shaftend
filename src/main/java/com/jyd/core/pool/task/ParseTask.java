package com.jyd.core.pool.task;

import com.jyd.core.constant.CodeEnum;
import com.jyd.core.domain.BaseDTO;
import com.jyd.core.parse.IParsePolicy;
import com.jyd.core.util.SpringUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * @author Zhenlin Jin
 * @date 2021/8/20 10:53
 */
@Slf4j
public class ParseTask implements Runnable {
    private BaseDTO baseDTO;

    public ParseTask(final BaseDTO baseDTO) {
        this.baseDTO = baseDTO;
    }

    @Override
    public void run() {
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
}
