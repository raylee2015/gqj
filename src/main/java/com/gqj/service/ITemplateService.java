package com.gqj.service;

import java.util.Map;

import com.gqj.entity.Template;

public interface ITemplateService {
	int deleteByPrimaryKeys(Template template);

	int insertSelective(Template template);

	int updateByPrimaryKeySelective(Template template);

	Map<String, Object> selectTemplatesForPage(Template template);

}
