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
import com.gqj.entity.ToolType;
import com.gqj.service.IToolTypeService;

@Controller
@RequestMapping("/gqj/tool_type")
public class ToolTypeController extends BaseController {
	public static final Logger LOGGER = Logger
			.getLogger(ToolTypeController.class);

	@Autowired
	private IToolTypeService toolTypeService;

	/**
	 * 添加物资类型信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addNewToolType.do")
	@ResponseBody
	public Map<String, Object> addNewToolType(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String typeName = request.getParameter("TYPE_NAME");
		ToolType toolType = new ToolType();
		toolType.setTypeId(-1l);
		toolType.setTypeName(typeName);
		return toolTypeService.addNewToolType(toolType);
	}

	/**
	 * 删除物资类型
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delToolTypes.do")
	@ResponseBody
	public Map<String, Object> delToolTypes(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String toolTypeIds = request
				.getParameter("TYPE_IDS");
		ToolType toolType = new ToolType();
		toolType.setIds(toolTypeIds);
		return toolTypeService.deleteToolTypes(toolType);
	}

	/**
	 * 跳转到物资类型管理操作页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/openEditUI.do", method = RequestMethod.GET)
	public ModelAndView openEditUI(
			HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView("/gqj/tool_type/editUI");
	}

	/**
	 * 分页查询物资类型列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryToolTypesPage.do")
	@ResponseBody
	public Map<String, Object> queryToolTypesPage(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String keyWord = request.getParameter("keyWord");
		ToolType toolType = new ToolType();
		toolType.setCurrPage(Integer.parseInt(page));
		toolType.setPageSize(Integer.parseInt(rows));
		toolType.setKeyWord(keyWord);
		return toolTypeService
				.selectToolTypesForPage(toolType);
	}

	/**
	 * 跳转到物资类型管理首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/index.do", method = RequestMethod.GET)
	public ModelAndView toIndex() {
		return new ModelAndView("/gqj/tool_type/index");
	}

	/**
	 * 更新物资类型信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateToolType.do")
	@ResponseBody
	public Map<String, Object> updateToolType(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String typeId = request.getParameter("TYPE_ID");
		String typeName = request.getParameter("TYPE_NAME");
		ToolType toolType = new ToolType();
		toolType.setTypeId(BaseUtil.strToLong(typeId));
		toolType.setTypeName(typeName);
		return toolTypeService.updateToolType(toolType);
	}

}
