package com.gqj.entity;

import com.base.entity.Base;

public class Manufacturer extends Base {
	private Long manId;

	private String manName;

	public Long getManId() {
		return manId;
	}

	public void setManId(Long manId) {
		this.manId = manId;
	}

	public String getManName() {
		return manName;
	}

	public void setManName(String manName) {
		this.manName = manName == null ? null
				: manName.trim();
	}
}