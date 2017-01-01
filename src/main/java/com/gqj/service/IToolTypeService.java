package com.gqj.service;

import java.util.Map;

import com.gqj.entity.ToolType;

public interface IToolTypeService {
	Map<String, Object> deleteToolTypes(
			ToolType toolType);

	Map<String, Object> addNewToolType(ToolType toolType);

	Map<String, Object> updateToolType(
			ToolType toolType);

	Map<String, Object> selectToolTypesForPage(
			ToolType toolType);

	String selectToolTypesForList(ToolType toolType);
}
