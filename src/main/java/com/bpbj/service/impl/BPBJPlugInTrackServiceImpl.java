package com.bpbj.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.util.DateUtil;
import com.bpbj.dao.BPBJPlugInTrackMapper;
import com.bpbj.entity.PlugInTrack;
import com.bpbj.service.IBPBJPlugInTrackService;

@Service
public class BPBJPlugInTrackServiceImpl implements IBPBJPlugInTrackService {

	@Autowired
	private BPBJPlugInTrackMapper plugInTrackMapper;

	@Override
	public Map<String, Object> deletePlugInTracks(PlugInTrack plugInTrack) {
		Map<String, Object> map = new HashMap<String, Object>();
		int bool = plugInTrackMapper.deleteByPrimaryKeys(plugInTrack);
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
	public Map<String, Object> addNewPlugInTrack(PlugInTrack plugInTrack) {
		Map<String, Object> map = new HashMap<String, Object>();
		int bool = plugInTrackMapper.insertSelective(plugInTrack);
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
	public Map<String, Object> selectPlugInTracksForPage(
			PlugInTrack plugInTrack) {
		List<Map<String, Object>> plugInTracks = plugInTrackMapper
				.selectPlugInTracksForPage(plugInTrack);
		for (Map<String, Object> item : plugInTracks) {
			if (item.get("TRACK_CREATE_TIME") != null) {
				item.put("TRACK_CREATE_TIME", DateUtil.getDate(
						item.get("TRACK_CREATE_TIME").toString()));
			}
		}
		int count = plugInTrackMapper
				.selectCountOfPlugInTracksForPage(plugInTrack);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", plugInTracks);
		map.put("total", count);
		return map;
	}

	@Override
	public Map<String, Object> updatePlugInTrack(PlugInTrack plugInTrack) {
		Map<String, Object> map = new HashMap<String, Object>();
		int bool = plugInTrackMapper
				.updateByPrimaryKeySelective(plugInTrack);
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
	public PlugInTrack selectPlugInTracksForObject(PlugInTrack plugInTrack) {
		return plugInTrackMapper.selectPlugInTracksForObject(plugInTrack);
	}

	@Override
	public List<PlugInTrack> selectPlugInTracksForList(
			PlugInTrack plugInTrack) {
		return plugInTrackMapper.selectPlugInTracksForList(plugInTrack);
	}

}
