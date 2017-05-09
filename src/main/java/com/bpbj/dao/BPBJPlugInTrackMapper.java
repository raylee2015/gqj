package com.bpbj.dao;

import java.util.List;
import java.util.Map;

import com.bpbj.entity.PlugInTrack;

public interface BPBJPlugInTrackMapper {
	int deleteByPrimaryKeys(PlugInTrack plugInTrack);

	List<Map<String, Object>> selectPlugInTracksForPage(
			PlugInTrack plugInTrack);

	List<PlugInTrack> selectPlugInTracksForList(PlugInTrack plugInTrack);

	PlugInTrack selectPlugInTracksForObject(PlugInTrack plugInTrack);

	int selectCountOfPlugInTracksForPage(PlugInTrack plugInTrack);

	int insertSelective(PlugInTrack plugInTrack);

	int updateByPrimaryKeySelective(PlugInTrack plugInTrack);
}