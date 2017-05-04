package com.bpbj.dao;

import java.util.List;
import java.util.Map;

import com.bpbj.entity.AccessoryInventory;

public interface BPBJAccessoryInventoryMapper {
	int deleteByPrimaryKeys(
			AccessoryInventory accessoryInventory);

	int insertSelective(
			AccessoryInventory accessoryInventory);

	List<Map<String, Object>> selectAccessoryInventorysForPage(
			Map<String, Object> param);

	List<Map<String, Object>> selectAccessoryInventorysForList(
			AccessoryInventory accessoryInventory);

	int selectCountOfAccessoryInventorysForPage(
			Map<String, Object> param);

	int updateByPrimaryKeySelective(
			AccessoryInventory accessoryInventory);
}