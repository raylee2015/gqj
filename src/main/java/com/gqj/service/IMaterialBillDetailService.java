package com.gqj.service;

import java.util.Map;

import com.gqj.entity.MaterialBill;

public interface IMaterialBillDetailService {
	int deleteByMaterialBill(MaterialBill materialBill);

	int addMaterialBillDetails(long materialBillId,
			String tools);

//	int updateMaterialBillDetail(
//			MaterialBillDetail materialBill);
//
//	Map<String, Object> selectMaterialBillDetailsForPage(
//			MaterialBillDetail materialBill);

	Map<String, Object> selectMaterialBillDetailsForList(
			MaterialBill materialBill);

}
