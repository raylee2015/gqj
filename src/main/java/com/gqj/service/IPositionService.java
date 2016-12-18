package com.gqj.service;

import java.util.Map;

import com.gqj.entity.Position;

public interface IPositionService {
	int deleteByPrimaryKeys(Position position);

	int insertSelective(Position position);

	int updateByPrimaryKeySelective(Position position);

	Map<String, Object> selectPositionsForPage(Position position);

}
