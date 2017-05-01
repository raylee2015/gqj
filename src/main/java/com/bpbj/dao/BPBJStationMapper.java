package com.bpbj.dao;

import java.util.List;
import java.util.Map;

import com.bpbj.entity.Station;

public interface BPBJStationMapper {
	int deleteByPrimaryKeys(Station Station);

	List<Map<String, Object>> selectStationsForPage(Station Station);

	int selectCountOfStationsForPage(Station Station);

	int insertSelective(Station Station);

	int updateByPrimaryKeySelective(Station Station);
}