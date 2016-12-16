package com.base.admin.entity;

import com.base.entity.Base;

public class Menu extends Base {
	private String menuExtCode;

	private Long menuId;

	private String menuInnerCode;

	private String menuLevel;

	private String menuName;

	private Long menuSort;

	private String menuUrl;

	private Long upMenuId;

	public String getMenuExtCode() {
		return menuExtCode;
	}

	public Long getMenuId() {
		return menuId;
	}

	public String getMenuInnerCode() {
		return menuInnerCode;
	}

	public String getMenuLevel() {
		return menuLevel;
	}

	public String getMenuName() {
		return menuName;
	}

	public Long getMenuSort() {
		return menuSort;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public Long getUpMenuId() {
		return upMenuId;
	}

	public void setMenuExtCode(String menuExtCode) {
		this.menuExtCode = menuExtCode == null ? null
				: menuExtCode.trim();
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public void setMenuInnerCode(String menuInnerCode) {
		this.menuInnerCode = menuInnerCode;
	}

	public void setMenuLevel(String menuLevel) {
		this.menuLevel = menuLevel == null ? null : menuLevel.trim();
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName == null ? null : menuName.trim();
	}

	public void setMenuSort(Long menuSort) {
		this.menuSort = menuSort;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl == null ? null : menuUrl.trim();
	}

	public void setUpMenuId(Long upMenuId) {
		this.upMenuId = upMenuId;
	}
}