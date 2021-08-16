package com.jyd.dao;

import com.jyd.entity.SlaveData;

public interface SlaveDataMapper {
    int deleteByPrimaryKey(Integer slaveId);

    int insert(SlaveData record);

    int insertSelective(SlaveData record);

    SlaveData selectByPrimaryKey(Integer slaveId);

    int updateByPrimaryKeySelective(SlaveData record);

    int updateByPrimaryKey(SlaveData record);
}