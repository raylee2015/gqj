package com.bpbj.service;

import java.util.Map;

import com.bpbj.entity.BaseTool;

public interface IBPBJBaseToolService {
	Map<String, Object> deleteBaseTools(BaseTool baseTool);

	Map<String, Object> addNewBaseTool(BaseTool baseTool);

	Map<String, Object> updateBaseTool(BaseTool baseTool);

	Map<String, Object> selectBaseToolsForPage(BaseTool baseTool);

	Map<String, Object> selectBaseToolForObject(BaseTool baseTool);

}
