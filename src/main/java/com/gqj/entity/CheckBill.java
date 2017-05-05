package com.gqj.entity;

import java.util.Date;

import com.base.entity.Base;

public class CheckBill extends Base {
	private String billCode;

	private Date billCreateTime;

	private Long billCreateUserId;

	private Long billDeptId;

	private Long billId;

	private Long billStatus;

	private String billRemark;

	public Long getBillStatus() {
		return billStatus;
	}

	public void setBillStatus(Long billStatus) {
		this.billStatus = billStatus;
	}

	public String getBillCode() {
		return billCode;
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

	public void setBillCode(String billCode) {
		this.billCode = billCode == null ? null : billCode.trim();
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
		this.billRemark = billRemark == null ? null : billRemark.trim();
	}

}