package com.bpbj.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.util.DateUtil;
import com.bpbj.dao.BPBJToolTrackMapper;
import com.bpbj.entity.PlugInTrack;
import com.bpbj.service.IBPBJToolTrackService;

@Service
public class BPBJToolTrackServiceImpl implements IBPBJToolTrackService {

	@Autowired
	private BPBJToolTrackMapper toolTrackMapper;

	@Override
	public Map<String, Object> deleteToolTracks(PlugInTrack toolTrack) {
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
	public Map<String, Object> addNewToolTrack(PlugInTrack toolTrack) {
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
			PlugInTrack toolTrack) {
		List<Map<String, Object>> toolTracks = toolTrackMapper
				.selectToolTracksForPage(toolTrack);
		for (Map<String, Object> item : toolTracks) {
			if (item.get("TRACK_CREATE_TIME") != null) {
				item.put("TRACK_CREATE_TIME", DateUtil.getDate(
						item.get("TRACK_CREATE_TIME").toString()));
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
	public Map<String, Object> updateToolTrack(PlugInTrack toolTrack) {
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
	public PlugInTrack selectToolTracksForObject(PlugInTrack toolTrack) {
		return toolTrackMapper.selectToolTracksForObject(toolTrack);
	}

	@Override
	public List<PlugInTrack> selectToolTracksForList(
			PlugInTrack toolTrack) {
		return toolTrackMapper.selectToolTracksForList(toolTrack);
	}

}
