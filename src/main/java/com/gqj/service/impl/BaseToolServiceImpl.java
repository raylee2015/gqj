package com.gqj.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gqj.dao.BaseToolMapper;
import com.gqj.entity.BaseTool;
import com.gqj.service.IBaseToolService;

@Service
public class BaseToolServiceImpl
		implements IBaseToolService {

	@Autowired
	private BaseToolMapper baseToolMapper;

	@Override
	public int deleteByPrimaryKeys(BaseTool baseTool) {
		return baseToolMapper.deleteByPrimaryKeys(baseTool);
	}

	@Override
	public int insertSelective(BaseTool baseTool) {
		return baseToolMapper.insertSelective(baseTool);
	}

	@Override
	public Map<String, Object> selectBaseToolsForPage(
			BaseTool baseTool) {
		List<Map<String, Object>> baseTools = baseToolMapper
				.selectBaseToolsForPage(baseTool);
		int count = baseToolMapper
				.selectCountOfBaseToolsForPage(baseTool);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", baseTools);
		map.put("total", count);
		return map;
	}

	@Override
	public int updateByPrimaryKeySelective(
			BaseTool baseTool) {
		return baseToolMapper
				.updateByPrimaryKeySelective(baseTool);
	}

}
