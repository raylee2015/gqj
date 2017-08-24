package com.base.admin.dao;

import java.util.List;

import com.base.admin.entity.User;

public interface UserMapper {
	int deleteByPrimaryKeys(User user);

	List<User> queryUsersForPage(User user);

	List<User> queryUsersForList(User user);

	User queryUserForSession(User user);

	int queryCountOfUsersForPage(User user);

	int insertSelective(User user);

	int updateByPrimaryKeysSelective(User user);

}