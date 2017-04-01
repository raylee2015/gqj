package com.gqj.service;

import java.io.IOException;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.base.admin.entity.User;
import com.gqj.entity.DemandPlan;

import net.sf.jxls.exception.ParsePropertyException;

public interface IDemandPlanService {
	Map<String, Object> deleteDemandPlansAndDetails(
			DemandPlan demandPlan);

	Map<String, Object> addDemandPlansAndDetails(DemandPlan demandPlan,
			String toolIds, String toolAmounts);

	Map<String, Object> updateDemandPlansAndDetails(
			DemandPlan demandPlan, String toolIds, String toolAmounts);

	Map<String, Object> selectDemandPlansForPage(DemandPlan demandPlan);

	Map<String, Object> updateDemandPlan(DemandPlan demandPlan);

	Map<String, Object> addAnnualDemandPlan(DemandPlan demandPlan,
			String deptIds, String deptNames, User createUser);

	Map<String, Object> totalToolForParentDemandPlan(
			DemandPlan demandPlan);

	Map<String, Object> exportDemandPlans(DemandPlan demandPlan)
			throws ParsePropertyException, InvalidFormatException,
			IOException;

	Map<String, Object> updateDemandPlansAndDetails(
			DemandPlan demandPlan, String toolIds, String toolAmounts,
			String toolSumAmounts);

}
