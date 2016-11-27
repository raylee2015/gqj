package com.sample.entity;

public class Sample {
	private long userId;
	private String userName;
	private String userCode;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName == null ? null : userName.trim();
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode == null ? null : userCode.trim();
	}

	@Override
	public String toString() {
		return "userId=" + userId + ", userName=" + userName + ", userCode=" + userCode;
	}
}
