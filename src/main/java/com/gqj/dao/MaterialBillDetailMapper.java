package com.gqj.dao;

import java.util.List;
import java.util.Map;

import com.gqj.entity.MaterialBill;
import com.gqj.entity.MaterialBillDetail;

public interface MaterialBillDetailMapper {
	List<Map<String, Object>> selectMaterialBillDetailsForList(
			MaterialBill materialBill);

	int selectCountOfMaterialBillDetailsForPage(
			MaterialBillDetail materialBillDetail);

	int deleteByMaterialBill(MaterialBill materialBill);

	int insertSelective(
			MaterialBillDetail materialBillDetail);

	int updateByPrimaryKeySelective(
			MaterialBillDetail materialBillDetail);
}