package com.gqj.service;

import java.util.Map;

import com.gqj.entity.BaseTool;

public interface IBaseToolService {
	Map<String, Object> deleteBaseTools(BaseTool baseTool);

	Map<String, Object> addNewBaseTool(BaseTool baseTool);

	Map<String, Object> updateBaseTool(BaseTool baseTool);

	Map<String, Object> selectBaseToolsForPage(
			BaseTool baseTool);

}
