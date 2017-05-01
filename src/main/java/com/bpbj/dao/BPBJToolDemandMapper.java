package com.bpbj.dao;

import java.util.List;
import java.util.Map;

import com.bpbj.entity.ToolDemand;

public interface BPBJToolDemandMapper {
	List<Map<String, Object>> selectToolDemandsForPage(
			ToolDemand toolDemand);

	int selectCountOfToolDemandsForPage(
			ToolDemand toolDemand);

	int deleteByPrimaryKeys(ToolDemand toolDemand);

	int insertSelective(ToolDemand toolDemand);

	int updateByPrimaryKeySelective(ToolDemand toolDemand);
}