package com.bpbj.dao;

import java.util.List;
import java.util.Map;

import com.bpbj.entity.Position;

public interface BPBJPositionMapper {
	int deleteByPrimaryKeys(Position position);

	List<Map<String, Object>> selectPositionsForPage(Position position);

	Map<String, Object> selectPositionsForObject(Position position);

	int selectCountOfPositionsForPage(Position position);

	int insertSelective(Position position);

	int updateByPrimaryKeySelective(Position position);
}