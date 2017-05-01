package com.bpbj.dao;

import java.util.List;
import java.util.Map;

import com.bpbj.entity.ToolType;

public interface BPBJToolTypeMapper {
	List<Map<String, Object>> selectToolTypesForPage(ToolType toolType);

	List<Map<String, Object>> selectToolTypesForList(ToolType toolType);

	int selectCountOfToolTypesForPage(ToolType toolType);

	int deleteByPrimaryKeys(ToolType toolType);

	int insertSelective(ToolType toolType);

	int updateByPrimaryKeySelective(ToolType toolType);

}