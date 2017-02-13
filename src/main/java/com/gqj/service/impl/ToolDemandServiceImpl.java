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
	public Map<String, Object> deleteToolDemands(
			ToolDemand toolDemand) {
		Map<String, Object> map = new HashMap<String, Object>();
		int bool = toolDemandMapper
				.deleteByPrimaryKeys(toolDemand);
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
	public Map<String, Object> addNewToolDemand(
			ToolDemand toolDemand) {
		Map<String, Object> map = new HashMap<String, Object>();
		int bool = toolDemandMapper
				.insertSelective(toolDemand);
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
	public Map<String, Object> updateToolDemand(
			ToolDemand toolDemand) {
		Map<String, Object> map = new HashMap<String, Object>();
		int bool = toolDemandMapper
				.updateByPrimaryKeySelective(toolDemand);
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
