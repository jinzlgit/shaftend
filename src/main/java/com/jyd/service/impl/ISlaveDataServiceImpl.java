package com.jyd.service.impl;

import com.jyd.dao.MessageMapper;
import com.jyd.dao.SlaveDataMapper;
import com.jyd.entity.Message;
import com.jyd.entity.SlaveData;
import com.jyd.service.ISlaveDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author Zhenlin Jin
 * @date 2021/8/12 21:20
 */
@Slf4j
@Service
public class ISlaveDataServiceImpl implements ISlaveDataService {

    @Autowired
    private SlaveDataMapper slaveDataMapper;

    @Autowired
    private MessageMapper messageMapper;

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public int insert(SlaveData slaveData, String msg) {
        Message message = new Message();
        message.setMsgContent(msg);
        message.setCreateTime(new Date());
        messageMapper.insert(message);
        slaveData.setMsgId(message.getMsgId());
        return slaveDataMapper.insert(slaveData);
    }
}
