package com.gqj.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gqj.entity.Tool;

public interface ToolMapper {
	int deleteByPrimaryKeys(Tool tool);

	List<Map<String, Object>> selectToolsForPage(
			HashMap<String, Object> param);

	Tool selectToolForObject(Tool tool);

	List<Tool> selectToolsForList(Tool tool);

	int selectCountOfToolsForPage(
			HashMap<String, Object> param);

	int insertSelective(Tool tool);

	int updateByPrimaryKeySelective(Tool tool);

	int updateToolByBatch(Tool tool);
}