package com.gqj.dao;

import java.util.List;
import java.util.Map;

import com.gqj.entity.BaseTool;

public interface BaseToolMapper {
	List<Map<String, Object>> selectBaseToolsForPage(
			BaseTool baseTool);

	int selectCountOfBaseToolsForPage(BaseTool baseTool);

	int deleteByPrimaryKeys(BaseTool baseTool);

	int insertSelective(BaseTool baseTool);

	int updateByPrimaryKeySelective(BaseTool baseTool);
}