package com.bpbj.service;

import java.util.Map;

import com.bpbj.entity.Manufacturer;

public interface IBPBJManufacturerService {
	Map<String, Object> deleteManufacturers(
			Manufacturer manufacturer);

	Map<String, Object> addNewManufacturer(
			Manufacturer manufacturer);

	Map<String, Object> updateManufacturer(
			Manufacturer manufacturer);

	Map<String, Object> selectManufacturersForPage(
			Manufacturer manufacturer);

	String selectManufacturersForList(
			Manufacturer manufacturer);
}
