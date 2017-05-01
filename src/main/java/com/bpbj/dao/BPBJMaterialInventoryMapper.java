package com.bpbj.dao;

import java.util.List;
import java.util.Map;

import com.bpbj.entity.MaterialInventory;

public interface BPBJMaterialInventoryMapper {
	int deleteByPrimaryKeys(
			MaterialInventory materialInventory);

	int insertSelective(
			MaterialInventory materialInventory);

	List<Map<String, Object>> selectMaterialInventorysForPage(
			Map<String, Object> param);

	List<Map<String, Object>> selectMaterialInventorysForList(
			MaterialInventory materialInventory);

	int selectCountOfMaterialInventorysForPage(
			Map<String, Object> param);

	int updateByPrimaryKeySelective(
			MaterialInventory materialInventory);
}