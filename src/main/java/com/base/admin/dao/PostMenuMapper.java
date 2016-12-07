package com.base.admin.dao;

import com.base.admin.entity.PostMenuKey;

public interface PostMenuMapper {
    int deleteByPrimaryKey(PostMenuKey key);

    int insert(PostMenuKey record);

    int insertSelective(PostMenuKey record);
}