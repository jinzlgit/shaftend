package com.jyd.service.impl;

import com.jyd.dao.MessageMapper;
import com.jyd.entity.Message;
import com.jyd.service.IMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Zhenlin Jin
 * @date 2021/8/12 21:18
 */
@Slf4j
@Service
public class MessageServiceImpl implements IMessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Override
    public int insert(Message message) {
        return messageMapper.insert(message);
    }
}
