package com.bpbj.dao;

import java.util.List;
import java.util.Map;

import com.bpbj.entity.Template;
import com.bpbj.entity.TemplateDetail;

public interface BPBJTemplateDetailMapper {

	List<Map<String, Object>> selectTemplateDetailsForList(
			Template template);

	List<Map<String, Object>> selectTemplateDetailsForPage(
			TemplateDetail templateDetail);

	int selectCountOfTemplateDetailsForPage(
			TemplateDetail templateDetail);

	int deleteByTemplate(Template template);

	int insertSelective(TemplateDetail templateDetail);

	int updateByPrimaryKeySelective(
			TemplateDetail templateDetail);
}