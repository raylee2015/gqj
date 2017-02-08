package com.gqj.service;

import java.util.Map;

import com.gqj.entity.Batch;
import com.gqj.entity.Tool;
import com.gqj.entity.ToolTrack;

public interface IBatchService {
	Map<String, Object> deleteBatchs(Batch batch);

	Map<String, Object> addNewBatchsAndDetails(Batch batch, Tool tool,
			ToolTrack toolTrack);

	Map<String, Object> updateBatch(Batch batch);

	Map<String, Object> selectBatchsForPage(Batch batch);

}
