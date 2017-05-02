package com.bpbj.service;

import java.util.Map;

import com.bpbj.entity.Zone;

public interface IBPBJZoneService {
	Map<String, Object> deleteZones(Zone zone);

	Map<String, Object> addNewZone(Zone zone);

	Map<String, Object> updateZone(Zone zone);

	Map<String, Object> selectZonesForPage(Zone zone);

}
