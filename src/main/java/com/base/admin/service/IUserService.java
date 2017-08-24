package com.base.admin.service;

import java.util.Map;

import com.base.admin.entity.User;

public interface IUserService {
	Map<String, Object> deleteByPrimaryKeys(User user);

	Map<String, Object> queryUsersForPage(User user);

	Map<String, Object> insertSelective(User user);

	Map<String, Object> updateByPrimaryKeysSelective(User user);

	User queryUserForObject(User user);

	User queryUserForSession(User user);
}
