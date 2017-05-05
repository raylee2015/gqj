package com.gqj.entity;

import com.base.entity.Base;

public class CheckBillDetail extends Base {
	private Long detailId;

	private Long baseToolId;

	private Long billId;
	private Long baseToolNormalFlag;

	private String baseToolCode;

	private String baseToolRemark;

	private Long baseToolStatus;

	public Long getBaseToolNormalFlag() {
		return baseToolNormalFlag;
	}

	public void setBaseToolNormalFlag(Long baseToolNormalFlag) {
		this.baseToolNormalFlag = baseToolNormalFlag;
	}

	public String getBaseToolRemark() {
		return baseToolRemark;
	}

	public void setBaseToolRemark(String baseToolRemark) {
		this.baseToolRemark = baseToolRemark;
	}

	public String getBaseToolCode() {
		return baseToolCode;
	}

	public void setBaseToolCode(String baseToolCode) {
		this.baseToolCode = baseToolCode;
	}

	public Long getDetailId() {
		return detailId;
	}

	public void setDetailId(Long detailId) {
		this.detailId = detailId;
	}

	public Long getBaseToolId() {
		return baseToolId;
	}

	public void setBaseToolId(Long baseToolId) {
		this.baseToolId = baseToolId;
	}

	public Long getBillId() {
		return billId;
	}

	public void setBillId(Long billId) {
		this.billId = billId;
	}

	public Long getBaseToolStatus() {
		return baseToolStatus;
	}

	public void setBaseToolStatus(Long baseToolStatus) {
		this.baseToolStatus = baseToolStatus;
	}

}