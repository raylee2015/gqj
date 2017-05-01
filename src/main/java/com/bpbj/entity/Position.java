package com.bpbj.entity;

import com.base.entity.Base;

public class Position extends Base {
	private Long posDeptId;

	private Long posId;

	private String posName;

	private Long storeId;

	private String storeName;

	public Long getPosDeptId() {
		return posDeptId;
	}

	public Long getPosId() {
		return posId;
	}

	public String getPosName() {
		return posName;
	}

	public Long getStoreId() {
		return storeId;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setPosDeptId(Long posDeptId) {
		this.posDeptId = posDeptId;
	}

	public void setPosId(Long posId) {
		this.posId = posId;
	}

	public void setPosName(String posName) {
		this.posName = posName == null ? null
				: posName.trim();
	}

	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
}