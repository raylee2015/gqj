package com.gqj.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gqj.dao.ToolDemandMapper;
import com.gqj.entity.ToolDemand;
import com.gqj.service.IToolDemandService;

@Service
public class ToolDemandServiceImpl
		implements IToolDemandService {

	@Autowired
	private ToolDemandMapper toolDemandMapper;

	@Override
	public int deleteByPrimaryKeys(ToolDemand toolDemand) {
		return toolDemandMapper
				.deleteByPrimaryKeys(toolDemand);
	}

	@Override
	public int insertSelective(ToolDemand toolDemand) {
		return toolDemandMapper.insertSelective(toolDemand);
	}

	@Override
	public Map<String, Object> selectToolDemandsForPage(
			ToolDemand toolDemand) {
		List<Map<String, Object>> toolDemands = toolDemandMapper
				.selectToolDemandsForPage(toolDemand);
		int count = toolDemandMapper
				.selectCountOfToolDemandsForPage(
						toolDemand);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", toolDemands);
		map.put("total", count);
		return map;
	}

	@Override
	public int updateByPrimaryKeySelective(
			ToolDemand toolDemand) {
		return toolDemandMapper
				.updateByPrimaryKeySelective(toolDemand);
	}

}
