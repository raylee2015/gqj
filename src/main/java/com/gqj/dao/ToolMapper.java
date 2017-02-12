package com.gqj.dao;

import java.util.List;
import java.util.Map;

import com.gqj.entity.Tool;

public interface ToolMapper {
	int deleteByPrimaryKeys(Tool tool);

	List<Map<String, Object>> selectToolsForPage(Tool tool);

	Tool selectToolForObject(Tool tool);

	List<Tool> selectToolsForList(Tool tool);

	int selectCountOfToolsForPage(Tool tool);

	int insertSelective(Tool tool);

	int updateByPrimaryKeySelective(Tool tool);

	int updateToolByBatch(Tool tool);
}