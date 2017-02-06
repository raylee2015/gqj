package com.gqj.dao;

import java.util.List;
import java.util.Map;

import com.gqj.entity.Tool;
import com.gqj.entity.ToolTrack;

public interface ToolTrackMapper {
	int deleteByPrimaryKeys(Tool tool);

	List<Map<String, Object>> selectToolTracksForPage(
			ToolTrack toolTrack);

	Map<String, Object> selectToolTracksForObject(ToolTrack toolTrack);

	int selectCountOfToolTracksForPage(ToolTrack toolTrack);

	int insertSelective(ToolTrack toolTrack);

	int updateByPrimaryKeySelective(ToolTrack toolTrack);
}