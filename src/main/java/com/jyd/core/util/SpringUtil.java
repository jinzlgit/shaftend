package com.jyd.core.util;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author Zhenlin Jin
 * @date 2021/8/12 22:08
 */
@Slf4j
@Component
public class SpringUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        applicationContext = context;
    }

    public static Object getBeanByName(String name) {
        if (StrUtil.isBlank(name)) {
            return null;
        }
        Object bean = null;
        try {
            bean = applicationContext.getBean(name);
        } catch (BeansException e) {
            log.error(e.getMessage());
        }
        return bean;
    }
}
