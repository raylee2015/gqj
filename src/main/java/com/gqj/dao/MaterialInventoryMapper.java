package com.gqj.dao;

import java.util.List;
import java.util.Map;

import com.gqj.entity.MaterialInventory;

public interface MaterialInventoryMapper {
	int deleteByPrimaryKeys(
			MaterialInventory materialInventory);

	int insertSelective(
			MaterialInventory materialInventory);

	List<Map<String, Object>> selectMaterialInventorysForPage(
			MaterialInventory materialInventory);
	
	List<Map<String, Object>> selectMaterialInventorysForList(
			MaterialInventory materialInventory);

	int selectCountOfMaterialInventorysForPage(
			MaterialInventory materialInventory);

	int updateByPrimaryKeySelective(
			MaterialInventory materialInventory);
}