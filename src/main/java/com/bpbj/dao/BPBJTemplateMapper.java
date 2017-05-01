package com.bpbj.dao;

import java.util.List;
import java.util.Map;

import com.bpbj.entity.Template;

public interface BPBJTemplateMapper {
	int deleteByPrimaryKeys(Template template);

	List<Map<String, Object>> selectTemplatesForPage(Template template);
	
	List<Map<String, Object>> selectTemplatesForList(
			Template template);

	int selectCountOfTemplatesForPage(Template template);

	int insertSelective(Template template);

	int updateByPrimaryKeySelective(Template template);
}