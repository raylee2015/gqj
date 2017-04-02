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

import com.base.admin.entity.User;
import com.base.admin.service.IDeptService;
import com.base.admin.service.IUserService;
import com.base.controller.BaseController;
import com.base.util.BaseUtil;
import com.gqj.entity.DemandPlan;
import com.gqj.entity.Template;
import com.gqj.entity.ToolDemand;
import com.gqj.entity.ToolType;
import com.gqj.service.IDemandPlanDetailService;
import com.gqj.service.IDemandPlanService;
import com.gqj.service.ITemplateDetailService;
import com.gqj.service.ITemplateService;
import com.gqj.service.IToolDemandService;
import com.gqj.service.IToolTypeService;
import com.gqj.util.PlanStatus;

@Controller
@RequestMapping("/gqj/demand_plan")
public class DemandPlanController extends BaseController {
	public static final Logger LOGGER = Logger
			.getLogger(DemandPlanController.class);

	@Autowired
	private IDemandPlanDetailService demandPlanDetailService;

	@Autowired
	private IDemandPlanService demandPlanService;

	@Autowired
	private IToolDemandService toolDemandService;

	@Autowired
	private IToolTypeService toolTypeService;

	@Autowired
	private ITemplateService templateService;

	@Autowired
	private ITemplateDetailService templateDetailService;

	/**
	 * 查询模板明细列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryTemplateDetailsForList.do")
	@ResponseBody
	public Map<String, Object> queryTemplateDetailsForList(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String templateId = request
				.getParameter("TEMPLATE_ID");
		Template template = new Template();
		template.setTemplateId(
				BaseUtil.strToLong(templateId));
		return templateDetailService
				.selectTemplateDetailsForList(template);
	}

	/**
	 * 分页查询模板列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryTemplateForList.do")
	@ResponseBody
	public Map<String, Object> queryTemplateForList(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		User user = getSessionUser(request, response);
		Template template = new Template();
		template.setTemplateDeptId(user.getUserDeptId());
		return templateService
				.selectTemplatesForList(template);
	}

	/**
	 * 添加需求计划信息
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
	 * 添加需求计划信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addAnnualDemandPlan.do")
	@ResponseBody
	@Transactional
	public Map<String, Object> addAnnualDemandPlan(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String planCode = request.getParameter("PLAN_CODE");
		String deptIds = request.getParameter("DEPT_IDS");
		String deptNames = request
				.getParameter("DEPT_NAMES");
		DemandPlan demandPlan = new DemandPlan();
		demandPlan.setPlanId(-1l);
		demandPlan.setPlanCode(planCode);
		return demandPlanService.addAnnualDemandPlan(
				demandPlan, deptIds, deptNames,
				getSessionUser(request, response));
	}

	/**
	 * 汇总需求计划信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/totalToolForParentDemandPlan.do")
	@ResponseBody
	@Transactional
	public Map<String, Object> totalToolForParentDemandPlan(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String planIds = request.getParameter("PLAN_IDS");
		DemandPlan demandPlan = new DemandPlan();
		demandPlan.setIds(planIds);
		return demandPlanService
				.totalToolForParentDemandPlan(demandPlan);
	}

	/**
	 * 删除需求计划
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
	 * 导出需求计划
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/exportDemandPlans.do")
	@ResponseBody
	public Map<String, Object> exportDemandPlans(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String demandPlanIds = request
				.getParameter("PLAN_IDS");
		DemandPlan demandPlan = new DemandPlan();
		demandPlan.setIds(demandPlanIds);
		return demandPlanService
				.exportDemandPlans(demandPlan);
	}

	/**
	 * 跳转到需求计划选择工器具操作页面
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
	 * 跳转到选人页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/openChooseUserForAnnualPlanUI.do", method = RequestMethod.GET)
	public ModelAndView openChooseUserForAnnualPlanUI(
			HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView(
				"/gqj/demand_plan/chooseUserUI");
	}

	/**
	 * 跳转到需求计划管理操作页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/openChooseDeptForAnnualPlanUI.do", method = RequestMethod.GET)
	public ModelAndView openChooseDeptForAnnualPlanUI(
			HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView(
				"/gqj/demand_plan/chooseDeptForAnnualPlanUI");
	}

	/**
	 * 查询需求计划明细列表
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

	@Autowired
	private IDeptService deptService;

	/**
	 * 查询部门树
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/queryDeptTree.do")
	@ResponseBody
	public void queryDeptTree(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.getWriter().print(deptService
				.selectDeptsForTree().toLowerCase());
		response.getWriter().flush();
		response.getWriter().close();
	}

	/**
	 * 分页查询需求计划列表
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
		String opType = request.getParameter("OP_TYPE");
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
		if ("EDIT".equals(opType)) {
			// 显示自己的所有计划
			demandPlan.setPlanCreateUserId(
					getSessionUser(request, response)
							.getUserId());
		} else if ("AUDIT_BY_WORK_GROUP".equals(opType)) {
			demandPlan.setPlanDeptId(
					getSessionUser(request, response)
							.getUserDeptId());
		} else if ("AUDIT_BY_DEPT".equals(opType)) {
		}
		return demandPlanService
				.selectDemandPlansForPage(demandPlan);
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

	@Autowired
	private IUserService userService;

	/**
	 * 分页查询用户列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryUsersPage.do")
	@ResponseBody
	public Map<String, Object> queryUsersPage(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String keyWord = request.getParameter("keyWord");
		User user = new User();
		user.setCurrPage(BaseUtil.strToInt(page));
		user.setPageSize(BaseUtil.strToInt(rows));
		user.setKeyWord(keyWord);
		return userService.queryUsersForPage(user);
	}

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
	 * 跳转到需求计划管理首页
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
	 * 更新需求计划信息
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
		String planStatus = request
				.getParameter("PLAN_STATUS");
		String toolAmounts = request
				.getParameter("TOOL_AMOUNTS");
		String toolSumAmounts = request
				.getParameter("TOOL_SUM_AMOUNTS");
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
		if (BaseUtil.strToLong(
				planStatus) == PlanStatus.UNSUBMIT) {
			demandPlan.setPlanStatus(PlanStatus.UNSUBMIT);
		} else {
			demandPlan.setPlanStatus(
					BaseUtil.strToLong(planStatus));
		}
		demandPlan.setPlanRemark(planRemark);
		return demandPlanService
				.updateDemandPlansAndDetails(demandPlan,
						toolIds, toolAmounts,
						toolSumAmounts);
	}

	/**
	 * 更新需求计划信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateDemandPlanStatus.do")
	@ResponseBody
	public Map<String, Object> updateDemandPlanStatus(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String planIds = request.getParameter("PLAN_IDS");
		String planStatus = request
				.getParameter("PLAN_STATUS");
		DemandPlan demandPlan = new DemandPlan();
		demandPlan.setIds(planIds);
		demandPlan.setPlanStatus(
				BaseUtil.strToLong(planStatus));
		return demandPlanService
				.updateDemandPlan(demandPlan);
	}

	/**
	 * 更新需求计划信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateCreateUserForAnnualPlan.do")
	@ResponseBody
	public Map<String, Object> updateCreateUserForAnnualPlan(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String planId = request.getParameter("PLAN_ID");
		String userId = request.getParameter("USER_ID");
		DemandPlan demandPlan = new DemandPlan();
		demandPlan.setPlanId(BaseUtil.strToLong(planId));
		// 由于需要整体配合，这里的update需要使用ids的修改
		demandPlan.setIds(planId);
		demandPlan.setPlanCreateUserId(
				BaseUtil.strToLong(userId));
		return demandPlanService
				.updateDemandPlan(demandPlan);
	}

}
