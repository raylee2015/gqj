package com.bpbj.dao;

import java.util.List;
import java.util.Map;

import com.bpbj.entity.AccessoryBill;
import com.bpbj.entity.AccessoryBillDetail;

public interface BPBJAccessoryBillDetailMapper {
	List<Map<String, Object>> selectAccessoryBillDetailsForList(
			AccessoryBill accessoryBill);

	List<Map<String, Object>> selectAccessoryBillDetailsForPage(
			Map<String, Object> param);

	int selectCountOfAccessoryBillDetailsForPage(
			Map<String, Object> param);

	int deleteByAccessoryBill(AccessoryBill accessoryBill);

	int insertSelective(
			AccessoryBillDetail accessoryBillDetail);

	int updateByPrimaryKeySelective(
			AccessoryBillDetail accessoryBillDetail);
}