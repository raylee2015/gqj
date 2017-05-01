package com.bpbj.entity;

import java.util.Date;

import com.base.entity.Base;

public class Tool extends Base {
	private Long toolId;

	private String toolCode;

	private Long storeId;

	private Long posId;

	private Long batchId;

	private String toolBox;

	private Long toolStatus;

	private Long toolDeptId;

	private Date toolTestDate;

	private Date toolRejectDate;

	private Double toolTestDateCircle;

	private String toolRemark;

	private Date toolNextTestDate;

	private Date toolPurchaseDate;

	private Date toolManufactureDate;

	private Long baseToolId;

	public Date getToolPurchaseDate() {
		return toolPurchaseDate;
	}

	public void setToolPurchaseDate(Date toolPurchaseDate) {
		this.toolPurchaseDate = toolPurchaseDate;
	}

	public Date getToolManufactureDate() {
		return toolManufactureDate;
	}

	public void setToolManufactureDate(Date toolManufactureDate) {
		this.toolManufactureDate = toolManufactureDate;
	}

	public Long getToolId() {
		return toolId;
	}

	public void setToolId(Long toolId) {
		this.toolId = toolId;
	}

	public String getToolCode() {
		return toolCode;
	}

	public void setToolCode(String toolCode) {
		this.toolCode = toolCode == null ? null : toolCode.trim();
	}

	public Long getStoreId() {
		return storeId;
	}

	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}

	public Long getPosId() {
		return posId;
	}

	public void setPosId(Long posId) {
		this.posId = posId;
	}

	public Long getBatchId() {
		return batchId;
	}

	public void setBatchId(Long batchId) {
		this.batchId = batchId;
	}

	public String getToolBox() {
		return toolBox;
	}

	public void setToolBox(String toolBox) {
		this.toolBox = toolBox == null ? null : toolBox.trim();
	}

	public Long getToolStatus() {
		return toolStatus;
	}

	public void setToolStatus(Long toolStatus) {
		this.toolStatus = toolStatus;
	}

	public Long getToolDeptId() {
		return toolDeptId;
	}

	public void setToolDeptId(Long toolDeptId) {
		this.toolDeptId = toolDeptId;
	}

	public Date getToolTestDate() {
		return toolTestDate;
	}

	public void setToolTestDate(Date toolTestDate) {
		this.toolTestDate = toolTestDate;
	}

	public Date getToolRejectDate() {
		return toolRejectDate;
	}

	public void setToolRejectDate(Date toolRejectDate) {
		this.toolRejectDate = toolRejectDate;
	}

	public Double getToolTestDateCircle() {
		return toolTestDateCircle;
	}

	public void setToolTestDateCircle(Double toolTestDateCircle) {
		this.toolTestDateCircle = toolTestDateCircle;
	}

	public String getToolRemark() {
		return toolRemark;
	}

	public void setToolRemark(String toolRemark) {
		this.toolRemark = toolRemark == null ? null : toolRemark.trim();
	}

	public Date getToolNextTestDate() {
		return toolNextTestDate;
	}

	public void setToolNextTestDate(Date toolNextTestDate) {
		this.toolNextTestDate = toolNextTestDate;
	}

	public Long getBaseToolId() {
		return baseToolId;
	}

	public void setBaseToolId(Long baseToolId) {
		this.baseToolId = baseToolId;
	}
}