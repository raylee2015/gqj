package com.bpbj.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bpbj.dao.BPBJZoneMapper;
import com.bpbj.entity.Zone;
import com.bpbj.service.IBPBJZoneService;

@Service
public class BPBJZoneServiceImpl implements IBPBJZoneService {

	@Autowired
	private BPBJZoneMapper zoneMapper;

	@Override
	public Map<String, Object> deleteZones(Zone zone) {
		Map<String, Object> map = new HashMap<String, Object>();
		int bool = zoneMapper.deleteByPrimaryKeys(zone);
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
	public Map<String, Object> addNewZone(Zone zone) {
		Map<String, Object> map = new HashMap<String, Object>();
		int bool = zoneMapper.insertSelective(zone);
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
	public Map<String, Object> selectZonesForPage(Zone zone) {
		List<Map<String, Object>> zones = zoneMapper.selectZonesForPage(zone);
		int count = zoneMapper.selectCountOfZonesForPage(zone);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", zones);
		map.put("total", count);
		return map;
	}

	@Override
	public Map<String, Object> updateZone(Zone zone) {
		Map<String, Object> map = new HashMap<String, Object>();
		int bool = zoneMapper.updateByPrimaryKeySelective(zone);
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
