package com.gqj.service;

import java.util.Map;

import com.gqj.entity.CheckBill;

public interface ICheckBillService {
	Map<String, Object> deleteCheckBillsAndDetails(
			CheckBill checkBill);

	Map<String, Object> selectCheckBillsForPage(
			CheckBill checkBill);

	Map<String, Object> addCheckBillsAndDetails(
			CheckBill checkBill);

	Map<String, Object> updateCheckBill(CheckBill checkBill);

}
