package com.bpbj.entity;

import java.util.Date;

import com.base.entity.Base;

public class MaterialBill extends Base {
	private String billCode;

	private Date billConfirmTime;

	private Long billConfirmUserId;

	private Date billCreateTime;

	private Long billCreateUserId;

	private Long billDeptId;

	private Long billId;

	private String billRemark;

	private Long billTakeDeptId;

	private Date billTakeTime;

	private Long billTakeUserId;

	private Long billType;

	private Long planId;

	private Long storeId;

	public String getBillCode() {
		return billCode;
	}

	public Date getBillConfirmTime() {
		return billConfirmTime;
	}

	public Long getBillConfirmUserId() {
		return billConfirmUserId;
	}

	public Date getBillCreateTime() {
		return billCreateTime;
	}

	public Long getBillCreateUserId() {
		return billCreateUserId;
	}

	public Long getBillDeptId() {
		return billDeptId;
	}

	public Long getBillId() {
		return billId;
	}

	public String getBillRemark() {
		return billRemark;
	}

	public Long getBillTakeDeptId() {
		return billTakeDeptId;
	}

	public Date getBillTakeTime() {
		return billTakeTime;
	}

	public Long getBillTakeUserId() {
		return billTakeUserId;
	}

	public Long getBillType() {
		return billType;
	}

	public Long getPlanId() {
		return planId;
	}

	public Long getStoreId() {
		return storeId;
	}

	public void setBillCode(String billCode) {
		this.billCode = billCode == null ? null
				: billCode.trim();
	}

	public void setBillConfirmTime(Date billConfirmTime) {
		this.billConfirmTime = billConfirmTime;
	}

	public void setBillConfirmUserId(
			Long billConfirmUserId) {
		this.billConfirmUserId = billConfirmUserId;
	}

	public void setBillCreateTime(Date billCreateTime) {
		this.billCreateTime = billCreateTime;
	}

	public void setBillCreateUserId(Long billCreateUserId) {
		this.billCreateUserId = billCreateUserId;
	}

	public void setBillDeptId(Long billDeptId) {
		this.billDeptId = billDeptId;
	}

	public void setBillId(Long billId) {
		this.billId = billId;
	}

	public void setBillRemark(String billRemark) {
		this.billRemark = billRemark == null ? null
				: billRemark.trim();
	}

	public void setBillTakeDeptId(Long billTakeDeptId) {
		this.billTakeDeptId = billTakeDeptId;
	}

	public void setBillTakeTime(Date billTakeTime) {
		this.billTakeTime = billTakeTime;
	}

	public void setBillTakeUserId(Long billTakeUserId) {
		this.billTakeUserId = billTakeUserId;
	}

	public void setBillType(Long billType) {
		this.billType = billType;
	}

	public void setPlanId(Long planId) {
		this.planId = planId;
	}

	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}
}