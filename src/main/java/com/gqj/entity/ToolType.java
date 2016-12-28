package com.gqj.entity;

import com.base.entity.Base;

public class ToolType extends Base {
	private Long typeId;

	private String typeName;

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName == null ? null
				: typeName.trim();
	}
}