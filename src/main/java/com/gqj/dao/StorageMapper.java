package com.gqj.dao;

import java.util.List;
import java.util.Map;

import com.gqj.entity.Storage;

public interface StorageMapper {
	int deleteByPrimaryKeys(Storage storage);

	List<Map<String, Object>> selectStoragesForPage(Storage storage);

	int selectCountOfStoragesForPage(Storage storage);

	int insertSelective(Storage storage);

	int updateByPrimaryKeySelective(Storage storage);
}