package com.bpbj.entity;

import com.base.entity.Base;

public class Station extends Base {
	private Long stationId;

	private String stationName;

	public Long getStationId() {
		return stationId;
	}

	public void setStationId(Long stationId) {
		this.stationId = stationId;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName == null ? null
				: stationName.trim();
	}

}