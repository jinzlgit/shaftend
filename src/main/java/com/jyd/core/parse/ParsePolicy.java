package com.jyd.core.parse;

import com.jyd.core.domain.BaseDTO;
import com.jyd.core.util.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author Zhenlin Jin
 * @date 2021/8/12 22:21
 */
@Slf4j
@Component
public class ParsePolicy {
    public static void parse(BaseDTO baseDTO) {
        IParsePolicy policy = (IParsePolicy) SpringUtil.getBeanByName(baseDTO.getCode());
        try {
            Optional.ofNullable(policy)
                    .orElseThrow(() -> new ClassNotFoundException("没有配置解析CODE:[" + baseDTO.getCode() + "]的策略"))
                    .parse(baseDTO.getContent());
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage());
        }
    }
}
