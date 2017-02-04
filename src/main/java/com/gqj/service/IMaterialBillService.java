package com.gqj.service;

import java.util.Map;

import com.gqj.entity.MaterialBill;

public interface IMaterialBillService {
	Map<String, Object> deleteMaterialBillsAndDetails(
			MaterialBill materialBill);

	Map<String, Object> updateMaterialBillsAndDetails(
			MaterialBill materialBill, String baseToolIds,
			String baseToolPosIds,
			String detailBillAmounts);

	Map<String, Object> selectMaterialBillsForPage(
			MaterialBill materialBill);

	Map<String, Object> addMaterialBillsAndDetails(
			MaterialBill materialBill, String baseToolIds,
			String baseToolPosIds, String baseToolAmounts);

	Map<String, Object> confirmMaterialBillsAndDetails(
			MaterialBill materialBill);

}
