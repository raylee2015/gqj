package com.gqj.entity;

import java.util.Date;

import com.base.entity.Base;

public class DemandPlan extends Base {
	private Long planId;

	private Long upPlanId;

	private Long planDeptId;

	private Long planType;

	private String planCode;

	private Long planStatus;

	private Long planCreateUserId;

	private Date planCreateDate;

	private String planAssignedDeptId;

	private String planAssignedDeptName;

	private String planRemark;

	public Long getPlanId() {
		return planId;
	}

	public void setPlanId(Long planId) {
		this.planId = planId;
	}

	public Long getUpPlanId() {
		return upPlanId;
	}

	public void setUpPlanId(Long upPlanId) {
		this.upPlanId = upPlanId;
	}

	public Long getPlanDeptId() {
		return planDeptId;
	}

	public void setPlanDeptId(Long planDeptId) {
		this.planDeptId = planDeptId;
	}

	public Long getPlanType() {
		return planType;
	}

	public void setPlanType(Long planType) {
		this.planType = planType;
	}

	public String getPlanCode() {
		return planCode;
	}

	public void setPlanCode(String planCode) {
		this.planCode = planCode == null ? null
				: planCode.trim();
	}

	public Long getPlanStatus() {
		return planStatus;
	}

	public void setPlanStatus(Long planStatus) {
		this.planStatus = planStatus;
	}

	public Long getPlanCreateUserId() {
		return planCreateUserId;
	}

	public void setPlanCreateUserId(Long planCreateUserId) {
		this.planCreateUserId = planCreateUserId;
	}

	public Date getPlanCreateDate() {
		return planCreateDate;
	}

	public void setPlanCreateDate(Date planCreateDate) {
		this.planCreateDate = planCreateDate;
	}

	public String getPlanAssignedDeptId() {
		return planAssignedDeptId;
	}

	public void setPlanAssignedDeptId(
			String planAssignedDeptId) {
		this.planAssignedDeptId = planAssignedDeptId == null
				? null : planAssignedDeptId.trim();
	}

	public String getPlanAssignedDeptName() {
		return planAssignedDeptName;
	}

	public void setPlanAssignedDeptName(
			String planAssignedDeptName) {
		this.planAssignedDeptName = planAssignedDeptName == null
				? null : planAssignedDeptName.trim();
	}

	public String getPlanRemark() {
		return planRemark;
	}

	public void setPlanRemark(String planRemark) {
		this.planRemark = planRemark == null ? null
				: planRemark.trim();
	}
}