package com.gqj.entity;

import com.base.entity.Base;

public class ToolDemand extends Base {
	private Long toolId;

	private Long typeId;

	private String toolName;

	private String toolStandardConfigDemand;

	private String toolModelDemand;

	private Long toolUnit;

	private String toolRemark;

	public Long getToolId() {
		return toolId;
	}

	public void setToolId(Long toolId) {
		this.toolId = toolId;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public String getToolName() {
		return toolName;
	}

	public void setToolName(String toolName) {
		this.toolName = toolName;
	}

	public String getToolStandardConfigDemand() {
		return toolStandardConfigDemand;
	}

	public void setToolStandardConfigDemand(
			String toolStandardConfigDemand) {
		this.toolStandardConfigDemand = toolStandardConfigDemand;
	}

	public String getToolModelDemand() {
		return toolModelDemand;
	}

	public void setToolModelDemand(String toolModelDemand) {
		this.toolModelDemand = toolModelDemand;
	}

	public Long getToolUnit() {
		return toolUnit;
	}

	public void setToolUnit(Long toolUnit) {
		this.toolUnit = toolUnit;
	}

	public String getToolRemark() {
		return toolRemark;
	}

	public void setToolRemark(String toolRemark) {
		this.toolRemark = toolRemark;
	}

}