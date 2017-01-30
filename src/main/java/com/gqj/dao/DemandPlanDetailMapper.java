package com.gqj.dao;

import java.util.List;
import java.util.Map;

import com.gqj.entity.DemandPlanDetail;
import com.gqj.entity.DemandPlan;

public interface DemandPlanDetailMapper {
	List<Map<String, Object>> selectDemandPlanDetailsForList(
			DemandPlan demandPlan);

	List<Map<String, Object>> selectSumDemandPlanDetailsForList(
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