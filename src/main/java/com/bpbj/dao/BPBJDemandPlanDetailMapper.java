package com.bpbj.dao;

import java.util.List;
import java.util.Map;

import com.bpbj.entity.DemandPlanDetail;
import com.bpbj.entity.DemandPlan;

public interface BPBJDemandPlanDetailMapper {
	List<Map<String, Object>> selectDemandPlanDetailsForList(
			DemandPlan demandPlan);

	List<Map<String, Object>> selectSumDemandPlanDetailsForList(
			DemandPlan demandPlan);

	List<Map<String, Object>> selectArriveToolListForDemandPlanDetails(
			DemandPlan demandPlan);

	List<Map<String, Object>> selectDemandPlanDetailsForPage(
			DemandPlanDetail demandPlanDetail);

	int selectCountOfDemandPlanDetailsForPage(
			DemandPlanDetail demandPlanDetail);

	int deleteByDemandPlan(DemandPlan demandPlan);

	int insertSelective(DemandPlanDetail demandPlanDetail);

	int updateByPrimaryKeySelective(
			DemandPlanDetail demandPlanDetail);
}