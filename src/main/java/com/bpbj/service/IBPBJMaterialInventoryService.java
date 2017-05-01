package com.bpbj.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.bpbj.entity.MaterialBill;

public interface IBPBJMaterialInventoryService {
	Map<String, Object> updateMaterialInventory(
			MaterialBill materialBill,
			ArrayList<HashMap<String, Object>> materialBillDetailList);

	Map<String, Object> selectMaterialInventorysForPage(
			Map<String, Object> param);

}
