package com.base.admin.dao;

import java.util.List;
import java.util.Map;

import com.base.admin.entity.User;

public interface UserMapper {
	int deleteByPrimaryKeys(String[] userIds);

	List<Map<String, Object>> selectUsersForPage(User user);

	int selectCountOfUsersForPage(User user);

	int insertSelective(User user);

	int updateByPrimaryKeySelective(User user);

}