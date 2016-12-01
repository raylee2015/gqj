package com.base.admin.dao;

import java.util.List;
import java.util.Map;

import com.base.admin.entity.User;

public interface UserMapper {
	int deleteByPrimaryKeys(String[] userIds);

	List<Map<String, Object>> selectUsersForPage(User user);

	int selectCountOfUsersForPage(User user);

	int deleteByPrimaryKey(Long userId);

	int insert(User user);

	int insertSelective(User user);

	User selectByPrimaryKey(Long userId);

	int updateByPrimaryKeySelective(User user);

	int updateByPrimaryKey(User user);
}