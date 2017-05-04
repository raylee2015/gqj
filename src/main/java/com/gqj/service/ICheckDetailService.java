package com.gqj.service;

import java.util.Map;

import com.gqj.entity.Check;

public interface ICheckDetailService {
	int deleteByCheck(Check check);

	Map<String, Object> selectCheckDetailsForList(
			Check check);

	int addCheckDetails(long checkId,
			String baseToolIds, String baseToolPosIds,
			String baseToolAmounts);

	Map<String, Object> selectCheckDetailsForPage(
			Map<String, Object> param);

}
