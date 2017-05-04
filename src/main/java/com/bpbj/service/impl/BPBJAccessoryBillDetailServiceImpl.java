package com.bpbj.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.util.BaseUtil;
import com.bpbj.dao.BPBJAccessoryBillDetailMapper;
import com.bpbj.entity.AccessoryBill;
import com.bpbj.entity.AccessoryBillDetail;
import com.bpbj.service.IBPBJAccessoryBillDetailService;

@Service
public class BPBJAccessoryBillDetailServiceImpl
		implements IBPBJAccessoryBillDetailService {

	@Autowired
	private BPBJAccessoryBillDetailMapper accessoryBillDetailMapper;

	@Override
	public int deleteByAccessoryBill(
			AccessoryBill accessoryBill) {
		return accessoryBillDetailMapper
				.deleteByAccessoryBill(accessoryBill);
	}

	@Override
	public int addAccessoryBillDetails(long accessoryBillId,
			String baseToolIds, String baseToolPosIds,
			String detailBillAmounts) {
		String[] baseToolId_arr = baseToolIds.split(",");
		String[] baseToolPosId_arr = baseToolPosIds
				.split(",");
		String[] detailBillAmount_arr = detailBillAmounts
				.split(",");
		int bool = 0;
		for (int i = 0; i < baseToolId_arr.length; i++) {
			AccessoryBillDetail accessoryBillDetail = new AccessoryBillDetail();
			accessoryBillDetail.setDetailId(-1l);
			accessoryBillDetail.setBillId(accessoryBillId);
			accessoryBillDetail.setBaseToolId(
					BaseUtil.strToLong(baseToolId_arr[i]));
			accessoryBillDetail.setPosId(BaseUtil
					.strToLong(baseToolPosId_arr[i]));
			accessoryBillDetail.setDetailBillAmount(BaseUtil
					.strToDouble(detailBillAmount_arr[i]));
			bool = accessoryBillDetailMapper
					.insertSelective(accessoryBillDetail);
		}
		return bool;
	}

	@Override
	public Map<String, Object> selectAccessoryBillDetailsForList(
			AccessoryBill accessoryBill) {
		List<Map<String, Object>> accessoryBillDetails = accessoryBillDetailMapper
				.selectAccessoryBillDetailsForList(
						accessoryBill);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", accessoryBillDetails);
		map.put("total", accessoryBillDetails.size());
		return map;
	}

	@Override
	public Map<String, Object> selectAccessoryBillDetailsForPage(
			Map<String, Object> param) {
		List<Map<String, Object>> accessoryBillDetails = accessoryBillDetailMapper
				.selectAccessoryBillDetailsForPage(param);
		int total = accessoryBillDetailMapper
				.selectCountOfAccessoryBillDetailsForPage(
						param);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", accessoryBillDetails);
		map.put("total", total);
		return map;
	}

}
