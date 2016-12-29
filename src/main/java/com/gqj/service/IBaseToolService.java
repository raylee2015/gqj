package com.gqj.service;

import java.util.Map;

import com.gqj.entity.BaseTool;

public interface IBaseToolService {
	int deleteByPrimaryKeys(BaseTool baseTool);

	int insertSelective(BaseTool baseTool);

	int updateByPrimaryKeySelective(BaseTool baseTool);

	Map<String, Object> selectBaseToolsForPage(
			BaseTool baseTool);

}
