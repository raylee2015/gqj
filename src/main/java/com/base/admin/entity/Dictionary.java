package com.base.admin.entity;

public class Dictionary extends Base {
	private Long dicId;

	private String dicCode;

	private String dicName;

	private String dicValue;

	private Long dicSort;

	private Long menuId;

	public Long getDicId() {
		return dicId;
	}

	public void setDicId(Long dicId) {
		this.dicId = dicId;
	}

	public String getDicCode() {
		return dicCode;
	}

	public void setDicCode(String dicCode) {
		this.dicCode = dicCode == null ? null : dicCode.trim();
	}

	public String getDicName() {
		return dicName;
	}

	public void setDicName(String dicName) {
		this.dicName = dicName == null ? null : dicName.trim();
	}

	public String getDicValue() {
		return dicValue;
	}

	public void setDicValue(String dicValue) {
		this.dicValue = dicValue == null ? null : dicValue.trim();
	}

	public Long getDicSort() {
		return dicSort;
	}

	public void setDicSort(Long dicSort) {
		this.dicSort = dicSort;
	}

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}
}