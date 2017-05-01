package com.bpbj.dao;

import java.util.List;
import java.util.Map;

import com.bpbj.entity.MaterialBill;
import com.bpbj.entity.MaterialBillDetail;

public interface BPBJMaterialBillDetailMapper {
	List<Map<String, Object>> selectMaterialBillDetailsForList(
			MaterialBill materialBill);

	List<Map<String, Object>> selectMaterialBillDetailsForPage(
			Map<String, Object> param);

	int selectCountOfMaterialBillDetailsForPage(
			Map<String, Object> param);

	int deleteByMaterialBill(MaterialBill materialBill);

	int insertSelective(
			MaterialBillDetail materialBillDetail);

	int updateByPrimaryKeySelective(
			MaterialBillDetail materialBillDetail);
}