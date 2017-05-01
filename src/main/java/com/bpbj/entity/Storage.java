package com.bpbj.entity;

import com.base.entity.Base;

public class Storage extends Base {
	private Long storeId;

	private String storeName;

	private Long storeDeptId;

	public Long getStoreId() {
		return storeId;
	}

	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName == null ? null : storeName.trim();
	}

	public Long getStoreDeptId() {
		return storeDeptId;
	}

	public void setStoreDeptId(Long storeDeptId) {
		this.storeDeptId = storeDeptId;
	}
}