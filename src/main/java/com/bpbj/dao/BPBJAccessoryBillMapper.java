package com.bpbj.dao;

import java.util.List;
import java.util.Map;

import com.bpbj.entity.AccessoryBill;

public interface BPBJAccessoryBillMapper {
	int deleteByPrimaryKeys(AccessoryBill accessoryBill);

	int insertSelective(AccessoryBill accessoryBill);

	List<Map<String, Object>> selectAccessoryBillsForPage(
			AccessoryBill accessoryBill);

	Map<String, Object> selectAccessoryBillForObject(
			AccessoryBill accessoryBill);

	int selectCountOfAccessoryBillsForPage(
			AccessoryBill accessoryBill);

	int updateByPrimaryKeySelective(
			AccessoryBill accessoryBill);
}