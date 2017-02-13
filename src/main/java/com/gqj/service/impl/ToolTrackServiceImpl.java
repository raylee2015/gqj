package com.gqj.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.util.DateUtil;
import com.gqj.dao.ToolTrackMapper;
import com.gqj.entity.ToolTrack;
import com.gqj.service.IToolTrackService;

@Service
public class ToolTrackServiceImpl implements IToolTrackService {

	@Autowired
	private ToolTrackMapper toolTrackMapper;

	@Override
	public Map<String, Object> deleteToolTracks(ToolTrack toolTrack) {
		Map<String, Object> map = new HashMap<String, Object>();
		int bool = toolTrackMapper.deleteByPrimaryKeys(toolTrack);
		if (bool == 0) {
			map.put("success", false);
			map.put("msg", "删除失败，请联系管理员");
		} else {
			map.put("success", true);
			map.put("msg", "删除成功");
		}
		return map;
	}

	@Override
	public Map<String, Object> addNewToolTrack(ToolTrack toolTrack) {
		Map<String, Object> map = new HashMap<String, Object>();
		int bool = toolTrackMapper.insertSelective(toolTrack);
		if (bool == 0) {
			map.put("success", false);
			map.put("msg", "保存出错，请联系管理员");
		} else {
			map.put("success", true);
			map.put("msg", "保存成功");
		}
		return map;
	}

	@Override
	public Map<String, Object> selectToolTracksForPage(
			ToolTrack toolTrack) {
		List<Map<String, Object>> toolTracks = toolTrackMapper
				.selectToolTracksForPage(toolTrack);
		for (Map<String, Object> item : toolTracks) {
			if (item.get("BATCH_CONFIRM_TIME") != null) {
				item.put("BATCH_CONFIRM_TIME", DateUtil.getDate(
						item.get("BATCH_CONFIRM_TIME").toString()));
			}
		}
		int count = toolTrackMapper
				.selectCountOfToolTracksForPage(toolTrack);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", toolTracks);
		map.put("total", count);
		return map;
	}

	@Override
	public Map<String, Object> updateToolTrack(ToolTrack toolTrack) {
		Map<String, Object> map = new HashMap<String, Object>();
		int bool = toolTrackMapper
				.updateByPrimaryKeySelective(toolTrack);
		if (bool == 0) {
			map.put("success", false);
			map.put("msg", "保存出错，请联系管理员");
		} else {
			map.put("success", true);
			map.put("msg", "保存成功");
		}
		return map;
	}

	@Override
	public ToolTrack selectToolTracksForObject(ToolTrack toolTrack) {
		return toolTrackMapper.selectToolTracksForObject(toolTrack);
	}

	@Override
	public List<ToolTrack> selectToolTracksForList(
			ToolTrack toolTrack) {
		return toolTrackMapper.selectToolTracksForList(toolTrack);
	}

}
