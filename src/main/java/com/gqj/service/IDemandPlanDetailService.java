package com.gqj.service;

import java.util.List;
import java.util.Map;

import com.gqj.entity.DemandPlan;
import com.gqj.entity.DemandPlanDetail;

public interface IDemandPlanDetailService {
	int deleteByDemandPlan(DemandPlan demandPlan);

	int addDemandPlanDetails(long demandPlanId,
			String toolIds, String toolAmounts);

	Map<String, Object> selectDemandPlanDetailsForPage(
			DemandPlanDetail demandPlanDetail);

	Map<String, Object> selectDemandPlanDetailsForList(
			DemandPlan demandPlan);

	List<Map<String, Object>> selectSumDemandPlanDetailsForList(
			DemandPlan demandPlan);

	int addDemandPlanDetails(long demandPlanId, String toolIds,
			String toolAmounts, String toolSumAmounts);

}
