package com.gqj.dao;

import com.gqj.entity.Batch;

public interface BatchMapper {
    int deleteByPrimaryKey(Long batchId);

    int insert(Batch record);

    int insertSelective(Batch record);

    Batch selectByPrimaryKey(Long batchId);

    int updateByPrimaryKeySelective(Batch record);

    int updateByPrimaryKey(Batch record);
}