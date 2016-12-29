package com.gqj.dao;

import java.util.List;
import java.util.Map;

import com.gqj.entity.Manufacturer;

public interface ManufacturerMapper {
	List<Map<String, Object>> selectManufacturersForPage(
			Manufacturer manufacturer);

	int selectCountOfManufacturersForPage(
			Manufacturer manufacturer);

	int deleteByPrimaryKeys(Manufacturer manufacturer);

	int insertSelective(Manufacturer manufacturer);

	int updateByPrimaryKeySelective(
			Manufacturer manufacturer);
}