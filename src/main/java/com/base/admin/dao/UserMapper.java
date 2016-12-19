package com.base.admin.dao;

import java.util.List;
import java.util.Map;

import com.base.admin.entity.User;

public interface UserMapper {
	int deleteByPrimaryKeys(String[] userIds);

	List<Map<String, Object>> queryUsersForPage(User user);

	List<Map<String, Object>> queryUsersForList(User user);

	User queryUserForSession(User user);

	int queryCountOfUsersForPage(User user);

	int insertSelective(User user);

	int updateByPrimaryKeysSelective(User user);

}