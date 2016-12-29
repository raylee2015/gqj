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

import com.base.controller.BaseController;
import com.base.util.BaseUtil;
import com.gqj.entity.BaseTool;
import com.gqj.service.IBaseToolService;

@Controller
@RequestMapping("/gqj/base_tool")
public class BaseToolController extends BaseController {
	public static final Logger LOGGER = Logger
			.getLogger(BaseToolController.class);

	@Autowired
	private IBaseToolService baseToolService;

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
		String baseTypeId = request.getParameter("TYPE_ID");
		String baseToolIdForPlan = request
				.getParameter("TOOL_ID_FOR_PLAN");
		String baseManId = request.getParameter("MAN_ID");
		String baseToolModel = request
				.getParameter("BASE_TOOL_MODEL");
		String baseToolSpec = request
				.getParameter("BASE_TOOL_SPEC");
		String baseToolRemark = request
				.getParameter("BASE_TOOL_REMARK");
		Map<String, Object> map = new HashMap<String, Object>();
		BaseTool baseTool = new BaseTool();
		baseTool.setBaseToolId(-1l);
		baseTool.setBaseToolModel(baseToolModel);
		baseTool.setBaseToolRemark(baseToolRemark);
		baseTool.setBaseToolSpec(baseToolSpec);
		baseTool.setTypeId(BaseUtil.strToLong(baseTypeId));
		baseTool.setManId(BaseUtil.strToLong(baseManId));
		baseTool.setToolIdForPlan(
				BaseUtil.strToLong(baseToolIdForPlan));

		int bool = baseToolService
				.insertSelective(baseTool);
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
		Map<String, Object> map = new HashMap<>();
		BaseTool baseTool = new BaseTool();
		baseTool.setIds(baseToolIds);
		int bool = baseToolService
				.deleteByPrimaryKeys(baseTool);
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
	 * 跳转到工器具基础管理首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/index.do", method = RequestMethod.GET)
	public ModelAndView toIndex() {
		return new ModelAndView("/gqj/base_tool/index");
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
		String baseTypeId = request.getParameter("TYPE_ID");
		String baseToolIdForPlan = request
				.getParameter("TOOL_ID_FOR_PLAN");
		String baseManId = request.getParameter("MAN_ID");
		String baseToolModel = request
				.getParameter("BASE_TOOL_MODEL");
		String baseToolSpec = request
				.getParameter("BASE_TOOL_SPEC");
		String baseToolRemark = request
				.getParameter("BASE_TOOL_REMARK");
		Map<String, Object> map = new HashMap<String, Object>();
		BaseTool baseTool = new BaseTool();
		baseTool.setBaseToolId(
				BaseUtil.strToLong(baseToolId));
		baseTool.setBaseToolModel(baseToolModel);
		baseTool.setBaseToolRemark(baseToolRemark);
		baseTool.setBaseToolSpec(baseToolSpec);
		baseTool.setTypeId(BaseUtil.strToLong(baseTypeId));
		baseTool.setManId(BaseUtil.strToLong(baseManId));
		baseTool.setToolIdForPlan(
				BaseUtil.strToLong(baseToolIdForPlan));
		int bool = baseToolService
				.updateByPrimaryKeySelective(baseTool);
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
