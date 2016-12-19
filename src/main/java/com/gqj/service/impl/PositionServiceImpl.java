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
public class PositionServiceImpl implements IPositionService {

	@Autowired
	private PositionMapper positionMapper;

	@Override
	public int deleteByPrimaryKeys(Position position) {
		return positionMapper.deleteByPrimaryKeys(position);
	}

	@Override
	public int insertSelective(Position position) {
		return positionMapper.insertSelective(position);
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
	public int updateByPrimaryKeySelective(Position position) {
		return positionMapper.updateByPrimaryKeySelective(position);
	}

	@Override
	public Map<String, Object> selectPositionsForObject(
			Position position) {
		return positionMapper.selectPositionsForObject(position);
	}

}
