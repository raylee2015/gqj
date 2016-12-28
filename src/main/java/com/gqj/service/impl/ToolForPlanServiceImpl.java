package com.gqj.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gqj.dao.ToolForPlanMapper;
import com.gqj.entity.ToolForPlan;
import com.gqj.service.IToolForPlanService;

@Service
public class ToolForPlanServiceImpl implements IToolForPlanService {

	@Autowired
	private ToolForPlanMapper toolForPlanMapper;

	@Override
	public int deleteByPrimaryKeys(ToolForPlan toolForPlan) {
		return toolForPlanMapper.deleteByPrimaryKeys(toolForPlan);
	}

	@Override
	public int insertSelective(ToolForPlan toolForPlan) {
		return toolForPlanMapper.insertSelective(toolForPlan);
	}

	@Override
	public Map<String, Object> selectToolForPlansForPage(
			ToolForPlan toolForPlan) {
		List<Map<String, Object>> toolForPlans = toolForPlanMapper
				.selectToolForPlansForPage(toolForPlan);
		int count = toolForPlanMapper
				.selectCountOfToolForPlansForPage(toolForPlan);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", toolForPlans);
		map.put("total", count);
		return map;
	}

	@Override
	public int updateByPrimaryKeySelective(ToolForPlan toolForPlan) {
		return toolForPlanMapper
				.updateByPrimaryKeySelective(toolForPlan);
	}

}
