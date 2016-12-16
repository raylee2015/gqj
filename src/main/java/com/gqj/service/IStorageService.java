package com.gqj.service;

import java.util.Map;

import com.gqj.entity.Storage;

public interface IStorageService {
	int deleteByPrimaryKeys(Storage storage);

	int insertSelective(Storage storage);

	int updateByPrimaryKeySelective(Storage storage);

	Map<String, Object> selectStoragesForPage(Storage storage);

}
