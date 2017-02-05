package com.gqj.dao;

import com.gqj.entity.Tool;

public interface ToolMapper {
    int deleteByPrimaryKey(Long toolId);

    int insert(Tool record);

    int insertSelective(Tool record);

    Tool selectByPrimaryKey(Long toolId);

    int updateByPrimaryKeySelective(Tool record);

    int updateByPrimaryKey(Tool record);
}