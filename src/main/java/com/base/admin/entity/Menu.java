package com.base.admin.entity;

public class Menu extends Base {
	private Long menuId;

	private Long upMenuId;

	private String menuName;

	private String menuLevel;

	private String menuUrl;

	private String menuIcon;

	private Long menuSort;

	private String menuExtCode;

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public Long getUpMenuId() {
		return upMenuId;
	}

	public void setUpMenuId(Long upMenuId) {
		this.upMenuId = upMenuId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName == null ? null : menuName.trim();
	}

	public String getMenuLevel() {
		return menuLevel;
	}

	public void setMenuLevel(String menuLevel) {
		this.menuLevel = menuLevel == null ? null : menuLevel.trim();
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl == null ? null : menuUrl.trim();
	}

	public String getMenuIcon() {
		return menuIcon;
	}

	public void setMenuIcon(String menuIcon) {
		this.menuIcon = menuIcon == null ? null : menuIcon.trim();
	}

	public Long getMenuSort() {
		return menuSort;
	}

	public void setMenuSort(Long menuSort) {
		this.menuSort = menuSort;
	}

	public String getMenuExtCode() {
		return menuExtCode;
	}

	public void setMenuExtCode(String menuExtCode) {
		this.menuExtCode = menuExtCode == null ? null
				: menuExtCode.trim();
	}
}