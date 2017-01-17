package com.gqj.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.admin.entity.User;
import com.base.util.BaseUtil;
import com.base.util.DateUtil;
import com.gqj.dao.DemandPlanMapper;
import com.gqj.entity.DemandPlan;
import com.gqj.service.IDemandPlanDetailService;
import com.gqj.service.IDemandPlanService;
import com.gqj.util.PlanStatus;

@Service
public class DemandPlanServiceImpl
		implements IDemandPlanService {

	@Autowired
	private DemandPlanMapper demandPlanMapper;

	@Autowired
	private IDemandPlanDetailService demandPlanDetailService;

	@Override
	public Map<String, Object> deleteDemandPlansAndDetails(
			DemandPlan demandPlan) {
		Map<String, Object> map = new HashMap<>();
		int bool = demandPlanDetailService
				.deleteByDemandPlan(demandPlan);
		bool = demandPlanMapper
				.deleteByPrimaryKeys(demandPlan);
		if (bool == 0) {
			map.put("success", false);
			map.put("msg", "删除失败，请联系管理员");
		} else {
			map.put("success", true);
			map.put("msg", "删除成功");
		}
		return map;
	}

	@Override
	public Map<String, Object> addDemandPlansAndDetails(
			DemandPlan demandPlan, String toolIds,
			String toolAmounts) {
		Map<String, Object> map = new HashMap<String, Object>();
		int bool = demandPlanMapper
				.insertSelective(demandPlan);
		bool = demandPlanDetailService.addDemandPlanDetails(
				demandPlan.getPlanId(), toolIds,
				toolAmounts);
		if (bool == 0) {
			map.put("success", false);
			map.put("msg", "保存出错，请联系管理员");
		} else {
			map.put("success", true);
			map.put("msg", "保存成功");
		}
		return map;
	}

	@Override
	public Map<String, Object> addAnnualDemandPlan(
			DemandPlan demandPlan, String deptIds,
			String deptNames, User createUser) {
		Map<String, Object> map = new HashMap<String, Object>();
		String[] deptId_arr = deptIds.split(",");
		int bool = 0;
		DemandPlan demandPlanParent = new DemandPlan();
		demandPlanParent
				.setPlanCode(demandPlan.getPlanCode());
		// 生成父计划
		demandPlanParent.setPlanAssignedDeptId(deptIds);
		demandPlanParent.setPlanAssignedDeptName(deptNames);
		demandPlanParent.setPlanCreateDate(new Date());
		demandPlanParent.setPlanCreateUserId(
				createUser.getUserId());
		demandPlanParent
				.setPlanDeptId(createUser.getUserDeptId());
		demandPlanParent.setPlanType(0L);
		bool = demandPlanMapper
				.insertSelective(demandPlanParent);
		// 生成子计划
		for (int i = 0; i < deptId_arr.length; i++) {
			DemandPlan demandPlanSub = new DemandPlan();
			demandPlanSub.setUpPlanId(
					demandPlanParent.getPlanId());
			demandPlanSub
					.setPlanCode(demandPlan.getPlanCode());
			demandPlanSub.setPlanAssignedDeptId(deptIds);
			demandPlanSub
					.setPlanAssignedDeptName(deptNames);
			demandPlanSub.setPlanDeptId(
					BaseUtil.strToLong(deptId_arr[i]));
			demandPlanSub.setPlanType(0L);
			demandPlanSub
					.setPlanStatus(PlanStatus.UNSUBMIT);
			demandPlanSub.setPlanCreateDate(new Date());
			bool = demandPlanMapper
					.insertSelective(demandPlanSub);
		}
		if (bool == 0) {
			map.put("success", false);
			map.put("msg", "保存出错，请联系管理员");
		} else {
			map.put("success", true);
			map.put("msg", "保存成功");
		}
		return map;
	}

	@Override
	public Map<String, Object> selectDemandPlansForPage(
			DemandPlan demandPlan) {
		List<Map<String, Object>> demandPlans = demandPlanMapper
				.selectDemandPlansForPage(demandPlan);
		// 转化日期
		for (Map<String, Object> item : demandPlans) {
			item.put("PLAN_CREATE_DATE",
					DateUtil.getDate(
							item.get("PLAN_CREATE_DATE")
									.toString()));
			if (item.get("UP_PLAN_ID") != null) {
				item.put("_parentId",
						item.get("UP_PLAN_ID"));
			}
		}
		int count = demandPlanMapper
				.selectCountOfDemandPlansForPage(
						demandPlan);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", demandPlans);
		map.put("total", count);
		return map;
	}

	@Override
	public Map<String, Object> updateDemandPlansAndDetails(
			DemandPlan demandPlan, String toolIds,
			String toolAmounts) {
		int bool = demandPlanDetailService
				.deleteByDemandPlan(demandPlan);
		bool = demandPlanMapper
				.updateByPrimaryKeySelective(demandPlan);
		bool = demandPlanDetailService.addDemandPlanDetails(
				demandPlan.getPlanId(), toolIds,
				toolAmounts);
		Map<String, Object> map = new HashMap<String, Object>();
		if (bool == 0) {
			map.put("success", false);
			map.put("msg", "更新出错，请联系管理员");
		} else {
			map.put("success", true);
			map.put("msg", "更新成功");
		}
		return map;
	}

	@Override
	public Map<String, Object> updateDemandPlan(
			DemandPlan demandPlan) {
		int bool = demandPlanMapper
				.updateByPrimaryKeySelective(demandPlan);
		Map<String, Object> map = new HashMap<String, Object>();
		if (bool == 0) {
			map.put("success", false);
			map.put("msg", "更新出错，请联系管理员");
		} else {
			map.put("success", true);
			map.put("msg", "更新成功");
		}
		return map;
	}

}
