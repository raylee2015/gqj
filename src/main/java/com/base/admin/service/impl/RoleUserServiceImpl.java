package com.base.admin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.admin.dao.RoleUserMapper;
import com.base.admin.entity.RoleUser;
import com.base.admin.entity.User;
import com.base.admin.service.IRoleUserService;
import com.base.util.BaseUtil;

import net.sf.json.JSONArray;

/**
 * 改用岗位，不用角色
 * 
 * @author Administrator
 *
 */
@Deprecated
@Service
public class RoleUserServiceImpl
		implements IRoleUserService {

	@Autowired
	private RoleUserMapper roleUserMapper;

	@Override
	public int selectByPrimaryKey(RoleUser roleUser) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteByPrimaryKeys(RoleUser roleMenu) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(RoleUser roleMenu) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String queryRoles() {
		List<Map<String, Object>> roles = roleUserMapper
				.queryRoles();
		JSONArray roleArr = JSONArray.fromObject(roles);
		// 返回是必须全是小写
		String tree = BaseUtil
				.list2Tree(roleArr, -1, "ID", "UP_MENU_ID",
						"children")
				.toString().toLowerCase();
		return tree;
	}

	@Override
	public List<User> queryUnSelectedUsers(
			RoleUser roleUser) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> querySelectedUsers(
			RoleUser roleUser) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int queryCountOfUnSelectedUsers(
			RoleUser roleUser) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int queryCountOfSelectedUsers(
			RoleUser roleUser) {
		// TODO Auto-generated method stub
		return 0;
	}
}
