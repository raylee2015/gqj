package com.base.admin.entity;

import com.base.entity.Base;

@Deprecated
public class Role extends Base {
	private Long roleId;

	private Long menuId;

	private String roleName;

	private Long roleSort;

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

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName == null ? null : roleName.trim();
	}

	public Long getRoleSort() {
		return roleSort;
	}

	public void setRoleSort(Long roleSort) {
		this.roleSort = roleSort;
	}
}