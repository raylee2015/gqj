package com.bpbj.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bpbj.dao.BPBJToolTypeMapper;
import com.bpbj.entity.ToolType;
import com.bpbj.service.IBPBJToolTypeService;

import net.sf.json.JSONArray;

@Service
public class BPBJToolTypeServiceImpl
		implements IBPBJToolTypeService {

	@Autowired
	private BPBJToolTypeMapper toolTypeMapper;

	@Override
	public Map<String, Object> deleteToolTypes(
			ToolType toolType) {
		Map<String, Object> map = new HashMap<String, Object>();
		int bool = toolTypeMapper
				.deleteByPrimaryKeys(toolType);
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
	public Map<String, Object> addNewToolType(
			ToolType toolType) {
		Map<String, Object> map = new HashMap<String, Object>();
		int bool = toolTypeMapper.insertSelective(toolType);
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
	public Map<String, Object> updateToolType(
			ToolType toolType) {
		Map<String, Object> map = new HashMap<String, Object>();
		int bool = toolTypeMapper
				.updateByPrimaryKeySelective(toolType);
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
	public String selectToolTypesForList(
			ToolType toolType) {
		List<Map<String, Object>> list = toolTypeMapper
				.selectToolTypesForList(toolType);
		String result = "";
		if (list != null && list.size() != 0) {
			result = JSONArray.fromObject(list).toString();
		}
		return result;
	}

}
