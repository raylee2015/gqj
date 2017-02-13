package com.gqj.entity;

import com.base.entity.Base;

public class BaseTool extends Base {
	private Long baseToolId;

	private Long toolDemandId;

	private Long manId;

	private String baseToolModel;

	private String baseToolSpec;

	private String baseToolRemark;

	private String baseToolName;

	private String baseToolEarthWire;

	public String getBaseToolEarthWire() {
		return baseToolEarthWire;
	}

	public void setBaseToolEarthWire(String baseToolEarthWire) {
		this.baseToolEarthWire = baseToolEarthWire;
	}

	public String getBaseToolName() {
		return baseToolName;
	}

	public void setBaseToolName(String baseToolName) {
		this.baseToolName = baseToolName;
	}

	public Long getToolDemandId() {
		return toolDemandId;
	}

	public void setToolDemandId(Long toolDemandId) {
		this.toolDemandId = toolDemandId;
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