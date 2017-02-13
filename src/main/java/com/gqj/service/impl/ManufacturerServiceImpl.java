package com.gqj.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gqj.dao.ManufacturerMapper;
import com.gqj.entity.Manufacturer;
import com.gqj.service.IManufacturerService;

import net.sf.json.JSONArray;

@Service
public class ManufacturerServiceImpl
		implements IManufacturerService {

	@Autowired
	private ManufacturerMapper manufacturerMapper;

	@Override
	public Map<String, Object> deleteManufacturers(
			Manufacturer manufacturer) {
		Map<String, Object> map = new HashMap<String, Object>();
		int bool = manufacturerMapper
				.deleteByPrimaryKeys(manufacturer);
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
	public Map<String, Object> addNewManufacturer(
			Manufacturer manufacturer) {
		Map<String, Object> map = new HashMap<String, Object>();
		int bool = manufacturerMapper
				.insertSelective(manufacturer);
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
	public Map<String, Object> selectManufacturersForPage(
			Manufacturer manufacturer) {
		List<Map<String, Object>> manufacturers = manufacturerMapper
				.selectManufacturersForPage(manufacturer);
		int count = manufacturerMapper
				.selectCountOfManufacturersForPage(
						manufacturer);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", manufacturers);
		map.put("total", count);
		return map;
	}

	@Override
	public String selectManufacturersForList(
			Manufacturer manufacturer) {
		List<Map<String, Object>> manufacturers = manufacturerMapper
				.selectManufacturersForList(manufacturer);
		String result = "";
		if (manufacturers != null
				&& manufacturers.size() != 0) {
			result = JSONArray.fromObject(manufacturers)
					.toString();
		}
		return result;
	}

	@Override
	public Map<String, Object> updateManufacturer(
			Manufacturer manufacturer) {
		Map<String, Object> map = new HashMap<String, Object>();
		int bool = manufacturerMapper
				.updateByPrimaryKeySelective(manufacturer);
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
