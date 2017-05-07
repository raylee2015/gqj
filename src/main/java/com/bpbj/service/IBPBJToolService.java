package com.bpbj.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bpbj.entity.Batch;
import com.bpbj.entity.PlugIn;
import com.bpbj.entity.PlugInTrack;

public interface IBPBJToolService {
	Map<String, Object> deleteTools(PlugIn tool);

	Map<String, Object> addNewTool(PlugIn tool);

	Map<String, Object> updateTool(PlugIn tool);

	Map<String, Object> selectToolsForPage(
			HashMap<String, Object> param);

	Map<String, Object> checkInTool(Batch batch,
			PlugIn tool, PlugInTrack toolTrack);

	Map<String, Object> resetTool(PlugIn tool,
			PlugInTrack toolTrack);

	Map<String, Object> checkOutTool(Batch batch, PlugIn tool,
			PlugInTrack toolTrack);

	Map<String, Object> updateToolByBatch(PlugIn tool);

	List<PlugIn> selectToolsForList(PlugIn tool);

	Map<String, Object> exchangeTool(Batch batch,
			PlugIn toolParam, PlugInTrack toolTrack);

	Map<String, Object> backTool(Batch batch, PlugIn toolParam,
			PlugInTrack toolTrack);

	Map<String, Object> rejectTool(Batch batch, PlugIn toolParam,
			PlugInTrack toolTrack);

	Map<String, Object> borrowTool(Batch batch, PlugIn toolParam,
			PlugInTrack toolTrack);

	Map<String, Object> useTool(Batch batch, PlugIn toolParam,
			PlugInTrack toolTrack);

	Map<String, Object> selfRetrunTool(Batch batch,
			PlugIn toolParam, PlugInTrack toolTrack);

}
