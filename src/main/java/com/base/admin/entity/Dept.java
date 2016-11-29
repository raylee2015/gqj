package com.base.admin.entity;

public class Dept {
	private Long deptId;

	private int currPage;

	private int pageSize;

	public int getCurrPage() {
		return currPage;
	}

	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * 用来生成部门树
	 */
	private Dept children;

	public Dept getChildren() {
		return children;
	}

	public void setChildren(Dept children) {
		this.children = children;
	}

	private Long upDeptId;

	private String deptName;

	private String deptType;

	private Long deptSort;

	private String deptInnerCode;

	private String deptInnerName;

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public Long getUpDeptId() {
		return upDeptId;
	}

	public void setUpDeptId(Long upDeptId) {
		this.upDeptId = upDeptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName == null ? null : deptName.trim();
	}

	public String getDeptType() {
		return deptType;
	}

	public void setDeptType(String deptType) {
		this.deptType = deptType == null ? null : deptType.trim();
	}

	public Long getDeptSort() {
		return deptSort;
	}

	public void setDeptSort(Long deptSort) {
		this.deptSort = deptSort;
	}

	public String getDeptInnerCode() {
		return deptInnerCode;
	}

	public void setDeptInnerCode(String deptInnerCode) {
		this.deptInnerCode = deptInnerCode == null ? null : deptInnerCode.trim();
	}

	public String getDeptInnerName() {
		return deptInnerName;
	}

	public void setDeptInnerName(String deptInnerName) {
		this.deptInnerName = deptInnerName == null ? null : deptInnerName.trim();
	}
}