package com.gqj.service;

import java.util.Map;

import com.gqj.entity.Storage;

public interface IStorageService {
	Map<String, Object> deleteStorages(
			Storage storage);

	Map<String, Object> addNewStorage(Storage storage);

	Map<String, Object> updateStorage(
			Storage storage);

	Map<String, Object> selectStoragesForPage(
			Storage storage);

}
