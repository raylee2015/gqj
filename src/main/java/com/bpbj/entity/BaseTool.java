package com.bpbj.entity;

import com.base.entity.Base;

public class BaseTool extends Base {
	private Long baseToolId;

	private Long manId;

	private Long baseToolType;

	private String baseToolCode;

	private String baseToolModel;

	private String baseToolSpec;

	private String baseToolRemark;

	private String baseToolName;

	public Long getBaseToolType() {
		return baseToolType;
	}

	public void setBaseToolType(Long baseToolType) {
		this.baseToolType = baseToolType;
	}

	public String getBaseToolCode() {
		return baseToolCode;
	}

	public void setBaseToolCode(String baseToolCode) {
		this.baseToolCode = baseToolCode;
	}

	public String getBaseToolName() {
		return baseToolName;
	}

	public void setBaseToolName(String baseToolName) {
		this.baseToolName = baseToolName;
	}

	public Long getBaseToolId() {
		return baseToolId;
	}

	public void setBaseToolId(Long baseToolId) {
		this.baseToolId = baseToolId;
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