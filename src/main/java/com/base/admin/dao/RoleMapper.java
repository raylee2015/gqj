package com.base.admin.dao;

import java.util.List;
import java.util.Map;

import com.base.admin.entity.Role;

public interface RoleMapper {

	int deleteByPrimaryKeys(Role role);

	int insertSelective(Role role);

	int updateByPrimaryKeySelective(Role role);

	List<Map<String, Object>> selectRolesForPage(Role role);

	int selectCountOfRolesForPage(Role role);
}