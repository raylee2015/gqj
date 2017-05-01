package com.bpbj.service;

import java.util.List;
import java.util.Map;

import com.bpbj.entity.DemandPlan;
import com.bpbj.entity.DemandPlanDetail;

public interface IBPBJDemandPlanDetailService {
	int deleteByDemandPlan(DemandPlan demandPlan);

	int addDemandPlanDetails(long demandPlanId,
			String toolIds, String toolAmounts);

	Map<String, Object> selectDemandPlanDetailsForPage(
			DemandPlanDetail demandPlanDetail);

	Map<String, Object> selectDemandPlanDetailsForList(
			DemandPlan demandPlan,String opType);

	List<Map<String, Object>> selectSumDemandPlanDetailsForList(
			DemandPlan demandPlan);

	int addDemandPlanDetails(long demandPlanId, String toolIds,
			String toolAmounts, String toolSumAmounts);

}
