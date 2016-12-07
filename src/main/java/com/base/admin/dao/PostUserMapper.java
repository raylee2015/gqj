package com.base.admin.dao;

import com.base.admin.entity.PostUserKey;

public interface PostUserMapper {
    int deleteByPrimaryKey(PostUserKey key);

    int insert(PostUserKey record);

    int insertSelective(PostUserKey record);
}