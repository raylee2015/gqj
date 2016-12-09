package com.base.admin.dao;

import com.base.admin.entity.PostMenu;

public interface PostMenuMapper {
    int deleteByPrimaryKey(PostMenu key);

    int insert(PostMenu record);

    int insertSelective(PostMenu record);
}