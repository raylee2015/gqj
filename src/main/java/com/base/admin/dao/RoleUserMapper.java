package com.base.admin.dao;

import java.util.List;
import java.util.Map;

import com.base.admin.entity.RoleUser;
import com.base.admin.entity.User;
@Deprecated
public interface RoleUserMapper {
	int selectByPrimaryKey(RoleUser roleUser);

	int deleteByPrimaryKeys(RoleUser roleMenu);

	int insert(RoleUser roleMenu);

	List<Map<String, Object>> queryRoles();

	List<User> queryUnSelectedUsers(RoleUser roleUser);

	List<User> querySelectedUsers(RoleUser roleUser);

	int queryCountOfUnSelectedUsers(RoleUser roleUser);

	int queryCountOfSelectedUsers(RoleUser roleUser);
}