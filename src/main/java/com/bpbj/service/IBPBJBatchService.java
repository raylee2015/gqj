package com.bpbj.service;

import java.util.Map;

import com.bpbj.entity.Batch;
import com.bpbj.entity.Tool;
import com.bpbj.entity.ToolTrack;

public interface IBPBJBatchService {
	Map<String, Object> deleteBatchs(Batch batch);

	Map<String, Object> addNewBatchsAndDetails(Batch batch,
			Tool tool, ToolTrack toolTrack);

	Map<String, Object> updateBatch(Batch batch);

	Map<String, Object> selectBatchsForPage(Batch batch);

	Batch selectBatchsForObject(Batch batch);

	Map<String, Object> delToolAndTrack(Tool tool,
			ToolTrack toolTrack, Batch batch);

	Map<String, Object> confirmBatchs(Batch batch);

	Map<String, Object> updateBatchs(Batch batch);
}
