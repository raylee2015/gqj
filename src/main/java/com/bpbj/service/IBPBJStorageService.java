package com.bpbj.service;

import java.util.Map;

import com.bpbj.entity.Storage;

public interface IBPBJStorageService {
	Map<String, Object> deleteStorages(
			Storage storage);

	Map<String, Object> addNewStorage(Storage storage);

	Map<String, Object> updateStorage(
			Storage storage);

	Map<String, Object> selectStoragesForPage(
			Storage storage);

}
