package com.gqj.service;

import java.util.Map;

import com.gqj.entity.ToolDemand;

public interface IToolDemandService {
	int deleteByPrimaryKeys(ToolDemand toolDemand);

	int insertSelective(ToolDemand toolDemand);

	int updateByPrimaryKeySelective(ToolDemand toolDemand);

	Map<String, Object> selectToolDemandsForPage(
			ToolDemand toolDemand);
}
