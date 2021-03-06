package com.gqj.dao;

import java.util.List;
import java.util.Map;

import com.gqj.entity.ToolTrack;

public interface ToolTrackMapper {
	int deleteByPrimaryKeys(ToolTrack toolTrack);

	List<Map<String, Object>> selectToolTracksForPage(
			ToolTrack toolTrack);

	List<ToolTrack> selectToolTracksForList(ToolTrack toolTrack);

	ToolTrack selectToolTracksForObject(ToolTrack toolTrack);

	int selectCountOfToolTracksForPage(ToolTrack toolTrack);

	int insertSelective(ToolTrack toolTrack);

	int updateByPrimaryKeySelective(ToolTrack toolTrack);
}