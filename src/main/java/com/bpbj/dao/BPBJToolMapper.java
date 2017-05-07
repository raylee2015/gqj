package com.bpbj.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bpbj.entity.PlugIn;

public interface BPBJToolMapper {
	int deleteByPrimaryKeys(PlugIn tool);

	List<Map<String, Object>> selectToolsForPage(
			HashMap<String, Object> param);

	PlugIn selectToolForObject(PlugIn tool);

	List<PlugIn> selectToolsForList(PlugIn tool);

	int selectCountOfToolsForPage(
			HashMap<String, Object> param);

	int insertSelective(PlugIn tool);

	int updateByPrimaryKeySelective(PlugIn tool);

	int updateToolByBatch(PlugIn tool);
}