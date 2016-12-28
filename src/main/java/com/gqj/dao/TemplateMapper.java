package com.gqj.dao;

import java.util.List;
import java.util.Map;

import com.gqj.entity.Template;

public interface TemplateMapper {
	int deleteByPrimaryKeys(Template template);

	List<Map<String, Object>> selectTemplatesForPage(Template template);

	int selectCountOfTemplatesForPage(Template template);

	int insertSelective(Template template);

	int updateByPrimaryKeySelective(Template template);
}