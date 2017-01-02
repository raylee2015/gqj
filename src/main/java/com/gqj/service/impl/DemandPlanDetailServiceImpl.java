package com.gqj.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.util.BaseUtil;
import com.gqj.dao.DemandPlanDetailMapper;
import com.gqj.entity.DemandPlan;
import com.gqj.entity.DemandPlanDetail;
import com.gqj.service.IDemandPlanDetailService;

@Service
public class DemandPlanDetailServiceImpl
		implements IDemandPlanDetailService {

	@Autowired
	private DemandPlanDetailMapper demandPlanDetailMapper;

	@Override
	public int deleteByDemandPlan(DemandPlan demandPlan) {
		return demandPlanDetailMapper
				.deleteByDemandPlan(demandPlan);
	}

	@Override
	public int addDemandPlanDetails(long demandPlanId,
			String toolIds, String toolAmounts) {
		String[] toolId_arr = toolIds.split(",");
		String[] toolAmount_arr = toolAmounts.split(",");
		int bool = 0;
		for (int i = 0; i < toolId_arr.length; i++) {
			DemandPlanDetail demandPlanDetail = new DemandPlanDetail();
			demandPlanDetail.setDetailId(-1l);
			demandPlanDetail.setPlanId(demandPlanId);
			demandPlanDetail.setToolId(
					BaseUtil.strToLong(toolId_arr[i]));
			demandPlanDetail.setToolAmount(
					BaseUtil.strToLong(toolAmount_arr[i]));
			bool = demandPlanDetailMapper
					.insertSelective(demandPlanDetail);
		}
		return bool;
	}

	@Override
	public Map<String, Object> selectDemandPlanDetailsForPage(
			DemandPlanDetail demandPlan) {
		List<Map<String, Object>> demandPlans = demandPlanDetailMapper
				.selectDemandPlanDetailsForPage(demandPlan);
		int count = demandPlanDetailMapper
				.selectCountOfDemandPlanDetailsForPage(
						demandPlan);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", demandPlans);
		map.put("total", count);
		return map;
	}

	@Override
	public Map<String, Object> selectDemandPlanDetailsForList(
			DemandPlan demandPlan) {
		List<Map<String, Object>> demandPlanDetails = demandPlanDetailMapper
				.selectDemandPlanDetailsForList(demandPlan);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", demandPlanDetails);
		map.put("total", demandPlanDetails.size());
		return map;
	}

}
