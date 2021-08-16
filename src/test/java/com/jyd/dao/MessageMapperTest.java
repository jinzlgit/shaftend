package com.jyd.dao;

import com.jyd.entity.Message;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MessageMapperTest {

    @Autowired
    private MessageMapper messageMapper;

    @Test
    void insert() {
        Message message = new Message();
        message.setMsgContent("789");
        message.setCreateTime(new Date());
        int i = messageMapper.insert(message);
        System.out.println("插入记录时返回的主键为:" + message.getMsgId());
    }
}