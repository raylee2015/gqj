package com.gqj.entity;

import java.util.Date;

import com.base.entity.Base;

public class ToolTrack extends Base {
	private Long baseToolId;

	private String baseToolManufacturerName;

	private String baseToolModel;
	private String baseToolName;

	private String baseToolSpec;

	private Long baseToolTypeId;

	private String baseToolTypeName;

	private String batchCode;

	private Long batchId;

	private Long posId;

	private String posName;

	private Long storeId;

	private String storeName;

	private String toolBox;

	private String toolCode;

	private Long toolDeptId;

	private Long toolId;

	private Date toolNextTestDate;

	private Date toolRejectDate;

	private Long toolStatus;

	private Date toolTestDate;

	private double toolTestDateCircle;

	private Date trackCreateTime;

	private Long trackCreateUserId;

	private Long trackId;

	public Long getBaseToolId() {
		return baseToolId;
	}

	public String getBaseToolManufacturerName() {
		return baseToolManufacturerName;
	}

	public String getBaseToolModel() {
		return baseToolModel;
	}

	public String getBaseToolName() {
		return baseToolName;
	}

	public String getBaseToolSpec() {
		return baseToolSpec;
	}

	public Long getBaseToolTypeId() {
		return baseToolTypeId;
	}

	public String getBaseToolTypeName() {
		return baseToolTypeName;
	}

	public String getBatchCode() {
		return batchCode;
	}

	public Long getBatchId() {
		return batchId;
	}

	public Long getPosId() {
		return posId;
	}

	public String getPosName() {
		return posName;
	}

	public Long getStoreId() {
		return storeId;
	}

	public String getStoreName() {
		return storeName;
	}

	public String getToolBox() {
		return toolBox;
	}

	public String getToolCode() {
		return toolCode;
	}

	public Long getToolDeptId() {
		return toolDeptId;
	}

	public Long getToolId() {
		return toolId;
	}

	public Date getToolNextTestDate() {
		return toolNextTestDate;
	}

	public Date getToolRejectDate() {
		return toolRejectDate;
	}

	public Long getToolStatus() {
		return toolStatus;
	}

	public Date getToolTestDate() {
		return toolTestDate;
	}

	public double getToolTestDateCircle() {
		return toolTestDateCircle;
	}

	public Date getTrackCreateTime() {
		return trackCreateTime;
	}

	public Long getTrackCreateUserId() {
		return trackCreateUserId;
	}

	public Long getTrackId() {
		return trackId;
	}

	public void setBaseToolId(Long baseToolId) {
		this.baseToolId = baseToolId;
	}

	public void setBaseToolManufacturerName(
			String baseToolManufacturerName) {
		this.baseToolManufacturerName = baseToolManufacturerName;
	}

	public void setBaseToolModel(String baseToolModel) {
		this.baseToolModel = baseToolModel == null ? null
				: baseToolModel.trim();
	}

	public void setBaseToolName(String baseToolName) {
		this.baseToolName = baseToolName == null ? null
				: baseToolName.trim();
	}

	public void setBaseToolSpec(String baseToolSpec) {
		this.baseToolSpec = baseToolSpec == null ? null
				: baseToolSpec.trim();
	}

	public void setBaseToolTypeId(Long baseToolTypeId) {
		this.baseToolTypeId = baseToolTypeId;
	}

	public void setBaseToolTypeName(String baseToolTypeName) {
		this.baseToolTypeName = baseToolTypeName == null ? null
				: baseToolTypeName.trim();
	}

	public void setBatchCode(String batchCode) {
		this.batchCode = batchCode == null ? null : batchCode.trim();
	}

	public void setBatchId(Long batchId) {
		this.batchId = batchId;
	}

	public void setPosId(Long posId) {
		this.posId = posId;
	}

	public void setPosName(String posName) {
		this.posName = posName == null ? null : posName.trim();
	}

	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName == null ? null : storeName.trim();
	}

	public void setToolBox(String toolBox) {
		this.toolBox = toolBox == null ? null : toolBox.trim();
	}

	public void setToolCode(String toolCode) {
		this.toolCode = toolCode == null ? null : toolCode.trim();
	}

	public void setToolDeptId(Long toolDeptId) {
		this.toolDeptId = toolDeptId;
	}

	public void setToolId(Long toolId) {
		this.toolId = toolId;
	}

	public void setToolNextTestDate(Date toolNextTestDate) {
		this.toolNextTestDate = toolNextTestDate;
	}

	public void setToolRejectDate(Date toolRejectDate) {
		this.toolRejectDate = toolRejectDate;
	}

	public void setToolStatus(Long toolStatus) {
		this.toolStatus = toolStatus;
	}

	public void setToolTestDate(Date toolTestDate) {
		this.toolTestDate = toolTestDate;
	}

	public void setToolTestDateCircle(double toolTestDateCircle) {
		this.toolTestDateCircle = toolTestDateCircle;
	}

	public void setTrackCreateTime(Date trackCreateTime) {
		this.trackCreateTime = trackCreateTime;
	}

	public void setTrackCreateUserId(Long trackCreateUserId) {
		this.trackCreateUserId = trackCreateUserId;
	}

	public void setTrackId(Long trackId) {
		this.trackId = trackId;
	}
}