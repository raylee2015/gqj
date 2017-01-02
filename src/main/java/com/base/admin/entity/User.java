package com.base.admin.entity;

import com.base.entity.Base;

public class User extends Base {
	private String userCode;

	private Long userDeptId;

	private String userDeptName;

	private Long userId;

	private String userLockFlag;

	private String userName;

	private String userPassWord;

	private String userPhone;

	private String userSort;

	private String userUseFlag;

	public String getUserCode() {
		return userCode;
	}

	public Long getUserDeptId() {
		return userDeptId;
	}

	public String getUserDeptName() {
		return userDeptName;
	}

	public Long getUserId() {
		return userId;
	}

	public String getUserLockFlag() {
		return userLockFlag;
	}

	public String getUserName() {
		return userName;
	}

	public String getUserPassWord() {
		return userPassWord;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public String getUserSort() {
		return userSort;
	}

	public String getUserUseFlag() {
		return userUseFlag;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode == null ? null
				: userCode.trim();
	}

	public void setUserDeptId(Long userDeptId) {
		this.userDeptId = userDeptId;
	}

	public void setUserDeptName(String userDeptName) {
		this.userDeptName = userDeptName;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setUserLockFlag(String userLockFlag) {
		this.userLockFlag = userLockFlag;
	}

	public void setUserName(String userName) {
		this.userName = userName == null ? null
				: userName.trim();
	}

	public void setUserPassWord(String userPassWord) {
		this.userPassWord = userPassWord;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone == null ? null
				: userPhone.trim();
	}

	public void setUserSort(String userSort) {
		this.userSort = userSort == null ? null
				: userSort.trim();
	}

	public void setUserUseFlag(String userUseFlag) {
		this.userUseFlag = userUseFlag;
	}
}