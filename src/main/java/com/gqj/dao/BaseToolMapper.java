package com.gqj.dao;

import com.gqj.entity.BaseTool;

public interface BaseToolMapper {
    int deleteByPrimaryKey(Long baseToolId);

    int insert(BaseTool record);

    int insertSelective(BaseTool record);

    BaseTool selectByPrimaryKey(Long baseToolId);

    int updateByPrimaryKeySelective(BaseTool record);

    int updateByPrimaryKey(BaseTool record);
}