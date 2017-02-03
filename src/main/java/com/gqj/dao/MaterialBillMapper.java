package com.gqj.dao;

import java.util.List;
import java.util.Map;

import com.gqj.entity.MaterialBill;

public interface MaterialBillMapper {
	int deleteByPrimaryKeys(MaterialBill materialBill);

	int insertSelective(MaterialBill materialBill);

	List<Map<String, Object>> selectMaterialBillsForPage(
			MaterialBill materialBill);

	int selectCountOfMaterialBillsForPage(
			MaterialBill materialBill);

	int updateByPrimaryKeySelective(
			MaterialBill materialBill);
}