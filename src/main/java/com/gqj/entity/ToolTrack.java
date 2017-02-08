package com.gqj.entity;

import java.math.BigDecimal;
import java.util.Date;

public class ToolTrack {
	private Long trackId;

	private Long toolId;

	private Long storeId;

	private Long posId;

	private Long batchId;

	private Long baseToolId;

	private String toolBox;

	private Long toolStatus;

	private Long trackCreateUserId;

	private Date trackCreateTime;

	private Date toolTestDate;

	private Date toolRejectDate;

	private double toolTestDateCircle;

	private Date toolNextTestDate;

	private String toolCode;

	private String storeName;

	private String posName;

	private String batchCode;

	private String baseToolName;

	private Long baseToolTypeId;

	private String baseToolTypeName;

	private String baseToolModel;

	private String baseToolSpec;

	public Long getTrackId() {
		return trackId;
	}

	public void setTrackId(Long trackId) {
		this.trackId = trackId;
	}

	public Long getToolId() {
		return toolId;
	}

	public void setToolId(Long toolId) {
		this.toolId = toolId;
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

	public Long getBaseToolId() {
		return baseToolId;
	}

	public void setBaseToolId(Long baseToolId) {
		this.baseToolId = baseToolId;
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

	public Long getTrackCreateUserId() {
		return trackCreateUserId;
	}

	public void setTrackCreateUserId(Long trackCreateUserId) {
		this.trackCreateUserId = trackCreateUserId;
	}

	public Date getTrackCreateTime() {
		return trackCreateTime;
	}

	public void setTrackCreateTime(Date trackCreateTime) {
		this.trackCreateTime = trackCreateTime;
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

	public double getToolTestDateCircle() {
		return toolTestDateCircle;
	}

	public void setToolTestDateCircle(double toolTestDateCircle) {
		this.toolTestDateCircle = toolTestDateCircle;
	}

	public Date getToolNextTestDate() {
		return toolNextTestDate;
	}

	public void setToolNextTestDate(Date toolNextTestDate) {
		this.toolNextTestDate = toolNextTestDate;
	}

	public String getToolCode() {
		return toolCode;
	}

	public void setToolCode(String toolCode) {
		this.toolCode = toolCode == null ? null : toolCode.trim();
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName == null ? null : storeName.trim();
	}

	public String getPosName() {
		return posName;
	}

	public void setPosName(String posName) {
		this.posName = posName == null ? null : posName.trim();
	}

	public String getBatchCode() {
		return batchCode;
	}

	public void setBatchCode(String batchCode) {
		this.batchCode = batchCode == null ? null : batchCode.trim();
	}

	public String getBaseToolName() {
		return baseToolName;
	}

	public void setBaseToolName(String baseToolName) {
		this.baseToolName = baseToolName == null ? null
				: baseToolName.trim();
	}

	public Long getBaseToolTypeId() {
		return baseToolTypeId;
	}

	public void setBaseToolTypeId(Long baseToolTypeId) {
		this.baseToolTypeId = baseToolTypeId;
	}

	public String getBaseToolTypeName() {
		return baseToolTypeName;
	}

	public void setBaseToolTypeName(String baseToolTypeName) {
		this.baseToolTypeName = baseToolTypeName == null ? null
				: baseToolTypeName.trim();
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
		this.baseToolSpec = baseToolSpec == null ? null
				: baseToolSpec.trim();
	}
}