package com.bpbj.dao;

import java.util.List;
import java.util.Map;

import com.bpbj.entity.Storage;

public interface BPBJStorageMapper {
	int deleteByPrimaryKeys(Storage storage);

	List<Map<String, Object>> selectStoragesForPage(Storage storage);

	int selectCountOfStoragesForPage(Storage storage);

	int insertSelective(Storage storage);

	int updateByPrimaryKeySelective(Storage storage);
}