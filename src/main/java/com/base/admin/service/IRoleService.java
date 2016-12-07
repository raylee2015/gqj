package com.base.admin.service;

import java.util.List;
import java.util.Map;

import com.base.admin.entity.Role;
@Deprecated
public interface IRoleService {
	int deleteByPrimaryKeys(Role role);

	int insertSelective(Role role);

	int updateByPrimaryKeySelective(Role role);

	List<Map<String, Object>> selectRolesForPage(Role role);

	int selectCountOfRolesForPage(Role role);
}
