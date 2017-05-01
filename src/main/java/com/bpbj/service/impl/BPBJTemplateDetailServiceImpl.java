package com.bpbj.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.util.BaseUtil;
import com.bpbj.dao.BPBJTemplateDetailMapper;
import com.bpbj.entity.Template;
import com.bpbj.entity.TemplateDetail;
import com.bpbj.service.IBPBJTemplateDetailService;

@Service
public class BPBJTemplateDetailServiceImpl
		implements IBPBJTemplateDetailService {

	@Autowired
	private BPBJTemplateDetailMapper templateDetailMapper;

	@Override
	public int deleteByTemplate(Template template) {
		return templateDetailMapper.deleteByTemplate(template);
	}

	@Override
	public int addTemplateDetails(long templateId, String toolIds) {
		String[] toolId_arr = toolIds.split(",");
		int bool = 0;
		for (int i = 0; i < toolId_arr.length; i++) {
			TemplateDetail templateDetail = new TemplateDetail();
			templateDetail.setDetailId(-1l);
			templateDetail.setTemplateId(templateId);
			templateDetail.setToolId(BaseUtil.strToLong(toolId_arr[i]));
			bool = templateDetailMapper.insertSelective(templateDetail);
		}
		return bool;
	}

	@Override
	public Map<String, Object> selectTemplateDetailsForPage(
			TemplateDetail template) {
		List<Map<String, Object>> templates = templateDetailMapper
				.selectTemplateDetailsForPage(template);
		int count = templateDetailMapper
				.selectCountOfTemplateDetailsForPage(template);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", templates);
		map.put("total", count);
		return map;
	}

	@Override
	public Map<String, Object> selectTemplateDetailsForList(
			Template template) {
		List<Map<String, Object>> templateDetails = templateDetailMapper
				.selectTemplateDetailsForList(template);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", templateDetails);
		map.put("total", templateDetails.size());
		return map;
	}

	@Override
	public int updateTemplateDetail(TemplateDetail template) {
		return templateDetailMapper
				.updateByPrimaryKeySelective(template);
	}

}
