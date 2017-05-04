package com.bpbj.service;

import java.util.Map;

import com.bpbj.entity.AccessoryBill;

public interface IBPBJAccessoryBillDetailService {
	int deleteByAccessoryBill(AccessoryBill accessoryBill);

	Map<String, Object> selectAccessoryBillDetailsForList(
			AccessoryBill accessoryBill);

	int addAccessoryBillDetails(long accessoryBillId,
			String baseToolIds, String baseToolPosIds,
			String baseToolAmounts);

	Map<String, Object> selectAccessoryBillDetailsForPage(
			Map<String, Object> param);

}
