package com.base.admin.entity;

public class User extends Base {
	private String userCode;

	private Long userDeptId;

	private Long userId;

	private String userName;

	private String userPassWord;

	private String userPhone;

	private String userSort;

	public String getUserCode() {
		return userCode;
	}

	public Long getUserDeptId() {
		return userDeptId;
	}

	public Long getUserId() {
		return userId;
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

	public void setUserCode(String userCode) {
		this.userCode = userCode == null ? null : userCode.trim();
	}

	public void setUserDeptId(Long userDeptId) {
		this.userDeptId = userDeptId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setUserName(String userName) {
		this.userName = userName == null ? null : userName.trim();
	}

	public void setUserPassWord(String userPassWord) {
		this.userPassWord = userPassWord;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone == null ? null : userPhone.trim();
	}

	public void setUserSort(String userSort) {
		this.userSort = userSort == null ? null : userSort.trim();
	}
}