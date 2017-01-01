package com.base.admin.entity;
/**
 * 改用岗位，不用角色
 * @author Administrator
 *
 */
@Deprecated
public class RoleMenu {
	private Long roleId;

	private Long menuId;

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}
}