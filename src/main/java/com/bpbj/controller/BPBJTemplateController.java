package com.bpbj.controller;

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
import com.base.controller.BaseController;
import com.base.util.BaseUtil;
import com.bpbj.entity.Template;
import com.bpbj.entity.ToolDemand;
import com.bpbj.entity.ToolType;
import com.bpbj.service.IBPBJTemplateDetailService;
import com.bpbj.service.IBPBJTemplateService;
import com.bpbj.service.IBPBJToolDemandService;
import com.bpbj.service.IBPBJToolTypeService;

@Controller
@RequestMapping("/bpbj/template")
public class BPBJTemplateController extends BaseController {
	public static final Logger LOGGER = Logger
			.getLogger(BPBJTemplateController.class);

	@Autowired
	private IBPBJTemplateService templateService;

	@Autowired
	private IBPBJToolDemandService toolDemandService;

	@Autowired
	private IBPBJToolTypeService toolTypeService;

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
	 * 添加模板信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addNewTemplatesAndDetails.do")
	@ResponseBody
	@Transactional
	public Map<String, Object> addNewTemplatesAndDetails(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String templateName = request
				.getParameter("TEMPLATE_NAME");
		String toolIds = request.getParameter("TOOL_IDS");
		long templateDeptId = getSessionUser(request,
				response).getUserDeptId();
		Template template = new Template();
		template.setTemplateId(-1l);
		template.setTemplateName(templateName);
		template.setTemplateDeptId(templateDeptId);
		template.setTemplateCreateUserId(
				getSessionUser(request, response)
						.getUserId());
		template.setTemplateCreateDate(new Date());
		return templateService
				.addTemplatesAndDetails(template, toolIds);
	}

	/**
	 * 删除模板
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delTemplates.do")
	@ResponseBody
	public Map<String, Object> delTemplates(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String templateIds = request
				.getParameter("TEMPLATE_IDS");
		Template template = new Template();
		template.setIds(templateIds);
		return templateService
				.deleteTemplatesAndDetails(template);
	}

	/**
	 * 分页查询模板列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryTemplatesPage.do")
	@ResponseBody
	public Map<String, Object> queryTemplatesPage(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		long templateDeptId = getSessionUser(request,
				response).getUserDeptId();
		String keyWord = request.getParameter("keyWord");
		Template template = new Template();
		template.setCurrPage(Integer.parseInt(page));
		template.setPageSize(Integer.parseInt(rows));
		template.setKeyWord(keyWord);
		template.setTemplateDeptId(templateDeptId);
		return templateService
				.selectTemplatesForPage(template);
	}

	@Autowired
	private IBPBJTemplateDetailService templateDetailService;

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
	 * 跳转到模板管理首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/index.do", method = RequestMethod.GET)
	public ModelAndView toIndex() {
		return new ModelAndView("/bpbj/template/index");
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
				"/bpbj/template/chooseToolDemandUI");
	}

	/**
	 * 更新模板信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateTemplatesAndDetails.do")
	@ResponseBody
	public Map<String, Object> updateTemplatesAndDetails(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String templateId = request
				.getParameter("TEMPLATE_ID");
		String templateName = request
				.getParameter("TEMPLATE_NAME");
		String toolIds = request.getParameter("TOOL_IDS");
		long templateDeptId = getSessionUser(request,
				response).getUserDeptId();
		Template template = new Template();
		template.setTemplateId(
				BaseUtil.strToLong(templateId));
		template.setIds(templateId);
		template.setTemplateName(templateName);
		template.setTemplateDeptId(templateDeptId);
		template.setTemplateCreateUserId(
				getSessionUser(request, response)
						.getUserId());
		template.setTemplateCreateDate(new Date());
		return templateService.updateTemplatesAndDetails(
				template, toolIds);
	}

}
