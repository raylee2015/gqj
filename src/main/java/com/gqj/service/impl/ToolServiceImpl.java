package com.gqj.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gqj.dao.ToolMapper;
import com.gqj.dao.ToolTrackMapper;
import com.gqj.entity.Batch;
import com.gqj.entity.Tool;
import com.gqj.entity.ToolTrack;
import com.gqj.service.IToolService;
import com.gqj.util.ToolStatus;

@Service
public class ToolServiceImpl implements IToolService {

	@Autowired
	private ToolMapper toolMapper;
	@Autowired
	private ToolTrackMapper toolTrackMapper;

	@Override
	public Map<String, Object> deleteTools(Tool tool) {
		Map<String, Object> map = new HashMap<>();
		int bool = toolMapper.deleteByPrimaryKeys(tool);
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
	public Map<String, Object> addNewTool(Tool tool) {
		Map<String, Object> map = new HashMap<String, Object>();
		int bool = toolMapper.insertSelective(tool);
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
	public Map<String, Object> checkInNewTool(Batch batch, Tool tool,
			ToolTrack toolTrack) {
		Map<String, Object> map = new HashMap<String, Object>();
		Tool temp = toolMapper.selectToolForObject(tool);
		int bool = 0;
		String msg = "";
		if (temp == null) {
			bool = toolMapper.insertSelective(tool);
			toolTrack.setTrackId(-1L);
			toolTrack.setToolId(tool.getToolId());
			toolTrack.setBatchId(tool.getBatchId());
			toolTrack.setToolStatus(tool.getToolStatus());
			bool = toolTrackMapper.insertSelective(toolTrack);
			if (bool == 0) {
				map.put("success", false);
				map.put("msg", "保存出错，请联系管理员");
			} else {
				map.put("success", true);
				map.put("msg", "保存成功");
			}
		} else {
			long toolStatus = temp.getToolStatus();
			if (toolStatus == ToolStatus.REJECT) {
				msg = "该工器具已经报废";
				map.put("success", false);
				map.put("msg", msg);
			} else if (toolStatus == ToolStatus.ON_ROAD
					|| toolStatus == ToolStatus.BORROW) {
				// 更新tool状态，新增track记录
				bool = toolMapper.updateByPrimaryKeySelective(tool);
				bool = toolTrackMapper.insertSelective(toolTrack);
				if (bool == 0) {
					map.put("success", false);
					map.put("msg", "保存出错，请联系管理员");
				} else {
					map.put("success", true);
					map.put("msg", "保存成功");
				}
			} else if (toolStatus == ToolStatus.CHECK_IN_COMING) {
				msg = "该工器具已经入库";
				map.put("success", false);
				map.put("msg", msg);
			} else {
				msg = "该工器具处于非正常状态，请查证";
				map.put("success", false);
				map.put("msg", msg);
			}
		}
		return map;
	}

	@Override
	public Map<String, Object> selectToolsForPage(Tool tool) {
		List<Map<String, Object>> tools = toolMapper
				.selectToolsForPage(tool);
		int count = toolMapper.selectCountOfToolsForPage(tool);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", tools);
		map.put("total", count);
		return map;
	}

	@Override
	public Map<String, Object> updateTool(Tool tool) {
		Map<String, Object> map = new HashMap<String, Object>();
		int bool = toolMapper.updateByPrimaryKeySelective(tool);
		if (bool == 0) {
			map.put("success", false);
			map.put("msg", "保存出错，请联系管理员");
		} else {
			map.put("success", true);
			map.put("msg", "保存成功");
		}
		return map;
	}

}