package com.gqj.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.base.admin.service.IDictionaryService;
import com.base.controller.BaseController;
import com.base.util.BaseUtil;
import com.gqj.entity.ToolForPlan;
import com.gqj.entity.ToolType;
import com.gqj.service.IToolForPlanService;
import com.gqj.service.IToolTypeService;

@Controller
@RequestMapping("/gqj/tool_for_plan")
public class ToolForPlanController extends BaseController {
	public static final Logger LOGGER = Logger
			.getLogger(ToolForPlanController.class);

	@Autowired
	private IDictionaryService dictionaryService;

	@Autowired
	private IToolForPlanService toolForPlanService;

	@Autowired
	private IToolTypeService toolTypeService;

	/**
	 * 添加工器具信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addNewToolForPlan.do")
	@ResponseBody
	public Map<String, Object> addNewToolForPlan(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String typeId = request.getParameter("TYPE_ID");
		String toolName = request.getParameter("TOOL_NAME");
		String toolStandardConfigDemand = request
				.getParameter("TOOL_STANDARD_CONFIG_DEMAND");
		String toolModelDemand = request
				.getParameter("TOOL_MODEL_DEMAND");
		String toolUnit = request.getParameter("TOOL_UNIT");
		String toolRemark = request.getParameter("TOOL_REMARK");
		Map<String, Object> map = new HashMap<String, Object>();
		ToolForPlan toolForPlan = new ToolForPlan();
		toolForPlan.setToolId(-1l);
		toolForPlan.setTypeId(BaseUtil.strToLong(typeId));
		toolForPlan.setToolName(toolName);
		toolForPlan
				.setToolStandardConfigDemand(toolStandardConfigDemand);
		toolForPlan.setToolModelDemand(toolModelDemand);
		toolForPlan.setToolUnit(BaseUtil.strToLong(toolUnit));
		toolForPlan.setToolRemark(toolRemark);
		int bool = toolForPlanService.insertSelective(toolForPlan);
		if (bool == 0) {
			map.put("success", false);
			map.put("msg", "保存出错，请联系管理员");
		} else {
			map.put("success", true);
			map.put("msg", "保存成功");
		}
		return map;
	}

	/**
	 * 删除工器具
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delToolForPlans.do")
	@ResponseBody
	public Map<String, Object> delToolForPlans(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String toolForPlanIds = request.getParameter("TOOL_IDS");
		Map<String, Object> map = new HashMap<>();
		ToolForPlan toolForPlan = new ToolForPlan();
		toolForPlan.setIds(toolForPlanIds);
		int bool = toolForPlanService.deleteByPrimaryKeys(toolForPlan);
		if (bool == 0) {
			map.put("success", false);
			map.put("msg", "删除失败，请联系管理员");
		} else {
			map.put("success", true);
			map.put("msg", "删除成功");
		}
		return map;
	}

	/**
	 * 跳转到工器具管理操作页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/openEditUI.do", method = RequestMethod.GET)
	public ModelAndView openEditUI(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView("/gqj/tool_for_plan/editUI");
	}

	/**
	 * 分页查询工器具列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryToolForPlansPage.do")
	@ResponseBody
	public Map<String, Object> queryToolForPlansPage(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String keyWord = request.getParameter("keyWord");
		ToolForPlan toolForPlan = new ToolForPlan();
		toolForPlan.setCurrPage(Integer.parseInt(page));
		toolForPlan.setPageSize(Integer.parseInt(rows));
		toolForPlan.setKeyWord(keyWord);
		return toolForPlanService
				.selectToolForPlansForPage(toolForPlan);
	}

	/**
	 * 查询下拉列表
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/queryToolForPlanTypeDropList.do")
	@ResponseBody
	public void queryToolForPlanTypeDropList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.getWriter().print(
				toolTypeService.selectToolTypesForList(new ToolType()));
		response.getWriter().flush();
		response.getWriter().close();
	}

	/**
	 * 查询工器具单位下拉列表
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/queryToolForPlanUnitDropList.do")
	@ResponseBody
	public void queryToolForPlanUnitDropList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.getWriter().print(
				dictionaryService.getDictionarysByDicCode("UNIT"));
		response.getWriter().flush();
		response.getWriter().close();
	}

	/**
	 * 跳转到工器具管理首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/index.do", method = RequestMethod.GET)
	public ModelAndView toIndex() {
		return new ModelAndView("/gqj/tool_for_plan/index");
	}

	/**
	 * 更新工器具信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateToolForPlan.do")
	@ResponseBody
	public Map<String, Object> updateToolForPlan(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String toolId = request.getParameter("TOOL_ID");
		String typeId = request.getParameter("TYPE_ID");
		String toolName = request.getParameter("TOOL_NAME");
		String toolStandardConfigDemand = request
				.getParameter("TOOL_STANDARD_CONFIG_DEMAND");
		String toolModelDemand = request
				.getParameter("TOOL_MODEL_DEMAND");
		String toolUnit = request.getParameter("TOOL_UNIT");
		String toolRemark = request.getParameter("TOOL_REMARK");
		Map<String, Object> map = new HashMap<String, Object>();
		ToolForPlan toolForPlan = new ToolForPlan();
		toolForPlan.setToolId(BaseUtil.strToLong(toolId));
		toolForPlan.setTypeId(BaseUtil.strToLong(typeId));
		toolForPlan.setToolName(toolName);
		toolForPlan
				.setToolStandardConfigDemand(toolStandardConfigDemand);
		toolForPlan.setToolModelDemand(toolModelDemand);
		toolForPlan.setToolUnit(BaseUtil.strToLong(toolUnit));
		toolForPlan.setToolRemark(toolRemark);
		int bool = toolForPlanService
				.updateByPrimaryKeySelective(toolForPlan);
		if (bool == 0) {
			map.put("success", false);
			map.put("msg", "保存出错，请联系管理员");
		} else {
			map.put("success", true);
			map.put("msg", "保存成功");
		}
		return map;
	}

}
