package com.bpbj.service;

import java.util.Map;

import com.bpbj.entity.Station;

public interface IBPBJStationService {
	Map<String, Object> deleteStations(
			Station station);

	Map<String, Object> addNewStation(Station station);

	Map<String, Object> updateStation(
			Station station);

	Map<String, Object> selectStationsForPage(
			Station station);

}
