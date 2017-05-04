package com.gqj.dao;

import com.gqj.entity.CheckDetail;

public interface CheckDetailMapper {
    int insert(CheckDetail record);

    int insertSelective(CheckDetail record);
}