package com.gqj.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gqj.dao.TemplateMapper;
import com.gqj.entity.Template;
import com.gqj.service.ITemplateDetailService;
import com.gqj.service.ITemplateService;

@Service
public class TemplateServiceImpl
		implements ITemplateService {

	@Autowired
	private TemplateMapper templateMapper;

	@Autowired
	private ITemplateDetailService templateDetailService;

	@Override
	public Map<String, Object> deleteTemplatesAndDetails(
			Template template) {
		Map<String, Object> map = new HashMap<>();
		int bool = templateDetailService
				.deleteByTemplate(template);
		bool = templateMapper.deleteByPrimaryKeys(template);
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
	public Map<String, Object> addTemplatesAndDetails(
			Template template, String toolIds) {
		Map<String, Object> map = new HashMap<String, Object>();
		int bool = templateMapper.insertSelective(template);
		bool = templateDetailService.addTemplateDetails(
				template.getTemplateId(), toolIds);
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
	public Map<String, Object> selectTemplatesForPage(
			Template template) {
		List<Map<String, Object>> templates = templateMapper
				.selectTemplatesForPage(template);
		int count = templateMapper
				.selectCountOfTemplatesForPage(template);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", templates);
		map.put("total", count);
		return map;
	}

	@Override
	public Map<String, Object> selectTemplatesForList(
			Template template) {
		List<Map<String, Object>> templates = templateMapper
				.selectTemplatesForList(template);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", templates);
		map.put("total", templates.size());
		return map;
	}

	@Override
	public Map<String, Object> updateTemplatesAndDetails(
			Template template, String toolIds) {
		int bool = templateDetailService
				.deleteByTemplate(template);
		bool = templateMapper
				.updateByPrimaryKeySelective(template);
		bool = templateDetailService.addTemplateDetails(
				template.getTemplateId(), toolIds);
		Map<String, Object> map = new HashMap<String, Object>();
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
