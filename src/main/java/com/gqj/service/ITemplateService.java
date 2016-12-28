package com.gqj.service;

import java.util.Map;

import com.gqj.entity.Template;

public interface ITemplateService {
	int deleteTemplatesAndDetails(Template template);

	int addTemplatesAndDetails(Template template, String toolIds);

	int updateTemplatesAndDetails(Template template, String toolIds);

	Map<String, Object> selectTemplatesForPage(Template template);

}
