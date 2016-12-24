package com.gqj.dao;

import com.gqj.entity.Material;

public interface MaterialMapper {
    int deleteByPrimaryKey(Long matId);

    int insert(Material record);

    int insertSelective(Material record);

    Material selectByPrimaryKey(Long matId);

    int updateByPrimaryKeySelective(Material record);

    int updateByPrimaryKey(Material record);
}