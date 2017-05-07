package com.bpbj.service;

import java.util.Map;

import com.bpbj.entity.Batch;
import com.bpbj.entity.PlugIn;
import com.bpbj.entity.PlugInTrack;

public interface IBPBJBatchService {
	Map<String, Object> deleteBatchs(Batch batch);

	Map<String, Object> addNewBatchsAndDetails(Batch batch,
			PlugIn tool, PlugInTrack toolTrack);

	Map<String, Object> updateBatch(Batch batch);

	Map<String, Object> selectBatchsForPage(Batch batch);

	Batch selectBatchsForObject(Batch batch);

	Map<String, Object> delToolAndTrack(PlugIn tool,
			PlugInTrack toolTrack, Batch batch);

	Map<String, Object> confirmBatchs(Batch batch);

	Map<String, Object> updateBatchs(Batch batch);
}
