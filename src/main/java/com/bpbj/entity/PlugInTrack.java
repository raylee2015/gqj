package com.bpbj.entity;

import com.base.entity.Base;

public class PlugInTrack extends Base {
	private Long batchId;

	private Long posId;

	private Long storeId;

	private Long plugInId;

	private Long plugInStatus;

	private Long trackId;

	public Long getBatchId() {
		return batchId;
	}

	public void setBatchId(Long batchId) {
		this.batchId = batchId;
	}

	public Long getPosId() {
		return posId;
	}

	public void setPosId(Long posId) {
		this.posId = posId;
	}

	public Long getStoreId() {
		return storeId;
	}

	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}

	public Long getPlugInId() {
		return plugInId;
	}

	public void setPlugInId(Long plugInId) {
		this.plugInId = plugInId;
	}

	public Long getPlugInStatus() {
		return plugInStatus;
	}

	public void setPlugInStatus(Long plugInStatus) {
		this.plugInStatus = plugInStatus;
	}

	public Long getTrackId() {
		return trackId;
	}

	public void setTrackId(Long trackId) {
		this.trackId = trackId;
	}

}