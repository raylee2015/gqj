package com.bpbj.entity;

import com.base.entity.Base;

public class DemandPlanDetail extends Base {
	private Long detailId;

	private Long planId;

	private Long toolId;

	private Long toolSumAmount;

	private Long toolAmount;

	private String detailRemark;

	public Long getDetailId() {
		return detailId;
	}

	public void setDetailId(Long detailId) {
		this.detailId = detailId;
	}

	public Long getPlanId() {
		return planId;
	}

	public void setPlanId(Long planId) {
		this.planId = planId;
	}

	public Long getToolId() {
		return toolId;
	}

	public void setToolId(Long toolId) {
		this.toolId = toolId;
	}

	public Long getToolSumAmount() {
		return toolSumAmount;
	}

	public void setToolSumAmount(Long toolSumAmount) {
		this.toolSumAmount = toolSumAmount;
	}

	public Long getToolAmount() {
		return toolAmount;
	}

	public void setToolAmount(Long toolAmount) {
		this.toolAmount = toolAmount;
	}

	public String getDetailRemark() {
		return detailRemark;
	}

	public void setDetailRemark(String detailRemark) {
		this.detailRemark = detailRemark == null ? null
				: detailRemark.trim();
	}
}