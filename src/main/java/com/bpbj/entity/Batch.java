package com.bpbj.entity;

import java.util.Date;

import com.base.entity.Base;

public class Batch extends Base {
	private Long batchId;

	private String batchCode;

	private Long batchType;

	private Long batchDeptId;

	private Long batchCreateUserId;

	private Date batchCreateTime;

	private Long batchConfirmUserId;

	private Date batchConfirmTime;

	private String batchRemark;

	private Long stationId;

	private Long zoneId;

	public Long getStationId() {
		return stationId;
	}

	public void setStationId(Long stationId) {
		this.stationId = stationId;
	}

	public Long getZoneId() {
		return zoneId;
	}

	public void setZoneId(Long zoneId) {
		this.zoneId = zoneId;
	}

	public Long getBatchId() {
		return batchId;
	}

	public void setBatchId(Long batchId) {
		this.batchId = batchId;
	}

	public String getBatchCode() {
		return batchCode;
	}

	public void setBatchCode(String batchCode) {
		this.batchCode = batchCode == null ? null : batchCode.trim();
	}

	public Long getBatchType() {
		return batchType;
	}

	public void setBatchType(Long batchType) {
		this.batchType = batchType;
	}

	public Long getBatchDeptId() {
		return batchDeptId;
	}

	public void setBatchDeptId(Long batchDeptId) {
		this.batchDeptId = batchDeptId;
	}

	public Long getBatchCreateUserId() {
		return batchCreateUserId;
	}

	public void setBatchCreateUserId(Long batchCreateUserId) {
		this.batchCreateUserId = batchCreateUserId;
	}

	public Date getBatchCreateTime() {
		return batchCreateTime;
	}

	public void setBatchCreateTime(Date batchCreateTime) {
		this.batchCreateTime = batchCreateTime;
	}

	public Long getBatchConfirmUserId() {
		return batchConfirmUserId;
	}

	public void setBatchConfirmUserId(Long batchConfirmUserId) {
		this.batchConfirmUserId = batchConfirmUserId;
	}

	public Date getBatchConfirmTime() {
		return batchConfirmTime;
	}

	public void setBatchConfirmTime(Date batchConfirmTime) {
		this.batchConfirmTime = batchConfirmTime;
	}

	public String getBatchRemark() {
		return batchRemark;
	}

	public void setBatchRemark(String batchRemark) {
		this.batchRemark = batchRemark == null ? null : batchRemark.trim();
	}
}