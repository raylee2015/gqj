package com.gqj.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gqj.dao.ManufacturerMapper;
import com.gqj.entity.Manufacturer;
import com.gqj.service.IManufacturerService;

@Service
public class ManufacturerServiceImpl
		implements IManufacturerService {

	@Autowired
	private ManufacturerMapper manufacturerMapper;

	@Override
	public int deleteByPrimaryKeys(
			Manufacturer manufacturer) {
		return manufacturerMapper
				.deleteByPrimaryKeys(manufacturer);
	}

	@Override
	public int insertSelective(Manufacturer manufacturer) {
		return manufacturerMapper
				.insertSelective(manufacturer);
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
	public int updateByPrimaryKeySelective(
			Manufacturer manufacturer) {
		return manufacturerMapper
				.updateByPrimaryKeySelective(manufacturer);
	}

}
