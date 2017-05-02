package com.bpbj.entity;

import com.base.entity.Base;

public class Zone extends Base {
	private Long zoneId;

	private Long stationId;

	private String zoneName;

	private Long zoneType;

	public Long getZoneType() {
		return zoneType;
	}

	public void setZoneType(Long zoneType) {
		this.zoneType = zoneType;
	}

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

	public String getZoneName() {
		return zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName == null ? null : zoneName.trim();
	}

}