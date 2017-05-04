package com.gqj.service;

import java.io.IOException;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.base.admin.entity.User;
import com.gqj.entity.Check;

import net.sf.jxls.exception.ParsePropertyException;

public interface ICheckService {
	Map<String, Object> deleteChecksAndDetails(
			Check check);

	Map<String, Object> updateChecksAndDetails(
			Check check, String baseToolIds,
			String baseToolPosIds,
			String detailBillAmounts);

	Map<String, Object> selectChecksForPage(
			Check check);

	Map<String, Object> addChecksAndDetails(
			Check check, String baseToolIds,
			String baseToolPosIds, String baseToolAmounts);

	Map<String, Object> confirmChecksAndDetails(
			Check check);

	Map<String, Object> takeChecks(
			Check check);

	Map<String, Object> exportTools(
			Check check,User user)
			throws ParsePropertyException,
			InvalidFormatException, IOException;

}
