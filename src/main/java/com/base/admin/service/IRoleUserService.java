package com.base.admin.service;

import java.util.List;

import com.base.admin.entity.RoleUser;
import com.base.admin.entity.User;
@Deprecated
public interface IRoleUserService {
	int selectByPrimaryKey(RoleUser roleUser);

	int deleteByPrimaryKeys(RoleUser roleMenu);

	int insert(RoleUser roleMenu);

	String queryRoles();

	List<User> queryUnSelectedUsers(RoleUser roleUser);

	List<User> querySelectedUsers(RoleUser roleUser);

	int queryCountOfUnSelectedUsers(RoleUser roleUser);

	int queryCountOfSelectedUsers(RoleUser roleUser);
}
