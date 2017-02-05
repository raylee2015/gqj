package com.gqj.service;

import java.util.Map;

import com.gqj.entity.MaterialBill;
import com.gqj.entity.MaterialBillDetail;

public interface IMaterialBillDetailService {
	int deleteByMaterialBill(MaterialBill materialBill);

	Map<String, Object> selectMaterialBillDetailsForList(
			MaterialBill materialBill);

	int addMaterialBillDetails(long materialBillId,
			String baseToolIds, String baseToolPosIds,
			String baseToolAmounts);

	Map<String, Object> selectMaterialBillDetailsForPage(
			Map<String, Object> param);

}
