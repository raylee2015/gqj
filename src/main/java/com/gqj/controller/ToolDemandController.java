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

import com.base.admin.service.IDictionaryService;
import com.base.controller.BaseController;
import com.base.util.BaseUtil;
import com.gqj.entity.ToolDemand;
import com.gqj.entity.ToolType;
import com.gqj.service.IToolDemandService;
import com.gqj.service.IToolTypeService;

@Controller
@RequestMapping("/gqj/tool_demand")
public class ToolDemandController extends BaseController {
	public static final Logger LOGGER = Logger
			.getLogger(ToolDemandController.class);

	@Autowired
	private IDictionaryService dictionaryService;

	@Autowired
	private IToolDemandService toolDemandService;

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
	@RequestMapping("/addNewToolDemand.do")
	@ResponseBody
	public Map<String, Object> addNewToolDemand(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String typeId = request.getParameter("TYPE_ID");
		String toolName = request.getParameter("TOOL_NAME");
		String toolStandardConfigDemand = request
				.getParameter(
						"TOOL_STANDARD_CONFIG_DEMAND");
		String toolModelDemand = request
				.getParameter("TOOL_MODEL_DEMAND");
		String toolUnit = request.getParameter("TOOL_UNIT");
		String toolRemark = request
				.getParameter("TOOL_REMARK");
		ToolDemand toolDemand = new ToolDemand();
		toolDemand.setToolId(-1l);
		toolDemand.setTypeId(BaseUtil.strToLong(typeId));
		toolDemand.setToolName(toolName);
		toolDemand.setToolStandardConfigDemand(
				toolStandardConfigDemand);
		toolDemand.setToolModelDemand(toolModelDemand);
		toolDemand
				.setToolUnit(BaseUtil.strToLong(toolUnit));
		toolDemand.setToolRemark(toolRemark);
		return toolDemandService
				.addNewToolDemand(toolDemand);
	}

	/**
	 * 删除工器具
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delToolDemands.do")
	@ResponseBody
	public Map<String, Object> delToolDemands(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String toolDemandIds = request
				.getParameter("TOOL_IDS");
		ToolDemand toolDemand = new ToolDemand();
		toolDemand.setIds(toolDemandIds);
		return toolDemandService
				.deleteToolDemands(toolDemand);
	}

	/**
	 * 跳转到工器具管理操作页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/openEditUI.do", method = RequestMethod.GET)
	public ModelAndView openEditUI(
			HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView("/gqj/tool_demand/editUI");
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
	 * 查询工器具单位下拉列表
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/queryToolDemandUnitDropList.do")
	@ResponseBody
	public void queryToolDemandUnitDropList(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.getWriter().print(dictionaryService
				.getDictionarysByDicCode("UNIT"));
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
		return new ModelAndView("/gqj/tool_demand/index");
	}

	/**
	 * 更新工器具信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateToolDemand.do")
	@ResponseBody
	public Map<String, Object> updateToolDemand(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String toolId = request.getParameter("TOOL_ID");
		String typeId = request.getParameter("TYPE_ID");
		String toolName = request.getParameter("TOOL_NAME");
		String toolStandardConfigDemand = request
				.getParameter(
						"TOOL_STANDARD_CONFIG_DEMAND");
		String toolModelDemand = request
				.getParameter("TOOL_MODEL_DEMAND");
		String toolUnit = request.getParameter("TOOL_UNIT");
		String toolRemark = request
				.getParameter("TOOL_REMARK");
		ToolDemand toolDemand = new ToolDemand();
		toolDemand.setToolId(BaseUtil.strToLong(toolId));
		toolDemand.setTypeId(BaseUtil.strToLong(typeId));
		toolDemand.setToolName(toolName);
		toolDemand.setToolStandardConfigDemand(
				toolStandardConfigDemand);
		toolDemand.setToolModelDemand(toolModelDemand);
		toolDemand
				.setToolUnit(BaseUtil.strToLong(toolUnit));
		toolDemand.setToolRemark(toolRemark);
		return toolDemandService
				.updateToolDemand(toolDemand);
	}

}
