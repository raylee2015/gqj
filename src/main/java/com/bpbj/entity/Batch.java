package com.bpbj.entity;

import java.util.Date;

import com.base.entity.Base;

public class Batch extends Base {
	private Long batchId;

	private String batchCode;

	private Long batchType;

	private Long batchCount;

	private Long batchDeptId;

	private Long batchCreateUserId;
	private Long batchReturnUserId;

	private Date batchCreateTime;

	private Long batchConfirmUserId;

	private Date batchConfirmTime;

	private Long batchTakeDeptId;

	private Long batchTakeUserId;

	private Date batchTakeTime;

	private String batchRemark;

	public Long getBatchReturnUserId() {
		return batchReturnUserId;
	}

	public void setBatchReturnUserId(Long batchReturnUserId) {
		this.batchReturnUserId = batchReturnUserId;
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

	public Long getBatchCount() {
		return batchCount;
	}

	public void setBatchCount(Long batchCount) {
		this.batchCount = batchCount;
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

	public Long getBatchTakeDeptId() {
		return batchTakeDeptId;
	}

	public void setBatchTakeDeptId(Long batchTakeDeptId) {
		this.batchTakeDeptId = batchTakeDeptId;
	}

	public Long getBatchTakeUserId() {
		return batchTakeUserId;
	}

	public void setBatchTakeUserId(Long batchTakeUserId) {
		this.batchTakeUserId = batchTakeUserId;
	}

	public Date getBatchTakeTime() {
		return batchTakeTime;
	}

	public void setBatchTakeTime(Date batchTakeTime) {
		this.batchTakeTime = batchTakeTime;
	}

	public String getBatchRemark() {
		return batchRemark;
	}

	public void setBatchRemark(String batchRemark) {
		this.batchRemark = batchRemark == null ? null
				: batchRemark.trim();
	}
}