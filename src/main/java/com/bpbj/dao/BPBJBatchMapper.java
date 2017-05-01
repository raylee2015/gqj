package com.bpbj.dao;

import java.util.List;
import java.util.Map;

import com.bpbj.entity.Batch;

public interface BPBJBatchMapper {
	int deleteByPrimaryKeys(Batch batch);

	List<Map<String, Object>> selectBatchsForPage(Batch batch);

	Batch selectBatchsForObject(Batch batch);

	int selectCountOfBatchsForPage(Batch batch);

	int insertSelective(Batch batch);

	int updateByPrimaryKeySelective(Batch batch);
}