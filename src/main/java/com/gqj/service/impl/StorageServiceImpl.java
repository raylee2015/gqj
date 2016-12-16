package com.gqj.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gqj.dao.StorageMapper;
import com.gqj.entity.Storage;
import com.gqj.service.IStorageService;

@Service
public class StorageServiceImpl implements IStorageService {

	@Autowired
	private StorageMapper storageMapper;

	@Override
	public int deleteByPrimaryKeys(Storage storage) {
		return storageMapper.deleteByPrimaryKeys(storage);
	}

	@Override
	public int insertSelective(Storage storage) {
		return storageMapper.insertSelective(storage);
	}

	@Override
	public Map<String, Object> selectStoragesForPage(Storage storage) {
		List<Map<String, Object>> storages = storageMapper
				.selectStoragesForPage(storage);
		int count = storageMapper.selectCountOfStoragesForPage(storage);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", storages);
		map.put("total", count);
		return map;
	}

	@Override
	public int updateByPrimaryKeySelective(Storage storage) {
		return storageMapper.updateByPrimaryKeySelective(storage);
	}

}
