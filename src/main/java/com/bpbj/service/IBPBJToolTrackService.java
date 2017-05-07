package com.bpbj.service;

import java.util.List;
import java.util.Map;

import com.bpbj.entity.PlugInTrack;

public interface IBPBJToolTrackService {
	Map<String, Object> deleteToolTracks(
			PlugInTrack toolTrack);

	Map<String, Object> addNewToolTrack(
			PlugInTrack toolTrack);

	Map<String, Object> updateToolTrack(
			PlugInTrack toolTrack);

	Map<String, Object> selectToolTracksForPage(
			PlugInTrack toolTrack);

	PlugInTrack selectToolTracksForObject(
			PlugInTrack toolTrack);

	List<PlugInTrack> selectToolTracksForList(
			PlugInTrack toolTrack);

}
