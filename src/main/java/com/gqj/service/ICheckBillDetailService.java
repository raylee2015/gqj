package com.gqj.service;

import java.util.Map;

import com.gqj.entity.CheckBill;
import com.gqj.entity.CheckBillDetail;

public interface ICheckBillDetailService {
	int deleteByCheckBill(CheckBill checkBill);

	Map<String, Object> selectCheckBillDetailsForList(CheckBill checkBill);

	int addCheckBillDetails(CheckBill checkBill);

	Map<String, Object> selectCheckBillDetailsForPage(Map<String, Object> param);

	Map<String, Object> updateCheckBillDetail(CheckBillDetail checkBillDetail);

}
