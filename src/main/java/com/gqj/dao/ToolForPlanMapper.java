package com.gqj.dao;

import java.util.List;
import java.util.Map;

import com.gqj.entity.ToolForPlan;

public interface ToolForPlanMapper {
	List<Map<String, Object>> selectToolForPlansForPage(
			ToolForPlan toolForPlan);

	int selectCountOfToolForPlansForPage(ToolForPlan toolForPlan);

	int deleteByPrimaryKeys(ToolForPlan toolForPlan);

	int insertSelective(ToolForPlan toolForPlan);

	int updateByPrimaryKeySelective(ToolForPlan toolForPlan);
}