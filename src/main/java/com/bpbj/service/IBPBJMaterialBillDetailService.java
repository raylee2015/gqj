package com.bpbj.service;

import java.util.Map;

import com.bpbj.entity.MaterialBill;

public interface IBPBJMaterialBillDetailService {
	int deleteByMaterialBill(MaterialBill materialBill);

	Map<String, Object> selectMaterialBillDetailsForList(
			MaterialBill materialBill);

	int addMaterialBillDetails(long materialBillId,
			String baseToolIds, String baseToolPosIds,
			String baseToolAmounts);

	Map<String, Object> selectMaterialBillDetailsForPage(
			Map<String, Object> param);

}
