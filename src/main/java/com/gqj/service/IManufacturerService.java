package com.gqj.service;

import java.util.Map;

import com.gqj.entity.Manufacturer;

public interface IManufacturerService {
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
