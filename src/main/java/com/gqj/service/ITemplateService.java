package com.gqj.service;

import java.util.Map;

import com.gqj.entity.Template;

public interface ITemplateService {
	Map<String, Object> deleteTemplatesAndDetails(
			Template template);

	Map<String, Object> addTemplatesAndDetails(
			Template template, String toolIds);

	Map<String, Object> updateTemplatesAndDetails(
			Template template, String toolIds);

	Map<String, Object> selectTemplatesForPage(
			Template template);

	Map<String, Object> selectTemplatesForList(
			Template template);

}
