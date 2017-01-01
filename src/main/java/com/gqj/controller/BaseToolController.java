package com.gqj.controller;

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

import com.base.controller.BaseController;
import com.base.util.BaseUtil;
import com.gqj.entity.BaseTool;
import com.gqj.entity.Manufacturer;
import com.gqj.entity.ToolDemand;
import com.gqj.entity.ToolType;
import com.gqj.service.IBaseToolService;
import com.gqj.service.IManufacturerService;
import com.gqj.service.IToolDemandService;
import com.gqj.service.IToolTypeService;

@Controller
@RequestMapping("/gqj/base_tool")
public class BaseToolController extends BaseController {
	public static final Logger LOGGER = Logger
			.getLogger(BaseToolController.class);

	@Autowired
	private IBaseToolService baseToolService;

	@Autowired
	private IManufacturerService manufacturerService;

	@Autowired
	private IToolDemandService toolDemandService;

	@Autowired
	private IToolTypeService toolTypeService;

	/**
	 * 添加工器具基础信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addNewBaseTool.do")
	@ResponseBody
	public Map<String, Object> addNewBaseTool(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String baseToolDemandId = request
				.getParameter("TOOL_DEMAND_ID");
		String baseManId = request.getParameter("MAN_ID");
		String baseToolModel = request
				.getParameter("BASE_TOOL_MODEL");
		String baseToolSpec = request
				.getParameter("BASE_TOOL_SPEC");
		String baseToolRemark = request
				.getParameter("BASE_TOOL_REMARK");
		BaseTool baseTool = new BaseTool();
		baseTool.setBaseToolId(-1l);
		baseTool.setBaseToolModel(baseToolModel);
		baseTool.setBaseToolRemark(baseToolRemark);
		baseTool.setBaseToolSpec(baseToolSpec);
		baseTool.setManId(BaseUtil.strToLong(baseManId));
		baseTool.setToolDemandId(
				BaseUtil.strToLong(baseToolDemandId));
		return baseToolService.addNewBaseTool(baseTool);
	}

	/**
	 * 删除工器具基础
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delBaseTools.do")
	@ResponseBody
	public Map<String, Object> delBaseTools(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String baseToolIds = request
				.getParameter("BASE_TOOL_IDS");
		BaseTool baseTool = new BaseTool();
		baseTool.setIds(baseToolIds);
		return baseToolService.deleteBaseTools(baseTool);
	}

	/**
	 * 跳转到工器具基础管理操作页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/openEditUI.do", method = RequestMethod.GET)
	public ModelAndView openEditUI(
			HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView("/gqj/base_tool/editUI");
	}

	/**
	 * 分页查询工器具基础列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryBaseToolsPage.do")
	@ResponseBody
	public Map<String, Object> queryBaseToolsPage(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String keyWord = request.getParameter("keyWord");
		BaseTool baseTool = new BaseTool();
		baseTool.setCurrPage(Integer.parseInt(page));
		baseTool.setPageSize(Integer.parseInt(rows));
		baseTool.setKeyWord(keyWord);
		return baseToolService
				.selectBaseToolsForPage(baseTool);
	}

	/**
	 * 分页查询厂家列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryManufacturersPage.do")
	@ResponseBody
	public Map<String, Object> queryManufacturersPage(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String keyWord = request.getParameter("keyWord");
		Manufacturer manufacturer = new Manufacturer();
		manufacturer.setCurrPage(Integer.parseInt(page));
		manufacturer.setPageSize(Integer.parseInt(rows));
		manufacturer.setKeyWord(keyWord);
		return manufacturerService
				.selectManufacturersForPage(manufacturer);
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
		toolDemand
				.setTypeId(BaseUtil.strToLong(toolTypeId));
		toolDemand.setCurrPage(Integer.parseInt(page));
		toolDemand.setPageSize(Integer.parseInt(rows));
		toolDemand.setKeyWord(keyWord);
		return toolDemandService
				.selectToolDemandsForPage(toolDemand);
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
	 * 跳转到工器具基础管理首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/index.do", method = RequestMethod.GET)
	public ModelAndView toIndex() {
		return new ModelAndView("/gqj/base_tool/index");
	}

	/**
	 * 更新工器具基础信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateBaseTool.do")
	@ResponseBody
	public Map<String, Object> updateBaseTool(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String baseToolId = request
				.getParameter("BASE_TOOL_ID");
		String baseToolDemandId = request
				.getParameter("TOOL_DEMAND_ID");
		String baseManId = request.getParameter("MAN_ID");
		String baseToolModel = request
				.getParameter("BASE_TOOL_MODEL");
		String baseToolSpec = request
				.getParameter("BASE_TOOL_SPEC");
		String baseToolRemark = request
				.getParameter("BASE_TOOL_REMARK");
		BaseTool baseTool = new BaseTool();
		baseTool.setBaseToolId(
				BaseUtil.strToLong(baseToolId));
		baseTool.setBaseToolModel(baseToolModel);
		baseTool.setBaseToolRemark(baseToolRemark);
		baseTool.setBaseToolSpec(baseToolSpec);
		baseTool.setManId(BaseUtil.strToLong(baseManId));
		baseTool.setToolDemandId(
				BaseUtil.strToLong(baseToolDemandId));
		return baseToolService.updateBaseTool(baseTool);
	}

}
