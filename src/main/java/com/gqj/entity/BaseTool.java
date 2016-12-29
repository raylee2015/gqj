package com.gqj.entity;

import com.base.entity.Base;

public class BaseTool extends Base {
	private Long baseToolId;

	private Long typeId;

	private Long toolIdForPlan;

	private Long manId;

	private String baseToolModel;

	private String baseToolSpec;

	private String baseToolRemark;

	public Long getBaseToolId() {
		return baseToolId;
	}

	public void setBaseToolId(Long baseToolId) {
		this.baseToolId = baseToolId;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public Long getToolIdForPlan() {
		return toolIdForPlan;
	}

	public void setToolIdForPlan(Long toolIdForPlan) {
		this.toolIdForPlan = toolIdForPlan;
	}

	public Long getManId() {
		return manId;
	}

	public void setManId(Long manId) {
		this.manId = manId;
	}

	public String getBaseToolModel() {
		return baseToolModel;
	}

	public void setBaseToolModel(String baseToolModel) {
		this.baseToolModel = baseToolModel == null ? null
				: baseToolModel.trim();
	}

	public String getBaseToolSpec() {
		return baseToolSpec;
	}

	public void setBaseToolSpec(String baseToolSpec) {
		this.baseToolSpec = baseToolSpec;
	}

	public String getBaseToolRemark() {
		return baseToolRemark;
	}

	public void setBaseToolRemark(String baseToolRemark) {
		this.baseToolRemark = baseToolRemark == null ? null
				: baseToolRemark.trim();
	}
}