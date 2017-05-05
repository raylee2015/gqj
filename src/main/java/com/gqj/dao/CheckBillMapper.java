package com.gqj.dao;

import java.util.List;
import java.util.Map;

import com.gqj.entity.CheckBill;

public interface CheckBillMapper {
	int deleteByPrimaryKeys(CheckBill checkBill);

	int insertSelective(CheckBill checkBill);

	List<Map<String, Object>> selectCheckBillsForPage(
			CheckBill checkBill);

	Map<String, Object> selectCheckBillForObject(
			CheckBill checkBill);

	int selectCountOfCheckBillsForPage(
			CheckBill checkBill);

	int updateByPrimaryKeySelective(
			CheckBill checkBill);
}