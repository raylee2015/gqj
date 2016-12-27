package com.gqj.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gqj.dao.TemplateMapper;
import com.gqj.entity.Template;
import com.gqj.service.ITemplateService;

@Service
public class TemplateServiceImpl implements ITemplateService {

	@Autowired
	private TemplateMapper templateMapper;

	@Override
	public int deleteByPrimaryKeys(Template template) {
		return templateMapper.deleteByPrimaryKeys(template);
	}

	@Override
	public int insertSelective(Template template) {
		return templateMapper.insertSelective(template);
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
	public int updateByPrimaryKeySelective(Template template) {
		return templateMapper.updateByPrimaryKeySelective(template);
	}

}
