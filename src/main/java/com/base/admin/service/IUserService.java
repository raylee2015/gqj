package com.base.admin.service;

import java.util.List;
import java.util.Map;

import com.base.admin.entity.User;

public interface IUserService {
	int deleteByPrimaryKeys(String[] userIds);

	List<Map<String, Object>> selectUsersForPage(User user);

	int selectCountOfUsersForPage(User user);

	int insertSelective(User user);

	int updateByPrimaryKeysSelective(User user);

	User selectUsersForObject(User user);
}
