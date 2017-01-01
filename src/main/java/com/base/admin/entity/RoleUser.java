package com.base.admin.entity;

import com.base.entity.Base;
/**
 * 改用岗位，不用角色
 * @author Administrator
 *
 */
@Deprecated
public class RoleUser extends Base{
    private Long roleId;

    private Long userId;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}