package com.bpbj.dao;

import java.util.List;
import java.util.Map;

import com.bpbj.entity.Manufacturer;

public interface BPBJManufacturerMapper {
	List<Map<String, Object>> selectManufacturersForPage(
			Manufacturer manufacturer);

	int selectCountOfManufacturersForPage(
			Manufacturer manufacturer);

	int deleteByPrimaryKeys(Manufacturer manufacturer);

	int insertSelective(Manufacturer manufacturer);

	int updateByPrimaryKeySelective(
			Manufacturer manufacturer);

	List<Map<String, Object>> selectManufacturersForList(
			Manufacturer manufacturer);
}