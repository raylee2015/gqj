package com.bpbj.service;

import java.util.List;
import java.util.Map;

import com.bpbj.entity.PlugInTrack;

public interface IBPBJPlugInTrackService {
	Map<String, Object> deletePlugInTracks(
			PlugInTrack plugInTrack);

	Map<String, Object> addNewPlugInTrack(
			PlugInTrack plugInTrack);

	Map<String, Object> updatePlugInTrack(
			PlugInTrack plugInTrack);

	Map<String, Object> selectPlugInTracksForPage(
			PlugInTrack plugInTrack);

	PlugInTrack selectPlugInTracksForObject(
			PlugInTrack plugInTrack);

	List<PlugInTrack> selectPlugInTracksForList(
			PlugInTrack plugInTrack);

}
