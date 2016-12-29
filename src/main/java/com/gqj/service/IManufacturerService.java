package com.gqj.service;

import java.util.Map;

import com.gqj.entity.Manufacturer;

public interface IManufacturerService {
	int deleteByPrimaryKeys(Manufacturer manufacturer);

	int insertSelective(Manufacturer manufacturer);

	int updateByPrimaryKeySelective(
			Manufacturer manufacturer);

	Map<String, Object> selectManufacturersForPage(
			Manufacturer manufacturer);
}
