package com.bpbj.service;

import java.util.Map;

import com.bpbj.entity.Template;

public interface IBPBJTemplateService {
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
