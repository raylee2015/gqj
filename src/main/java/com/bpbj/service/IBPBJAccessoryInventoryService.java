package com.bpbj.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.bpbj.entity.AccessoryBill;

public interface IBPBJAccessoryInventoryService {
	Map<String, Object> updateAccessoryInventory(
			AccessoryBill accessoryBill,
			ArrayList<HashMap<String, Object>> accessoryBillDetailList);

	Map<String, Object> selectAccessoryInventorysForPage(
			Map<String, Object> param);

}
