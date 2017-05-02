package com.bpbj.dao;

import java.util.List;
import java.util.Map;

import com.bpbj.entity.Zone;

public interface BPBJZoneMapper {
	int deleteByPrimaryKeys(Zone Zone);

	List<Map<String, Object>> selectZonesForPage(Zone Zone);

	int selectCountOfZonesForPage(Zone Zone);

	int insertSelective(Zone Zone);

	int updateByPrimaryKeySelective(Zone Zone);
}