package com.gqj.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.gqj.entity.MaterialBill;

public interface IMaterialInventoryService {
	Map<String, Object> updateMaterialInventory(
			MaterialBill materialBill,
			ArrayList<HashMap<String, Object>> materialBillDetailList);

	Map<String, Object> selectMaterialInventorysForPage(
			Map<String, Object> param);

}
