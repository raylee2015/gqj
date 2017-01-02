package com.gqj.controller;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.base.controller.BaseController;
import com.base.util.BaseUtil;
import com.gqj.entity.DemandPlan;
import com.gqj.entity.ToolDemand;
import com.gqj.entity.ToolType;
import com.gqj.service.IDemandPlanDetailService;
import com.gqj.service.IDemandPlanService;
import com.gqj.service.IToolDemandService;
import com.gqj.service.IToolTypeService;
import com.gqj.util.PlanStatus;

@Controller
@RequestMapping("/gqj/demand_plan")
public class DemandPlanController extends BaseController {
	public static final Logger LOGGER = Logger
			.getLogger(DemandPlanController.class);

	@Autowired
	private IDemandPlanService demandPlanService;

	@Autowired
	private IToolDemandService toolDemandService;

	@Autowired
	private IToolTypeService toolTypeService;

	/**
	 * 查询下拉列表
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/queryToolDemandTypeDropList.do")
	@ResponseBody
	public void queryToolDemandTypeDropList(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.getWriter().print(toolTypeService
				.selectToolTypesForList(new ToolType()));
		response.getWriter().flush();
		response.getWriter().close();
	}

	/**
	 * 分页查询工器具列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryToolDemandsPage.do")
	@ResponseBody
	public Map<String, Object> queryToolDemandsPage(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String keyWord = request.getParameter("keyWord");
		String toolTypeId = request
				.getParameter("TOOL_TYPE_ID");
		ToolDemand toolDemand = new ToolDemand();
		toolDemand.setCurrPage(Integer.parseInt(page));
		toolDemand.setPageSize(Integer.parseInt(rows));
		toolDemand.setKeyWord(keyWord);
		toolDemand
				.setTypeId(BaseUtil.strToLong(toolTypeId));
		return toolDemandService
				.selectToolDemandsForPage(toolDemand);
	}

	/**
	 * 添加模板信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addNewDemandPlansAndDetails.do")
	@ResponseBody
	@Transactional
	public Map<String, Object> addNewDemandPlansAndDetails(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String planCode = request.getParameter("PLAN_CODE");
		String planType = request.getParameter("PLAN_TYPE");
		String toolIds = request.getParameter("TOOL_IDS");
		String toolAmounts = request
				.getParameter("TOOL_AMOUNTS");
		String planRemark = request
				.getParameter("PLAN_REMARK");
		DemandPlan demandPlan = new DemandPlan();
		demandPlan.setPlanId(-1l);
		demandPlan
				.setPlanType(BaseUtil.strToLong(planType));
		demandPlan.setPlanCode(planCode);
		demandPlan.setPlanCreateDate(new Date());
		demandPlan.setPlanCreateUserId(
				getSessionUser(request, response)
						.getUserId());
		demandPlan.setPlanDeptId(
				getSessionUser(request, response)
						.getUserDeptId());
		demandPlan.setPlanStatus(PlanStatus.UNSUBMIT);
		demandPlan.setPlanRemark(planRemark);
		return demandPlanService.addDemandPlansAndDetails(
				demandPlan, toolIds, toolAmounts);
	}

	/**
	 * 删除模板
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/deleteDemandPlansAndDetails.do")
	@ResponseBody
	public Map<String, Object> deleteDemandPlansAndDetails(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String demandPlanIds = request
				.getParameter("PLAN_IDS");
		DemandPlan demandPlan = new DemandPlan();
		demandPlan.setIds(demandPlanIds);
		return demandPlanService
				.deleteDemandPlansAndDetails(demandPlan);
	}

	/**
	 * 分页查询模板列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryDemandPlansPage.do")
	@ResponseBody
	public Map<String, Object> queryDemandPlansPage(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String keyWord = request.getParameter("keyWord");
		String planType = request.getParameter("PLAN_TYPE");
		DemandPlan demandPlan = new DemandPlan();
		demandPlan.setCurrPage(Integer.parseInt(page));
		demandPlan.setPageSize(Integer.parseInt(rows));
		demandPlan.setKeyWord(keyWord);
		demandPlan
				.setPlanType(BaseUtil.strToLong(planType));
		return demandPlanService
				.selectDemandPlansForPage(demandPlan);
	}

	@Autowired
	private IDemandPlanDetailService demandPlanDetailService;

	/**
	 * 查询模板明细列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryDemandPlanDetailsForList.do")
	@ResponseBody
	public Map<String, Object> queryDemandPlanDetailsForList(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String demandPlanId = request
				.getParameter("PLAN_ID");
		DemandPlan demandPlan = new DemandPlan();
		demandPlan.setPlanId(
				BaseUtil.strToLong(demandPlanId));
		return demandPlanDetailService
				.selectDemandPlanDetailsForList(demandPlan);
	}

	/**
	 * 跳转到模板管理首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/index.do", method = RequestMethod.GET)
	public ModelAndView toIndex(
			HttpServletRequest request) {
		String planType = request.getParameter("PLAN_TYPE");
		ModelAndView mv = new ModelAndView();
		if ("ANNUAL".equals(planType)) {
			mv.setViewName("/gqj/demand_plan/annual_index");
		} else if ("TEMPORARY".equals(planType)) {
			mv.setViewName(
					"/gqj/demand_plan/temporary_index");
		}
		return mv;
	}

	/**
	 * 跳转到模板管理操作页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/openChooseToolDemandUI.do", method = RequestMethod.GET)
	public ModelAndView openChooseToolDemandUI(
			HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView(
				"/gqj/demand_plan/chooseToolDemandUI");
	}

	/**
	 * 更新模板信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateDemandPlansAndDetails.do")
	@ResponseBody
	public Map<String, Object> updateDemandPlansAndDetails(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String planId = request.getParameter("PLAN_ID");
		String planCode = request.getParameter("PLAN_CODE");
		String planType = request.getParameter("PLAN_TYPE");
		String toolIds = request.getParameter("TOOL_IDS");
		String toolAmounts = request
				.getParameter("TOOL_AMOUNTS");
		String planRemark = request
				.getParameter("PLAN_REMARK");
		DemandPlan demandPlan = new DemandPlan();
		demandPlan.setPlanId(BaseUtil.strToLong(planId));
		demandPlan.setIds(planId);
		demandPlan.setPlanCode(planCode);
		demandPlan
				.setPlanType(BaseUtil.strToLong(planType));
		demandPlan.setPlanCreateDate(new Date());
		demandPlan.setPlanCreateUserId(
				getSessionUser(request, response)
						.getUserId());
		demandPlan.setPlanDeptId(
				getSessionUser(request, response)
						.getUserDeptId());
		demandPlan.setPlanStatus(PlanStatus.UNSUBMIT);
		demandPlan.setPlanRemark(planRemark);
		return demandPlanService
				.updateDemandPlansAndDetails(demandPlan,
						toolIds, toolAmounts);
	}

}
