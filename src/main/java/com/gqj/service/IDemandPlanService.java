package com.gqj.service;

import java.util.Map;

import com.gqj.entity.DemandPlan;

public interface IDemandPlanService {
	Map<String, Object> deleteDemandPlansAndDetails(
			DemandPlan demandPlan);

	Map<String, Object> addDemandPlansAndDetails(
			DemandPlan demandPlan, String toolIds,
			String toolAmounts);

	Map<String, Object> updateDemandPlansAndDetails(
			DemandPlan demandPlan, String toolIds,
			String toolAmounts);

	Map<String, Object> selectDemandPlansForPage(
			DemandPlan demandPlan);

	Map<String, Object> updateDemandPlan(DemandPlan demandPlan);

}
