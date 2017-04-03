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
			String toolIds, String toolAmounts,
			String toolSumAmounts) {
		String[] toolId_arr = toolIds.split(",");
		String[] toolAmount_arr = toolAmounts.split(",");
		String[] toolSumAmount_arr = toolSumAmounts
				.split(",");
		int bool = 0;
		for (int i = 0; i < toolId_arr.length; i++) {
			DemandPlanDetail demandPlanDetail = new DemandPlanDetail();
			demandPlanDetail.setDetailId(-1l);
			demandPlanDetail.setPlanId(demandPlanId);
			demandPlanDetail.setToolId(
					BaseUtil.strToLong(toolId_arr[i]));
			if (!"".equals(toolAmounts)) {
				demandPlanDetail.setToolAmount(BaseUtil
						.strToLong(toolAmount_arr[i]));
			}
			if (!"".equals(toolSumAmounts)) {
				demandPlanDetail.setToolSumAmount(BaseUtil
						.strToLong(toolSumAmount_arr[i]));
			}
			bool = demandPlanDetailMapper
					.insertSelective(demandPlanDetail);
		}
		return bool;
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
	public List<Map<String, Object>> selectSumDemandPlanDetailsForList(
			DemandPlan demandPlan) {
		List<Map<String, Object>> demandPlans = demandPlanDetailMapper
				.selectSumDemandPlanDetailsForList(
						demandPlan);
		return demandPlans;
	}

	@Override
	public Map<String, Object> selectDemandPlanDetailsForList(
			DemandPlan demandPlan, String opType) {
		List<Map<String, Object>> demandPlanDetails = demandPlanDetailMapper
				.selectDemandPlanDetailsForList(demandPlan);
		if ("AUDIT_BY_DEPT".equals(opType)) {
			List<Map<String, Object>> arriveToolList = demandPlanDetailMapper
					.selectArriveToolListForDemandPlanDetails(
							demandPlan);
			for (Map<String, Object> item : demandPlanDetails) {
				for (Map<String, Object> tool : arriveToolList) {
					if (item.get("TOOL_ID").toString()
							.equals(tool.get("TOOL_ID")
									.toString())) {
						item.put("TOOL_ARRIVE_AMOUNT",
								tool.get(
										"TOOL_ARRIVE_AMOUNT")
										.toString());
						break;
					}
				}
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", demandPlanDetails);
		map.put("total", demandPlanDetails.size());
		return map;
	}

}
