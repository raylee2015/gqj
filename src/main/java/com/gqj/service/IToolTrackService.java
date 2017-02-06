package com.gqj.service;

import java.util.Map;

import com.gqj.entity.Tool;
import com.gqj.entity.ToolTrack;

public interface IToolTrackService {
	Map<String, Object> deleteToolTracks(Tool tool);

	Map<String, Object> addNewToolTrack(ToolTrack toolTrack);

	Map<String, Object> updateToolTrack(ToolTrack toolTrack);

	Map<String, Object> selectToolTracksForPage(ToolTrack toolTrack);

	Map<String, Object> selectToolTracksForObject(ToolTrack toolTrack);

}
