package com.base.admin.entity;

import com.base.entity.Base;

public class Dept extends Base {

	private String deptCode;

	private Long deptId;

	private String deptInnerCode;

	private String deptInnerName;

	private String deptName;

	private Long deptSort;

	private Long upDeptId;

	private String upDeptName;

	public String getDeptCode() {
		return deptCode;
	}

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

	public String getUpDeptName() {
		return upDeptName;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
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
		this.deptName = deptName == null ? null
				: deptName.trim();
	}

	public void setDeptSort(Long deptSort) {
		this.deptSort = deptSort;
	}

	public void setUpDeptId(Long upDeptId) {
		this.upDeptId = upDeptId;
	}

	public void setUpDeptName(String upDeptName) {
		this.upDeptName = upDeptName;
	}
}