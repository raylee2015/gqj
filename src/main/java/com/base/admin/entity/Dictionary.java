package com.base.admin.entity;

public class Dictionary extends Base {
	private String dicCode;

	private Long dicId;

	private String dicLabel;

	private String dicName;

	private Long dicSort;

	private String dicValue;

	private Long menuId;

	public String getDicCode() {
		return dicCode;
	}

	public Long getDicId() {
		return dicId;
	}

	public String getDicLabel() {
		return dicLabel;
	}

	public String getDicName() {
		return dicName;
	}

	public Long getDicSort() {
		return dicSort;
	}

	public String getDicValue() {
		return dicValue;
	}

	public Long getMenuId() {
		return menuId;
	}

	public void setDicCode(String dicCode) {
		this.dicCode = dicCode == null ? null : dicCode.trim();
	}

	public void setDicId(Long dicId) {
		this.dicId = dicId;
	}

	public void setDicLabel(String dicLabel) {
		this.dicLabel = dicLabel;
	}

	public void setDicName(String dicName) {
		this.dicName = dicName == null ? null : dicName.trim();
	}

	public void setDicSort(Long dicSort) {
		this.dicSort = dicSort;
	}

	public void setDicValue(String dicValue) {
		this.dicValue = dicValue == null ? null : dicValue.trim();
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}
}