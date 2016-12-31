package com.gqj.dao;

import java.util.List;
import java.util.Map;

import com.gqj.entity.ToolDemand;

public interface ToolDemandMapper {
	List<Map<String, Object>> selectToolDemandsForPage(
			ToolDemand toolDemand);

	int selectCountOfToolDemandsForPage(
			ToolDemand toolDemand);

	int deleteByPrimaryKeys(ToolDemand toolDemand);

	int insertSelective(ToolDemand toolDemand);

	int updateByPrimaryKeySelective(ToolDemand toolDemand);
}