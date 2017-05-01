package com.bpbj.service;

import java.util.Map;

import com.bpbj.entity.Position;

public interface IBPBJPositionService {
	Map<String, Object> deletePositions(
			Position position);

	Map<String, Object> addNewPosition(Position position);

	Map<String, Object> updatePosition(
			Position position);

	Map<String, Object> selectPositionsForPage(
			Position position);

	Map<String, Object> selectPositionsForObject(
			Position position);

}
