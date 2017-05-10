package com.bpbj.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bpbj.entity.PlugIn;

public interface BPBJPlugInMapper {
	int deleteByPrimaryKeys(PlugIn plugIn);

	List<Map<String, Object>> selectPlugInsForPage(
			HashMap<String, Object> param);

	PlugIn selectPlugInForObject(PlugIn plugIn);

	List<PlugIn> selectPlugInsForList(PlugIn plugIn);

	int selectCountOfPlugInsForPage(
			HashMap<String, Object> param);

	int insertSelective(PlugIn plugIn);

	int updateByPrimaryKeySelective(PlugIn plugIn);

	int updatePlugInByBatch(PlugIn plugIn);

	List<Map<String, Object>> selectPlugInsForPageByBaseTool(HashMap<String, Object> param);

	int selectCountOfPlugInsForPageByBaseTool(HashMap<String, Object> param);
}