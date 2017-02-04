package com.gqj.entity;

import com.base.entity.Base;

public class MaterialInventory extends Base {
	private Double inventAmount;

	private Long baseToolId;

	private Long storeId;

	private Long posId;

	public Long getBaseToolId() {
		return baseToolId;
	}

	public void setBaseToolId(Long baseToolId) {
		this.baseToolId = baseToolId;
	}

	public Long getPosId() {
		return posId;
	}

	public void setPosId(Long posId) {
		this.posId = posId;
	}

	public Double getInventAmount() {
		return inventAmount;
	}

	public Long getStoreId() {
		return storeId;
	}

	public void setInventAmount(Double inventAmount) {
		this.inventAmount = inventAmount;
	}

	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}
}