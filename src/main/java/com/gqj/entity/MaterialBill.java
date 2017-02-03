package com.gqj.entity;

import java.util.Date;

import com.base.entity.Base;

public class MaterialBill extends Base {
	private Long billId;

	private Long storeId;

	private String billCode;

	private Long billType;

	private Long billCreateUserId;

	private Long billDeptId;

	private Date billCreateTime;

	private Long billConfirmUserId;

	private Date billConfirmTime;

	private Long billTakeUserId;

	private Long billTakeDeptId;

	private Date billTakeTime;

	private String billRemark;

	public Long getBillId() {
		return billId;
	}

	public void setBillId(Long billId) {
		this.billId = billId;
	}

	public Long getStoreId() {
		return storeId;
	}

	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}

	public String getBillCode() {
		return billCode;
	}

	public void setBillCode(String billCode) {
		this.billCode = billCode == null ? null
				: billCode.trim();
	}

	public Long getBillType() {
		return billType;
	}

	public void setBillType(Long billType) {
		this.billType = billType;
	}

	public Long getBillCreateUserId() {
		return billCreateUserId;
	}

	public void setBillCreateUserId(Long billCreateUserId) {
		this.billCreateUserId = billCreateUserId;
	}

	public Long getBillDeptId() {
		return billDeptId;
	}

	public void setBillDeptId(Long billDeptId) {
		this.billDeptId = billDeptId;
	}

	public Date getBillCreateTime() {
		return billCreateTime;
	}

	public void setBillCreateTime(Date billCreateTime) {
		this.billCreateTime = billCreateTime;
	}

	public Long getBillConfirmUserId() {
		return billConfirmUserId;
	}

	public void setBillConfirmUserId(
			Long billConfirmUserId) {
		this.billConfirmUserId = billConfirmUserId;
	}

	public Date getBillConfirmTime() {
		return billConfirmTime;
	}

	public void setBillConfirmTime(Date billConfirmTime) {
		this.billConfirmTime = billConfirmTime;
	}

	public Long getBillTakeUserId() {
		return billTakeUserId;
	}

	public void setBillTakeUserId(Long billTakeUserId) {
		this.billTakeUserId = billTakeUserId;
	}

	public Long getBillTakeDeptId() {
		return billTakeDeptId;
	}

	public void setBillTakeDeptId(Long billTakeDeptId) {
		this.billTakeDeptId = billTakeDeptId;
	}

	public Date getBillTakeTime() {
		return billTakeTime;
	}

	public void setBillTakeTime(Date billTakeTime) {
		this.billTakeTime = billTakeTime;
	}

	public String getBillRemark() {
		return billRemark;
	}

	public void setBillRemark(String billRemark) {
		this.billRemark = billRemark == null ? null
				: billRemark.trim();
	}
}