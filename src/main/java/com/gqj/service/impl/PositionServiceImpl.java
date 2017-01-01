package com.gqj.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gqj.dao.PositionMapper;
import com.gqj.entity.Position;
import com.gqj.service.IPositionService;

@Service
public class PositionServiceImpl
		implements IPositionService {

	@Autowired
	private PositionMapper positionMapper;

	@Override
	public Map<String, Object> deletePositions(
			Position position) {
		Map<String, Object> map = new HashMap<>();
		int bool = positionMapper
				.deleteByPrimaryKeys(position);
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
	public Map<String, Object> addNewPosition(
			Position position) {
		Map<String, Object> map = new HashMap<String, Object>();
		int bool = positionMapper.insertSelective(position);
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
	public Map<String, Object> selectPositionsForPage(
			Position position) {
		List<Map<String, Object>> positions = positionMapper
				.selectPositionsForPage(position);
		int count = positionMapper
				.selectCountOfPositionsForPage(position);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", positions);
		map.put("total", count);
		return map;
	}

	@Override
	public Map<String, Object> updatePosition(
			Position position) {
		Map<String, Object> map = new HashMap<String, Object>();
		int bool = positionMapper
				.updateByPrimaryKeySelective(position);
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
	public Map<String, Object> selectPositionsForObject(
			Position position) {
		return positionMapper
				.selectPositionsForObject(position);
	}

}
