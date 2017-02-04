package com.gqj.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.gqj.entity.MaterialBill;
import com.gqj.entity.MaterialInventory;

public interface IMaterialInventoryService {
	Map<String, Object> deleteMaterialInventorys(
			MaterialInventory materialInventory);

	Map<String, Object> addNewMaterialInventory(
			MaterialInventory materialInventory);

	Map<String, Object> updateMaterialInventory(
			MaterialBill materialBill,
			ArrayList<HashMap<String, Object>> materialBillDetailList);

	Map<String, Object> selectMaterialInventorysForPage(
			MaterialInventory materialInventory);

}
