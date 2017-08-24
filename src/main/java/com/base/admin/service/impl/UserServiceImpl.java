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
	public Map<String, Object> deleteByPrimaryKeys(
			User user) {
		Map<String, Object> map = new HashMap<String, Object>();
		int bool = userMapper.deleteByPrimaryKeys(user);
		if (bool == 0) {
			map.put("success", false);
			map.put("msg", "删除失败，请联系管理员");
		} else {
			map.put("success", true);
			map.put("msg", "删除成功");
		}
		return map;
	}

	@Override
	public Map<String, Object> queryUsersForPage(
			User user) {
		List<User> users = userMapper
				.queryUsersForPage(user);
		int count = userMapper
				.queryCountOfUsersForPage(user);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", users);
		map.put("total", count);
		return map;
	}

	@Override
	public User queryUserForObject(User user) {
		List<User> users = userMapper
				.queryUsersForList(user);
		if (users.size() > 0) {
			return userMapper.queryUsersForList(user)
					.get(0);
		} else {
			return null;
		}
	}

	@Override
	public Map<String, Object> insertSelective(User user) {
		Map<String, Object> map = new HashMap<String, Object>();
		int bool = userMapper.insertSelective(user);
		if (bool == 0) {
			map.put("success", false);
			map.put("msg", "保存出错，请联系管理员");
		} else {
			map.put("success", true);
			map.put("msg", "保存成功");
		}
		return map;
	}

	@Override
	public Map<String, Object> updateByPrimaryKeysSelective(
			User user) {
		Map<String, Object> map = new HashMap<String, Object>();
		int bool = userMapper
				.updateByPrimaryKeysSelective(user);
		if (bool == 0) {
			map.put("success", false);
			map.put("msg", "初始化出错，请联系管理员");
		} else {
			map.put("success", true);
			map.put("msg", "初始化成功");
		}
		return map;
	}

	@Override
	public User queryUserForSession(User user) {
		return userMapper.queryUserForSession(user);
	}

}
