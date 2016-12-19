package com.base.admin.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.admin.dao.UserMapper;
import com.base.admin.entity.User;
import com.base.admin.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserMapper userMapper;

	@Override
	public int deleteByPrimaryKeys(String[] userIds) {
		return userMapper.deleteByPrimaryKeys(userIds);
	}

	@Override
	public Map<String, Object> queryUsersForPage(User user) {
		List<Map<String, Object>> users = userMapper
				.queryUsersForPage(user);
		int count = userMapper.queryCountOfUsersForPage(user);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", users);
		map.put("total", count);
		return map;
	}

	@Override
	public Map<String, Object> queryUserForObject(User user) {
		return userMapper.queryUsersForList(user).get(0);
	}

	@Override
	public int insertSelective(User user) {
		return userMapper.insertSelective(user);
	}

	@Override
	public int updateByPrimaryKeysSelective(User user) {
		return userMapper.updateByPrimaryKeysSelective(user);
	}

	@Override
	public User queryUserForSession(User user) {
		return userMapper.queryUserForSession(user);
	}

}
