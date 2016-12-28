package com.gqj.service;

import java.util.Map;

import com.gqj.entity.ToolType;

public interface IToolTypeService {
	int deleteByPrimaryKeys(ToolType toolType);

	int insertSelective(ToolType toolType);

	int updateByPrimaryKeySelective(ToolType toolType);

	Map<String, Object> selectToolTypesForPage(ToolType toolType);

	String selectToolTypesForList(ToolType toolType);
}
