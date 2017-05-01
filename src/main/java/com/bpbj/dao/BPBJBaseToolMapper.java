package com.bpbj.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bpbj.entity.BaseTool;

public interface BPBJBaseToolMapper {
	List<Map<String, Object>> selectBaseToolsForPage(
			BaseTool baseTool);

	Map<String, Object> selectBaseToolForObject(
			BaseTool baseTool);

	int selectCountOfBaseToolsForPage(BaseTool baseTool);

	int deleteByPrimaryKeys(BaseTool baseTool);

	int insertSelective(BaseTool baseTool);

	int updateByPrimaryKeySelective(BaseTool baseTool);

	List<Map<String, Object>> selectBaseToolsForPageWithMap(
			HashMap<String, Object> param);

	int selectCountOfBaseToolsForPageWithMap(
			HashMap<String, Object> param);
}