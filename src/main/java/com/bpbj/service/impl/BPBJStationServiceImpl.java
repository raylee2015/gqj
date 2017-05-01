package com.bpbj.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bpbj.dao.BPBJStationMapper;
import com.bpbj.entity.Station;
import com.bpbj.service.IBPBJStationService;

@Service
public class BPBJStationServiceImpl implements IBPBJStationService {

	@Autowired
	private BPBJStationMapper stationMapper;

	@Override
	public Map<String, Object> deleteStations(
			Station station) {
		Map<String, Object> map = new HashMap<String, Object>();
		int bool = stationMapper
				.deleteByPrimaryKeys(station);
		if (bool == 0) {
			map.put("success", false);
			map.put("msg", "删除失败，请联系管理员");
		} else {
			map.put("success", true);
			map.put("msg", "删除成功");
		}
		return map;
	}

	@Override
	public Map<String, Object> addNewStation(
			Station station) {
		Map<String, Object> map = new HashMap<String, Object>();
		int bool = stationMapper.insertSelective(station);
		if (bool == 0) {
			map.put("success", false);
			map.put("msg", "保存出错，请联系管理员");
		} else {
			map.put("success", true);
			map.put("msg", "保存成功");
		}
		return map;
	}

	@Override
	public Map<String, Object> selectStationsForPage(
			Station station) {
		List<Map<String, Object>> stations = stationMapper
				.selectStationsForPage(station);
		int count = stationMapper
				.selectCountOfStationsForPage(station);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", stations);
		map.put("total", count);
		return map;
	}

	@Override
	public Map<String, Object> updateStation(
			Station station) {
		Map<String, Object> map = new HashMap<String, Object>();
		int bool = stationMapper
				.updateByPrimaryKeySelective(station);
		if (bool == 0) {
			map.put("success", false);
			map.put("msg", "保存出错，请联系管理员");
		} else {
			map.put("success", true);
			map.put("msg", "保存成功");
		}
		return map;
	}

}
