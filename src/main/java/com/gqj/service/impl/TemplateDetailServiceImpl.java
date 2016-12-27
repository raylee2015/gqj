package com.gqj.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gqj.dao.TemplateDetailMapper;
import com.gqj.entity.TemplateDetail;
import com.gqj.service.ITemplateDetailService;

@Service
public class TemplateDetailServiceImpl
		implements ITemplateDetailService {

	@Autowired
	private TemplateDetailMapper templateMapper;

	@Override
	public int deleteByPrimaryKeys(TemplateDetail template) {
		return templateMapper.deleteByPrimaryKeys(template);
	}

	@Override
	public int insertSelective(TemplateDetail template) {
		return templateMapper.insertSelective(template);
	}

	@Override
	public Map<String, Object> selectTemplateDetailsForPage(
			TemplateDetail template) {
		List<Map<String, Object>> templates = templateMapper
				.selectTemplateDetailsForPage(template);
		int count = templateMapper
				.selectCountOfTemplateDetailsForPage(template);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", templates);
		map.put("total", count);
		return map;
	}

	@Override
	public int updateByPrimaryKeySelective(TemplateDetail template) {
		return templateMapper.updateByPrimaryKeySelective(template);
	}

}
