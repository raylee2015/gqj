package com.gqj.service;

import java.util.Map;

import com.gqj.entity.Batch;
import com.gqj.entity.Tool;
import com.gqj.entity.ToolTrack;

public interface IToolService {
	Map<String, Object> deleteTools(Tool tool);

	Map<String, Object> addNewTool(Tool tool);

	Map<String, Object> updateTool(Tool tool);

	Map<String, Object> selectToolsForPage(Tool tool);

	Map<String, Object> checkInNewTool(Batch batch, Tool tool,
			ToolTrack toolTrack);

	Map<String, Object> resetTool(Tool tool, ToolTrack toolTrack);

	Map<String, Object> checkOutTool(Batch batch, Tool tool,
			ToolTrack toolTrack);

}
