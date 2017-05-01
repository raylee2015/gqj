package com.bpbj.dao;

import java.util.List;
import java.util.Map;

import com.bpbj.entity.MaterialBill;

public interface BPBJMaterialBillMapper {
	int deleteByPrimaryKeys(MaterialBill materialBill);

	int insertSelective(MaterialBill materialBill);

	List<Map<String, Object>> selectMaterialBillsForPage(
			MaterialBill materialBill);

	Map<String, Object> selectMaterialBillForObject(
			MaterialBill materialBill);

	int selectCountOfMaterialBillsForPage(
			MaterialBill materialBill);

	int updateByPrimaryKeySelective(
			MaterialBill materialBill);
}