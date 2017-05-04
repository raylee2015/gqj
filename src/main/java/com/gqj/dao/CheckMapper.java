package com.gqj.dao;

import com.gqj.entity.Check;

public interface CheckMapper {
    int insert(Check record);

    int insertSelective(Check record);
}