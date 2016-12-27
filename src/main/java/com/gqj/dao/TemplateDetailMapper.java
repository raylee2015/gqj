package com.gqj.dao;

import java.util.List;
import java.util.Map;

import com.gqj.entity.Template;
import com.gqj.entity.TemplateDetail;

public interface TemplateDetailMapper {

	List<Map<String, Object>> selectTemplateDetailsForList(
			Template template);

	List<Map<String, Object>> selectTemplateDetailsForPage(
			TemplateDetail templateDetail);

	int selectCountOfTemplateDetailsForPage(
			TemplateDetail templateDetail);

	int deleteByPrimaryKeys(TemplateDetail templateDetail);

	int insertSelective(TemplateDetail templateDetail);

	int updateByPrimaryKeySelective(
			TemplateDetail templateDetail);
}