package com.jyd;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author JinZhenlin
 */
@Slf4j
@EnableAsync
@EnableTransactionManagement
@MapperScan("com.jyd.dao")
@SpringBootApplication
public class ShaftendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShaftendApplication.class, args);
        log.info("===服务启动成功===");
    }

}
