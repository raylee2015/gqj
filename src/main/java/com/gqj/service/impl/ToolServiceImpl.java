package com.gqj.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gqj.dao.ToolMapper;
import com.gqj.entity.Tool;
import com.gqj.service.IToolService;

@Service
public class ToolServiceImpl implements IToolService {

	@Autowired
	private ToolMapper toolMapper;

	@Override
	public Map<String, Object> deleteTools(Tool tool) {
		Map<String, Object> map = new HashMap<>();
		int bool = toolMapper.deleteByPrimaryKeys(tool);
		if (bool == 0) {
			map.put("success", false);
			map.put("msg", "删除失败，请联系管理员");
		} else {
			map.put("success", true);
			map.put("msg", "删除成功");
		}
		return map;
	}

	@Override
	public Map<String, Object> addNewTool(Tool tool) {
		Map<String, Object> map = new HashMap<String, Object>();
		int bool = toolMapper.insertSelective(tool);
		if (bool == 0) {
			map.put("success", false);
			map.put("msg", "保存出错，请联系管理员");
		} else {
			map.put("success", true);
			map.put("msg", "保存成功");
		}
		return map;
	}

	@Override
	public Map<String, Object> selectToolsForPage(Tool tool) {
		List<Map<String, Object>> tools = toolMapper
				.selectToolsForPage(tool);
		int count = toolMapper.selectCountOfToolsForPage(tool);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", tools);
		map.put("total", count);
		return map;
	}

	@Override
	public Map<String, Object> updateTool(Tool tool) {
		Map<String, Object> map = new HashMap<String, Object>();
		int bool = toolMapper.updateByPrimaryKeySelective(tool);
		if (bool == 0) {
			map.put("success", false);
			map.put("msg", "保存出错，请联系管理员");
		} else {
			map.put("success", true);
			map.put("msg", "保存成功");
		}
		return map;
	}

	@Override
	public Map<String, Object> selectToolsForObject(Tool tool) {
		return toolMapper.selectToolsForObject(tool);
	}

}
