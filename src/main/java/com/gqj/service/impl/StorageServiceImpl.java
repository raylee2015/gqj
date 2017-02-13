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
	public Map<String, Object> deleteStorages(
			Storage storage) {
		Map<String, Object> map = new HashMap<String, Object>();
		int bool = storageMapper
				.deleteByPrimaryKeys(storage);
		if (bool == 0) {
			map.put("success", false);
			map.put("msg", "删除失败，请联系管理员");
		} else {
			map.put("success", true);
			map.put("msg", "删除成功");
		}
		return map;
	}

	@Override
	public Map<String, Object> addNewStorage(
			Storage storage) {
		Map<String, Object> map = new HashMap<String, Object>();
		int bool = storageMapper.insertSelective(storage);
		if (bool == 0) {
			map.put("success", false);
			map.put("msg", "保存出错，请联系管理员");
		} else {
			map.put("success", true);
			map.put("msg", "保存成功");
		}
		return map;
	}

	@Override
	public Map<String, Object> selectStoragesForPage(
			Storage storage) {
		List<Map<String, Object>> storages = storageMapper
				.selectStoragesForPage(storage);
		int count = storageMapper
				.selectCountOfStoragesForPage(storage);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", storages);
		map.put("total", count);
		return map;
	}

	@Override
	public Map<String, Object> updateStorage(
			Storage storage) {
		Map<String, Object> map = new HashMap<String, Object>();
		int bool = storageMapper
				.updateByPrimaryKeySelective(storage);
		if (bool == 0) {
			map.put("success", false);
			map.put("msg", "保存出错，请联系管理员");
		} else {
			map.put("success", true);
			map.put("msg", "保存成功");
		}
		return map;
	}

}
