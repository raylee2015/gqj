package com.gqj.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gqj.dao.ToolTypeMapper;
import com.gqj.entity.ToolType;
import com.gqj.service.IToolTypeService;

import net.sf.json.JSONArray;

@Service
public class ToolTypeServiceImpl implements IToolTypeService {

	@Autowired
	private ToolTypeMapper toolTypeMapper;

	@Override
	public int deleteByPrimaryKeys(ToolType toolType) {
		return toolTypeMapper.deleteByPrimaryKeys(toolType);
	}

	@Override
	public int insertSelective(ToolType toolType) {
		return toolTypeMapper.insertSelective(toolType);
	}

	@Override
	public Map<String, Object> selectToolTypesForPage(
			ToolType toolType) {
		List<Map<String, Object>> toolTypes = toolTypeMapper
				.selectToolTypesForPage(toolType);
		int count = toolTypeMapper
				.selectCountOfToolTypesForPage(toolType);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", toolTypes);
		map.put("total", count);
		return map;
	}

	@Override
	public int updateByPrimaryKeySelective(ToolType toolType) {
		return toolTypeMapper.updateByPrimaryKeySelective(toolType);
	}

	@Override
	public String selectToolTypesForList(ToolType toolType) {
		List<Map<String, Object>> list = toolTypeMapper
				.selectToolTypesForList(toolType);
		String result = "";
		if (list != null && list.size() != 0) {
			result = JSONArray.fromObject(list).toString();
		}
		return result;
	}

}
