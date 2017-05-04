package com.bpbj.service;

import java.io.IOException;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.base.admin.entity.User;
import com.bpbj.entity.AccessoryBill;

import net.sf.jxls.exception.ParsePropertyException;

public interface IBPBJAccessoryBillService {
	Map<String, Object> deleteAccessoryBillsAndDetails(
			AccessoryBill accessoryBill);

	Map<String, Object> updateAccessoryBillsAndDetails(
			AccessoryBill accessoryBill, String baseToolIds,
			String baseToolPosIds,
			String detailBillAmounts);

	Map<String, Object> selectAccessoryBillsForPage(
			AccessoryBill accessoryBill);

	Map<String, Object> addAccessoryBillsAndDetails(
			AccessoryBill accessoryBill, String baseToolIds,
			String baseToolPosIds, String baseToolAmounts);

	Map<String, Object> confirmAccessoryBillsAndDetails(
			AccessoryBill accessoryBill);

	Map<String, Object> takeAccessoryBills(
			AccessoryBill accessoryBill);

	Map<String, Object> exportTools(
			AccessoryBill accessoryBill,User user)
			throws ParsePropertyException,
			InvalidFormatException, IOException;

}
