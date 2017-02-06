package com.gqj.dao;

import java.util.List;
import java.util.Map;

import com.gqj.entity.Batch;

public interface BatchMapper {
	int deleteByPrimaryKeys(Batch batch);

	List<Map<String, Object>> selectBatchsForPage(Batch batch);

	Map<String, Object> selectBatchsForObject(Batch batch);

	int selectCountOfBatchsForPage(Batch batch);

	int insertSelective(Batch batch);

	int updateByPrimaryKeySelective(Batch batch);
}