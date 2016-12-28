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
public class TemplateServiceImpl implements ITemplateService {

	@Autowired
	private TemplateMapper templateMapper;

	@Autowired
	private ITemplateDetailService templateDetailService;

	@Override
	public int deleteTemplatesAndDetails(Template template) {
		int bool = templateDetailService.deleteByTemplate(template);
		bool = templateMapper.deleteByPrimaryKeys(template);
		return bool;
	}

	@Override
	public int addTemplatesAndDetails(Template template,
			String toolIds) {
		int bool = templateMapper.insertSelective(template);
		bool = templateDetailService
				.addTemplateDetails(template.getTemplateId(), toolIds);
		return bool;
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
	public int updateTemplatesAndDetails(Template template,
			String toolIds) {
		int bool = templateDetailService.deleteByTemplate(template);
		bool = templateMapper.updateByPrimaryKeySelective(template);
		bool = templateDetailService
				.addTemplateDetails(template.getTemplateId(), toolIds);
		return bool;
	}

}
