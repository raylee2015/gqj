package com.gqj.service;

import java.util.Map;

import com.gqj.entity.Batch;

public interface IBatchService {
	Map<String, Object> deleteBatchs(
			Batch batch);

	Map<String, Object> addNewBatch(Batch batch);

	Map<String, Object> updateBatch(
			Batch batch);

	Map<String, Object> selectBatchsForPage(
			Batch batch);

	Map<String, Object> selectBatchsForObject(
			Batch batch);

}
