package com.gqj.dao;

import java.util.List;
import java.util.Map;

import com.gqj.entity.DemandPlan;

public interface DemandPlanMapper {
	int deleteByPrimaryKeys(DemandPlan demandPlan);

	int insertSelective(DemandPlan demandPlan);

	int updateByPrimaryKeySelective(DemandPlan demandPlan);

	List<Map<String, Object>> selectDemandPlansForPage(
			DemandPlan demandPlan);

	int selectCountOfDemandPlansForPage(
			DemandPlan demandPlan);
}