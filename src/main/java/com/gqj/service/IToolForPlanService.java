package com.gqj.service;

import java.util.Map;

import com.gqj.entity.ToolForPlan;

public interface IToolForPlanService {
	int deleteByPrimaryKeys(ToolForPlan toolForPlan);

	int insertSelective(ToolForPlan toolForPlan);

	int updateByPrimaryKeySelective(ToolForPlan toolForPlan);

	Map<String, Object> selectToolForPlansForPage(
			ToolForPlan toolForPlan);
}
