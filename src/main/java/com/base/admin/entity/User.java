package com.base.admin.entity;

import com.base.entity.Base;

public class User extends Base {
	private String deptCode;

	private Long deptId;

	private String deptInnerCode;

	private String deptInnerName;

	private String deptName;

	private Long deptSort;

	private Long upDeptId;

	private String userCode;

	private String userDeptCode;

	private Long userDeptId;

	private String userDeptName;

	private Long userId;

	private String userLockFlag;

	private String userLockFlagName;

	private String userName;

	private String userPassWord;

	private String userPhone;

	private String userSort;

	private String userUseFlag;

	private String userUseFlagName;

	public String getDeptCode() {
		return deptCode;
	}

	public Long getDeptId() {
		return deptId;
	}

	public String getDeptInnerCode() {
		return deptInnerCode;
	}

	public String getDeptInnerName() {
		return deptInnerName;
	}

	public String getDeptName() {
		return deptName;
	}

	public Long getDeptSort() {
		return deptSort;
	}

	public Long getUpDeptId() {
		return upDeptId;
	}

	public String getUserCode() {
		return userCode;
	}

	public String getUserDeptCode() {
		return userDeptCode;
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

	public String getUserLockFlagName() {
		return userLockFlagName;
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

	public String getUserUseFlagName() {
		return userUseFlagName;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public void setDeptInnerCode(String deptInnerCode) {
		this.deptInnerCode = deptInnerCode == null ? null
				: deptInnerCode.trim();
	}

	public void setDeptInnerName(String deptInnerName) {
		this.deptInnerName = deptInnerName == null ? null
				: deptInnerName.trim();
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName == null ? null
				: deptName.trim();
	}

	public void setDeptSort(Long deptSort) {
		this.deptSort = deptSort;
	}

	public void setUpDeptId(Long upDeptId) {
		this.upDeptId = upDeptId;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode == null ? null
				: userCode.trim();
	}

	public void setUserDeptCode(String userDeptCode) {
		this.userDeptCode = userDeptCode;
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

	public void setUserLockFlagName(
			String userLockFlagName) {
		this.userLockFlagName = userLockFlagName;
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

	public void setUserUseFlagName(String userUseFlagName) {
		this.userUseFlagName = userUseFlagName;
	}
}