package com.gqj.service;

import java.util.Map;

import com.base.admin.entity.User;
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

	Map<String, Object> updateDemandPlan(
			DemandPlan demandPlan);

	Map<String, Object> addAnnualDemandPlan(
			DemandPlan demandPlan, String deptIds,
			String deptNames, User createUser);

}
