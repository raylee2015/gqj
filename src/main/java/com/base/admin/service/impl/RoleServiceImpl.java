package com.base.admin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.admin.dao.RoleMapper;
import com.base.admin.entity.Role;
import com.base.admin.service.IRoleService;
@Deprecated
@Service
public class RoleServiceImpl implements IRoleService {

	@Autowired
	private RoleMapper roleMapper;

	@Override
	public int deleteByPrimaryKeys(Role role) {
		return roleMapper.deleteByPrimaryKeys(role);
	}

	@Override
	public int insertSelective(Role role) {
		return roleMapper.insertSelective(role);
	}

	@Override
	public int selectCountOfRolesForPage(Role role) {
		return roleMapper.selectCountOfRolesForPage(role);
	}

	@Override
	public List<Map<String, Object>> selectRolesForPage(Role role) {
		return roleMapper.selectRolesForPage(role);
	}

	@Override
	public int updateByPrimaryKeySelective(Role role) {
		return roleMapper.updateByPrimaryKeySelective(role);
	}

}
