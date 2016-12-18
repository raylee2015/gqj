package com.gqj.dao;

import java.util.List;
import java.util.Map;

import com.gqj.entity.Position;

public interface PositionMapper {
	int deleteByPrimaryKeys(Position position);

	List<Map<String, Object>> selectPositionsForPage(Position position);

	int selectCountOfPositionsForPage(Position position);

	int insertSelective(Position position);

	int updateByPrimaryKeySelective(Position position);
}