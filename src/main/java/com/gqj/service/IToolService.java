package com.gqj.service;

import java.util.Map;

import com.gqj.entity.Tool;

public interface IToolService {
	Map<String, Object> deleteTools(Tool tool);

	Map<String, Object> addNewTool(Tool tool);

	Map<String, Object> updateTool(Tool tool);

	Map<String, Object> selectToolsForPage(Tool tool);

	Map<String, Object> selectToolsForObject(Tool tool);

}
