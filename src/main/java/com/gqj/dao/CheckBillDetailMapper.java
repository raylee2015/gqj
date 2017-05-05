package com.gqj.dao;

import java.util.List;
import java.util.Map;

import com.gqj.entity.CheckBill;
import com.gqj.entity.CheckBillDetail;

public interface CheckBillDetailMapper {
	List<Map<String, Object>> selectCheckBillDetailsForList(
			CheckBill checkBill);

	List<Map<String, Object>> selectCheckBillDetailsForPage(
			Map<String, Object> param);

	int selectCountOfCheckBillDetailsForPage(
			Map<String, Object> param);

	int deleteByCheckBill(CheckBill checkBill);

	int insertSelective(
			CheckBillDetail checkBillDetail);

	int updateByPrimaryKeySelective(
			CheckBillDetail checkBillDetail);
}