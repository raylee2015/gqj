package com.gqj.service;

import java.util.Map;

import com.gqj.entity.Position;

public interface IPositionService {
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
