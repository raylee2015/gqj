package com.gqj.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.util.BaseUtil;
import com.gqj.dao.CheckDetailMapper;
import com.gqj.entity.Check;
import com.gqj.entity.CheckDetail;
import com.gqj.service.ICheckDetailService;

@Service
public class CheckDetailServiceImpl
		implements ICheckDetailService {

	@Autowired
	private CheckDetailMapper checkDetailMapper;

	@Override
	public int deleteByCheck(
			Check check) {
		return checkDetailMapper
				.deleteByCheck(check);
	}

	@Override
	public int addCheckDetails(long checkId,
			String baseToolIds, String baseToolPosIds,
			String detailBillAmounts) {
		String[] baseToolId_arr = baseToolIds.split(",");
		String[] baseToolPosId_arr = baseToolPosIds
				.split(",");
		String[] detailBillAmount_arr = detailBillAmounts
				.split(",");
		int bool = 0;
		for (int i = 0; i < baseToolId_arr.length; i++) {
			CheckDetail checkDetail = new CheckDetail();
			checkDetail.setDetailId(-1l);
			checkDetail.setBaseToolId(
					BaseUtil.strToLong(baseToolId_arr[i]));
			checkDetail.setPosId(BaseUtil
					.strToLong(baseToolPosId_arr[i]));
			bool = checkDetailMapper
					.insertSelective(checkDetail);
		}
		return bool;
	}

	@Override
	public Map<String, Object> selectCheckDetailsForList(
			Check check) {
		List<Map<String, Object>> checkDetails = checkDetailMapper
				.selectCheckDetailsForList(
						check);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", checkDetails);
		map.put("total", checkDetails.size());
		return map;
	}

	@Override
	public Map<String, Object> selectCheckDetailsForPage(
			Map<String, Object> param) {
		List<Map<String, Object>> checkDetails = checkDetailMapper
				.selectCheckDetailsForPage(param);
		int total = checkDetailMapper
				.selectCountOfCheckDetailsForPage(
						param);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", checkDetails);
		map.put("total", total);
		return map;
	}

}
