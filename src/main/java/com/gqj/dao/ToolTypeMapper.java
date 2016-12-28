package com.gqj.dao;

import java.util.List;
import java.util.Map;

import com.gqj.entity.ToolType;

public interface ToolTypeMapper {
	List<Map<String, Object>> selectToolTypesForPage(ToolType toolType);

	List<Map<String, Object>> selectToolTypesForList(ToolType toolType);

	int selectCountOfToolTypesForPage(ToolType toolType);

	int deleteByPrimaryKeys(ToolType toolType);

	int insertSelective(ToolType toolType);

	int updateByPrimaryKeySelective(ToolType toolType);

}