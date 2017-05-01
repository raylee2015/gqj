package com.bpbj.service;

import java.util.Map;

import com.bpbj.entity.ToolType;

public interface IBPBJToolTypeService {
	Map<String, Object> deleteToolTypes(
			ToolType toolType);

	Map<String, Object> addNewToolType(ToolType toolType);

	Map<String, Object> updateToolType(
			ToolType toolType);

	Map<String, Object> selectToolTypesForPage(
			ToolType toolType);

	String selectToolTypesForList(ToolType toolType);
}
