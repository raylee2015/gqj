package com.base.admin.dao;

import com.base.admin.entity.RoleMenu;
@Deprecated
public interface RoleMenuMapper {
    int deleteByPrimary(RoleMenu roleMenu);

    int insert(RoleMenu roleMenu);

    int insertSelective(RoleMenu roleMenu);
}