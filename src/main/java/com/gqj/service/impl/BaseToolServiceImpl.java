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
	public Map<String, Object> deleteBaseTools(
			BaseTool baseTool) {
		Map<String, Object> map = new HashMap<>();
		int bool = baseToolMapper
				.deleteByPrimaryKeys(baseTool);
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
	public Map<String, Object> addNewBaseTool(
			BaseTool baseTool) {
		Map<String, Object> map = new HashMap<String, Object>();
		int bool = baseToolMapper.insertSelective(baseTool);
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
	public Map<String, Object> updateBaseTool(
			BaseTool baseTool) {
		Map<String, Object> map = new HashMap<String, Object>();
		int bool = baseToolMapper
				.updateByPrimaryKeySelective(baseTool);
		if (bool == 0) {
			map.put("success", false);
			map.put("msg", "保存出错，请联系管理员");
		} else {
			map.put("success", true);
			map.put("msg", "保存成功");
		}
		return map;
	}

}
