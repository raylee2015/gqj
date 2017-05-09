package com.bpbj.service;

import java.util.Map;

import com.bpbj.entity.Batch;
import com.bpbj.entity.PlugIn;
import com.bpbj.entity.PlugInTrack;

public interface IBPBJBatchService {
	Map<String, Object> deleteBatchs(Batch batch);

	Map<String, Object> addNewBatchsAndDetails(Batch batch,
			PlugIn plugIn, PlugInTrack plugInTrack);

	Map<String, Object> updateBatch(Batch batch);

	Map<String, Object> selectBatchsForPage(Batch batch);

	Batch selectBatchsForObject(Batch batch);

	Map<String, Object> delPlugInAndTrack(PlugIn plugIn,
			PlugInTrack plugInTrack, Batch batch);

	Map<String, Object> confirmBatchs(Batch batch);

	Map<String, Object> updateBatchs(Batch batch);
}
