package com.bpbj.entity;

import java.util.Date;

import com.base.entity.Base;

public class PlugIn extends Base {
	private Long plugInId;

	private String plugInCode;

	private Long batchId;

	private Long storeId;

	private Long posId;

	private Long plugInStatus;

	private Long plugInDeptId;

	private String plugInRemark;

	private Date plugInManDate;

	private Long baseToolId;

	public Long getBatchId() {
		return batchId;
	}

	public void setBatchId(Long batchId) {
		this.batchId = batchId;
	}

	public Long getPlugInId() {
		return plugInId;
	}

	public void setPlugInId(Long plugInId) {
		this.plugInId = plugInId;
	}

	public String getPlugInCode() {
		return plugInCode;
	}

	public void setPlugInCode(String plugInCode) {
		this.plugInCode = plugInCode;
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

	public Long getPlugInStatus() {
		return plugInStatus;
	}

	public void setPlugInStatus(Long plugInStatus) {
		this.plugInStatus = plugInStatus;
	}

	public Long getPlugInDeptId() {
		return plugInDeptId;
	}

	public void setPlugInDeptId(Long plugInDeptId) {
		this.plugInDeptId = plugInDeptId;
	}

	public String getPlugInRemark() {
		return plugInRemark;
	}

	public void setPlugInRemark(String plugInRemark) {
		this.plugInRemark = plugInRemark;
	}

	public Date getPlugInManDate() {
		return plugInManDate;
	}

	public void setPlugInManDate(Date plugInManDate) {
		this.plugInManDate = plugInManDate;
	}

	public Long getBaseToolId() {
		return baseToolId;
	}

	public void setBaseToolId(Long baseToolId) {
		this.baseToolId = baseToolId;
	}

}