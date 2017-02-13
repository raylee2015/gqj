package com.gqj.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gqj.entity.Batch;
import com.gqj.entity.Tool;
import com.gqj.entity.ToolTrack;

public interface IToolService {
	Map<String, Object> deleteTools(Tool tool);

	Map<String, Object> addNewTool(Tool tool);

	Map<String, Object> updateTool(Tool tool);

	Map<String, Object> selectToolsForPage(
			HashMap<String, Object> param);

	Map<String, Object> checkInTool(Batch batch,
			Tool tool, ToolTrack toolTrack);

	Map<String, Object> resetTool(Tool tool,
			ToolTrack toolTrack);

	Map<String, Object> checkOutTool(Batch batch, Tool tool,
			ToolTrack toolTrack);

	Map<String, Object> updateToolByBatch(Tool tool);

	List<Tool> selectToolsForList(Tool tool);

	Map<String, Object> exchangeTool(Batch batch,
			Tool toolParam, ToolTrack toolTrack);

	Map<String, Object> backTool(Batch batch, Tool toolParam,
			ToolTrack toolTrack);

	Map<String, Object> rejectTool(Batch batch, Tool toolParam,
			ToolTrack toolTrack);

	Map<String, Object> borrowTool(Batch batch, Tool toolParam,
			ToolTrack toolTrack);

}
