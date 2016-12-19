package com.base.admin.service;

import java.util.Map;

import com.base.admin.entity.User;

public interface IUserService {
	int deleteByPrimaryKeys(String[] userIds);

	Map<String, Object> queryUsersForPage(User user);

	int insertSelective(User user);

	int updateByPrimaryKeysSelective(User user);

	Map<String, Object> queryUserForObject(User user);

	User queryUserForSession(User user);
}
