package com.bpbj.dao;

import java.util.List;
import java.util.Map;

import com.bpbj.entity.PlugInTrack;

public interface BPBJToolTrackMapper {
	int deleteByPrimaryKeys(PlugInTrack toolTrack);

	List<Map<String, Object>> selectToolTracksForPage(
			PlugInTrack toolTrack);

	List<PlugInTrack> selectToolTracksForList(PlugInTrack toolTrack);

	PlugInTrack selectToolTracksForObject(PlugInTrack toolTrack);

	int selectCountOfToolTracksForPage(PlugInTrack toolTrack);

	int insertSelective(PlugInTrack toolTrack);

	int updateByPrimaryKeySelective(PlugInTrack toolTrack);
}