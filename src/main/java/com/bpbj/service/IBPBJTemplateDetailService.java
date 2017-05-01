package com.bpbj.service;

import java.util.Map;

import com.bpbj.entity.Template;
import com.bpbj.entity.TemplateDetail;

public interface IBPBJTemplateDetailService {
	int deleteByTemplate(Template template);

	int addTemplateDetails(long templateId, String toolIds);

	int updateTemplateDetail(TemplateDetail template);

	Map<String, Object> selectTemplateDetailsForPage(
			TemplateDetail template);

	Map<String, Object> selectTemplateDetailsForList(Template template);

}
