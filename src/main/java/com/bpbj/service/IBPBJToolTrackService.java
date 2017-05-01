package com.bpbj.service;

import java.util.List;
import java.util.Map;

import com.bpbj.entity.ToolTrack;

public interface IBPBJToolTrackService {
	Map<String, Object> deleteToolTracks(
			ToolTrack toolTrack);

	Map<String, Object> addNewToolTrack(
			ToolTrack toolTrack);

	Map<String, Object> updateToolTrack(
			ToolTrack toolTrack);

	Map<String, Object> selectToolTracksForPage(
			ToolTrack toolTrack);

	ToolTrack selectToolTracksForObject(
			ToolTrack toolTrack);

	List<ToolTrack> selectToolTracksForList(
			ToolTrack toolTrack);

}
