package com.gqj.service;

import java.io.IOException;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.gqj.entity.MaterialBill;

import net.sf.jxls.exception.ParsePropertyException;

public interface IMaterialBillService {
	Map<String, Object> deleteMaterialBillsAndDetails(
			MaterialBill materialBill);

	Map<String, Object> updateMaterialBillsAndDetails(
			MaterialBill materialBill, String baseToolIds,
			String baseToolPosIds,
			String detailBillAmounts);

	Map<String, Object> selectMaterialBillsForPage(
			MaterialBill materialBill);

	Map<String, Object> addMaterialBillsAndDetails(
			MaterialBill materialBill, String baseToolIds,
			String baseToolPosIds, String baseToolAmounts);

	Map<String, Object> confirmMaterialBillsAndDetails(
			MaterialBill materialBill);

	Map<String, Object> takeMaterialBills(
			MaterialBill materialBill);

	Map<String, Object> exportTools(
			MaterialBill materialBill)
			throws ParsePropertyException,
			InvalidFormatException, IOException;

}
