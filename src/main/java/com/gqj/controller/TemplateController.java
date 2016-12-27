package com.gqj.controller;

import java.util.Date;
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
import com.gqj.entity.Template;
import com.gqj.service.ITemplateService;

@Controller
@RequestMapping("/gqj/template")
public class TemplateController extends BaseController {
	public static final Logger LOGGER = Logger
			.getLogger(TemplateController.class);

	@Autowired
	private ITemplateService templateService;

	/**
	 * 添加模板信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addNewTemplate.do")
	@ResponseBody
	public Map<String, Object> addNewTemplate(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String templateName = request.getParameter("TEMPLATE_NAME");
		long templateDeptId = getSessionUser(request, response)
				.getUserDeptId();
		Map<String, Object> map = new HashMap<String, Object>();
		Template template = new Template();
		template.setTemplateId(-1l);
		template.setTemplateName(templateName);
		template.setTemplateDeptId(templateDeptId);
		template.setTemplateCreateUserId(
				getSessionUser(request, response).getUserId());
		template.setTemplateCreateDate(new Date());
		int bool = templateService.insertSelective(template);
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
	 * 删除模板
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delTemplates.do")
	@ResponseBody
	public Map<String, Object> delTemplates(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String templateIds = request.getParameter("TEMPLATE_IDS");
		Map<String, Object> map = new HashMap<>();
		Template template = new Template();
		template.setIds(templateIds);
		int bool = templateService.deleteByPrimaryKeys(template);
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
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		long templateDeptId = getSessionUser(request, response)
				.getUserDeptId();
		String keyWord = request.getParameter("keyWord");
		Template template = new Template();
		template.setCurrPage(Integer.parseInt(page));
		template.setPageSize(Integer.parseInt(rows));
		template.setKeyWord(keyWord);
		template.setTemplateDeptId(templateDeptId);
		return templateService.selectTemplatesForPage(template);
	}

	/**
	 * 跳转到模板管理首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/index.do", method = RequestMethod.GET)
	public ModelAndView toIndex() {
		return new ModelAndView("/gqj/template/index");
	}

	/**
	 * 跳转到模板管理操作页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/openEditUI.do", method = RequestMethod.GET)
	public ModelAndView openEditUI(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView("/gqj/template/editUI");
	}

	/**
	 * 更新模板信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateTemplate.do")
	@ResponseBody
	public Map<String, Object> updateTemplate(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String templateId = request.getParameter("TEMPLATE_ID");
		String templateName = request.getParameter("TEMPLATE_NAME");
		long templateDeptId = getSessionUser(request, response)
				.getUserDeptId();
		Map<String, Object> map = new HashMap<String, Object>();
		Template template = new Template();
		template.setTemplateId(BaseUtil.strToLong(templateId));
		template.setTemplateName(templateName);
		template.setTemplateDeptId(templateDeptId);
		template.setTemplateCreateUserId(
				getSessionUser(request, response).getUserId());
		template.setTemplateCreateDate(new Date());
		int bool = templateService
				.updateByPrimaryKeySelective(template);
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
