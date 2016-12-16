package com.gqj.dao;

import com.gqj.entity.Position;

public interface PositionMapper {
    int deleteByPrimaryKey(Long posId);

    int insert(Position record);

    int insertSelective(Position record);

    Position selectByPrimaryKey(Long posId);

    int updateByPrimaryKeySelective(Position record);

    int updateByPrimaryKey(Position record);
}