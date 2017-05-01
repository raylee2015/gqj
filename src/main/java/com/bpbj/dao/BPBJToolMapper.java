package com.bpbj.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bpbj.entity.Tool;

public interface BPBJToolMapper {
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