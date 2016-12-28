package com.gqj.service;

import java.util.Map;

import com.gqj.entity.Template;
import com.gqj.entity.TemplateDetail;

public interface ITemplateDetailService {
	int deleteByTemplate(Template template);

	int addTemplateDetails(long templateId, String toolIds);

	int updateByPrimaryKeySelective(TemplateDetail template);

	Map<String, Object> selectTemplateDetailsForPage(
			TemplateDetail template);

	Map<String, Object> selectTemplateDetailsForList(Template template);

}
