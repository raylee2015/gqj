package com.gqj.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.admin.entity.User;
import com.base.admin.service.IDeptService;
import com.base.util.BaseUtil;
import com.base.util.DateUtil;
import com.gqj.dao.DemandPlanDetailMapper;
import com.gqj.dao.DemandPlanMapper;
import com.gqj.entity.DemandPlan;
import com.gqj.service.IDemandPlanDetailService;
import com.gqj.service.IDemandPlanService;
import com.gqj.util.PlanStatus;
import com.index.util.BaseSysParam;

import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;

@Service
public class DemandPlanServiceImpl implements IDemandPlanService {

	@Autowired
	private DemandPlanMapper demandPlanMapper;

	@Autowired
	private DemandPlanDetailMapper demandPlanDetailMapper;

	@Autowired
	private IDemandPlanDetailService demandPlanDetailService;

	@Override
	public Map<String, Object> deleteDemandPlansAndDetails(
			DemandPlan demandPlan) {
		Map<String, Object> map = new HashMap<String, Object>();
		int bool = demandPlanDetailService
				.deleteByDemandPlan(demandPlan);
		bool = demandPlanMapper.deleteByPrimaryKeys(demandPlan);
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
	public Map<String, Object> exportDemandPlans(DemandPlan demandPlan)
			throws ParsePropertyException, InvalidFormatException,
			IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		String demandPlanIds = demandPlan.getIds();
		String[] demandPlanId_arr = demandPlanIds.split(",");
		int bool = 1;
		ArrayList<String> exportFileList = new ArrayList<String>();
		for (String demandPlanId : demandPlanId_arr) {
			DemandPlan param = new DemandPlan();
			param.setPlanId(BaseUtil.strToLong(demandPlanId));
			Map<String, Object> resultDemandPlan = demandPlanMapper
					.selectDemandPlanForObject(param);
			List<Map<String, Object>> resultDemandPlanDetailList = demandPlanDetailMapper
					.selectDemandPlanDetailsForList(param);
			int sequence = 1;
			for (Map<String, Object> item : resultDemandPlanDetailList) {
				item.put("sequence", sequence++);
			}
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("plan", resultDemandPlan);
			data.put("list", resultDemandPlanDetailList);
			// 生成文件
			XLSTransformer transformer = new XLSTransformer();
			String templatePath = this.getClass().getClassLoader()
					.getResource(
							"/com/gqj/resources/template/demandPlan.xls")
					.getPath();
			String targetPath = BaseSysParam.getSysRootPath()
					+ "/tempFile/"
					+ resultDemandPlan.get("PLAN_CODE").toString() + "-"
					+ resultDemandPlan.get("PLAN_DEPT_NAME").toString()
					+ ".xls";
			transformer.transformXLS(templatePath, data, targetPath);
			exportFileList.add(targetPath);
		}
		if (bool == 0) {
			map.put("success", false);
			map.put("msg", "导出失败，请联系管理员");
		} else {
			map.put("exportFileList", exportFileList);
			map.put("success", true);
			map.put("msg", "导出成功");
		}
		return map;
	}

	@Override
	public Map<String, Object> exportParentDemandPlans(
			DemandPlan demandPlan) throws ParsePropertyException,
			InvalidFormatException, IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		String demandPlanIds = demandPlan.getIds();
		String[] demandPlanId_arr = demandPlanIds.split(",");
		int bool = 1;
		ArrayList<String> exportFileList = new ArrayList<String>();
		for (String demandPlanId : demandPlanId_arr) {
			DemandPlan paramForParentPlan = new DemandPlan();
			paramForParentPlan
					.setPlanId(BaseUtil.strToLong(demandPlanId));
			Map<String, Object> resultDemandPlanForParentPlan = demandPlanMapper
					.selectDemandPlanForObject(paramForParentPlan);
			List<Map<String, Object>> resultDemandPlanDetailForParentPlanList = demandPlanDetailMapper
					.selectDemandPlanDetailsForList(paramForParentPlan);
			int sequence = 1;
			for (Map<String, Object> item : resultDemandPlanDetailForParentPlanList) {
				item.put("sequence", sequence++);
			}
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("PLAN_CODE", resultDemandPlanForParentPlan
					.get("PLAN_CODE").toString());
			data.put("PLAN_DEPT_NAME", resultDemandPlanForParentPlan
					.get("PLAN_DEPT_NAME").toString());

			paramForParentPlan.setPlanId(null);
			paramForParentPlan
					.setUpPlanId(BaseUtil.strToLong(demandPlanId));
			List<DemandPlan> subDemandPlanList = demandPlanMapper
					.selectDemandPlansForList(paramForParentPlan);

			String planAssignedDeptIds = resultDemandPlanForParentPlan
					.get("PLAN_ASSIGNED_DEPT_ID").toString();
			String planAssignedDeptNames = resultDemandPlanForParentPlan
					.get("PLAN_ASSIGNED_DEPT_NAME").toString();
			String[] planAssignedDeptId_arr = planAssignedDeptIds
					.split(",");
			String[] planAssignedDeptName_arr = planAssignedDeptNames
					.split(",");
			for (int i = 0; i < subDemandPlanList.size(); i++) {
				DemandPlan paramForSubPlan = subDemandPlanList.get(i);
				List<Map<String, Object>> resultDemandPlanDetailListForSubPlan = demandPlanDetailMapper
						.selectDemandPlanDetailsForList(
								paramForSubPlan);
				long planDeptId = paramForSubPlan.getPlanDeptId();
				String planDeptName = "";
				for (int j = 0; j < planAssignedDeptId_arr.length; j++) {
					if (planAssignedDeptId_arr[j]
							.equals(planDeptId + "")) {
						planDeptName = planAssignedDeptName_arr[j];
						break;
					}
				}
				data.put("PLAN_DEPT_NAME_" + i, planDeptName);
				for (Map<String, Object> itemOfParentPlan : resultDemandPlanDetailForParentPlanList) {
					for (Map<String, Object> itemOfSubPlan : resultDemandPlanDetailListForSubPlan) {
						if (itemOfParentPlan.get("TOOL_ID").toString()
								.equals(itemOfSubPlan.get("TOOL_ID")
										.toString())) {
							itemOfParentPlan.put("TOOL_AMOUNT_" + i,
									itemOfSubPlan.get("TOOL_AMOUNT")
											.toString());
							break;
						}
					}
				}
			}
			data.put("list", resultDemandPlanDetailForParentPlanList);

			// 生成文件
			XLSTransformer transformer = new XLSTransformer();
			String templatePath = this.getClass().getClassLoader()
					.getResource(
							"/com/gqj/resources/template/parentDemandPlan.xls")
					.getPath();
			String targetPath = BaseSysParam.getSysRootPath()
					+ "/tempFile/" + resultDemandPlanForParentPlan
							.get("PLAN_CODE").toString()
					+ ".xls";
			transformer.transformXLS(templatePath, data, targetPath);
			exportFileList.add(targetPath);
		}
		if (bool == 0) {
			map.put("success", false);
			map.put("msg", "导出失败，请联系管理员");
		} else {
			map.put("exportFileList", exportFileList);
			map.put("success", true);
			map.put("msg", "导出成功");
		}
		return map;
	}

	@Override
	public Map<String, Object> addDemandPlansAndDetails(
			DemandPlan demandPlan, String toolIds, String toolAmounts) {
		Map<String, Object> map = new HashMap<String, Object>();
		int bool = demandPlanMapper.insertSelective(demandPlan);
		bool = demandPlanDetailService.addDemandPlanDetails(
				demandPlan.getPlanId(), toolIds, toolAmounts);
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
			DemandPlan demandPlan, String deptIds, String deptNames,
			User createUser) {
		Map<String, Object> map = new HashMap<String, Object>();
		String[] deptId_arr = deptIds.split(",");
		int bool = 0;
		DemandPlan demandPlanParent = new DemandPlan();
		demandPlanParent.setPlanCode(demandPlan.getPlanCode());
		// 生成父计划
		demandPlanParent.setPlanAssignedDeptId(deptIds);
		demandPlanParent.setPlanAssignedDeptName(deptNames);
		demandPlanParent.setPlanCreateDate(new Date());
		demandPlanParent.setPlanCreateUserId(createUser.getUserId());
		demandPlanParent.setPlanDeptId(createUser.getUserDeptId());
		demandPlanParent.setPlanType(0L);
		bool = demandPlanMapper.insertSelective(demandPlanParent);
		// 生成子计划
		for (int i = 0; i < deptId_arr.length; i++) {
			DemandPlan demandPlanSub = new DemandPlan();
			demandPlanSub.setUpPlanId(demandPlanParent.getPlanId());
			demandPlanSub.setPlanCode(demandPlan.getPlanCode());
			demandPlanSub.setPlanAssignedDeptId(deptIds);
			demandPlanSub.setPlanAssignedDeptName(deptNames);
			demandPlanSub
					.setPlanDeptId(BaseUtil.strToLong(deptId_arr[i]));
			demandPlanSub.setPlanType(0L);
			demandPlanSub.setPlanStatus(PlanStatus.UNSUBMIT);
			demandPlanSub.setPlanCreateDate(new Date());
			bool = demandPlanMapper.insertSelective(demandPlanSub);
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
			item.put("PLAN_CREATE_DATE", DateUtil
					.getDate(item.get("PLAN_CREATE_DATE").toString()));
			if (item.get("UP_PLAN_ID") != null) {
				if (demandPlan.getPlanDeptId() != null) {
					// 不增加_parentId才能显示
				} else if (demandPlan.getPlanCreateUserId() != null) {
					// 不增加_parentId才能显示
				} else {
					item.put("_parentId", item.get("UP_PLAN_ID"));
				}
			}
		}
		int count = demandPlanMapper
				.selectCountOfDemandPlansForPage(demandPlan);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", demandPlans);
		map.put("total", count);
		return map;
	}

	@Override
	public Map<String, Object> updateDemandPlansAndDetails(
			DemandPlan demandPlan, String toolIds, String toolAmounts) {
		int bool = demandPlanDetailService
				.deleteByDemandPlan(demandPlan);
		bool = demandPlanMapper.updateByPrimaryKeySelective(demandPlan);
		bool = demandPlanDetailService.addDemandPlanDetails(
				demandPlan.getPlanId(), toolIds, toolAmounts);
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
	public Map<String, Object> updateDemandPlansAndDetails(
			DemandPlan demandPlan, String toolIds, String toolAmounts,
			String toolSumAmounts) {
		int bool = demandPlanDetailService
				.deleteByDemandPlan(demandPlan);
		bool = demandPlanMapper.updateByPrimaryKeySelective(demandPlan);
		bool = demandPlanDetailService.addDemandPlanDetails(
				demandPlan.getPlanId(), toolIds, toolAmounts,
				toolSumAmounts);
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
	public Map<String, Object> updateDemandPlan(DemandPlan demandPlan) {
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

	@Override
	@Transactional
	public Map<String, Object> totalToolForParentDemandPlan(
			DemandPlan demandPlan) {
		String planIds = demandPlan.getIds();
		String[] planId_arr = planIds.split(",");
		Map<String, Object> map = new HashMap<String, Object>();
		for (int i = 0; i < planId_arr.length; i++) {
			String planId = planId_arr[i];
			// 修改计划状态
			DemandPlan temp = new DemandPlan();
			temp.setIds(planId);
			temp.setPlanStatus(PlanStatus.TOTAL);
			updateDemandPlan(temp);
			// 查询子需求计划的id串
			String parentPlanId = planId_arr[i];
			temp = new DemandPlan();
			temp.setUpPlanId(BaseUtil.strToLong(parentPlanId));
			List<DemandPlan> subDemandPlanList = demandPlanMapper
					.selectDemandPlansForList(temp);
			String subDemandPlanIds = "";
			for (DemandPlan demandPlanItem : subDemandPlanList) {
				subDemandPlanIds += demandPlanItem.getPlanId() + ",";
			}
			subDemandPlanIds = subDemandPlanIds.substring(0,
					subDemandPlanIds.length() - 1);
			// 根据id串汇总父需求计划的明细
			// 1.查询工器具id以及数量
			String toolIds = "";
			String toolAmounts = "";
			String toolSumAmounts = "";
			temp = new DemandPlan();
			temp.setIds(subDemandPlanIds);
			List<Map<String, Object>> subDemandPlanDetailList = demandPlanDetailService
					.selectSumDemandPlanDetailsForList(temp);
			for (Map<String, Object> demandPlanDetail : subDemandPlanDetailList) {
				toolIds += demandPlanDetail.get("TOOL_ID").toString()
						+ ",";
				toolSumAmounts += demandPlanDetail.get("TOOL_AMOUNT")
						.toString() + ",";
			}
			// 2.插入汇总数据
			temp = new DemandPlan();
			temp.setIds(parentPlanId);
			int bool = demandPlanDetailService.deleteByDemandPlan(temp);
			bool = demandPlanDetailService.addDemandPlanDetails(
					BaseUtil.strToInt(planIds), toolIds, toolAmounts,
					toolSumAmounts);
			if (bool == 0) {
				map.put("success", false);
				map.put("msg", "更新出错，请联系管理员");
			} else {
				map.put("success", true);
				map.put("msg", "更新成功");
			}
		}
		return map;
	}

}
