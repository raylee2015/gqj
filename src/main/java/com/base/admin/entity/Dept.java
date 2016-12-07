package com.base.admin.entity;

public class Dept extends Base {

	private Long deptId;

	private String deptInnerCode;

	private String deptInnerName;

	private String deptName;

	private Long deptSort;

	private Long upDeptId;

	public Long getDeptId() {
		return deptId;
	}

	public String getDeptInnerCode() {
		return deptInnerCode;
	}

	public String getDeptInnerName() {
		return deptInnerName;
	}

	public String getDeptName() {
		return deptName;
	}

	public Long getDeptSort() {
		return deptSort;
	}

	public Long getUpDeptId() {
		return upDeptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public void setDeptInnerCode(String deptInnerCode) {
		this.deptInnerCode = deptInnerCode == null ? null
				: deptInnerCode.trim();
	}

	public void setDeptInnerName(String deptInnerName) {
		this.deptInnerName = deptInnerName == null ? null
				: deptInnerName.trim();
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName == null ? null : deptName.trim();
	}

	public void setDeptSort(Long deptSort) {
		this.deptSort = deptSort;
	}

	public void setUpDeptId(Long upDeptId) {
		this.upDeptId = upDeptId;
	}
}