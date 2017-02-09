package com.gqj.service;

import java.util.Map;

import com.gqj.entity.ToolTrack;

public interface IToolTrackService {
	Map<String, Object> deleteToolTracks(ToolTrack toolTrack);

	Map<String, Object> addNewToolTrack(ToolTrack toolTrack);

	Map<String, Object> updateToolTrack(ToolTrack toolTrack);

	Map<String, Object> selectToolTracksForPage(ToolTrack toolTrack);

	Map<String, Object> selectToolTracksForObject(ToolTrack toolTrack);

}
