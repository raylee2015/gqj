package com.gqj.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.util.BaseUtil;
import com.gqj.dao.MaterialBillDetailMapper;
import com.gqj.entity.MaterialBill;
import com.gqj.entity.MaterialBillDetail;
import com.gqj.service.IMaterialBillDetailService;

@Service
public class MaterialBillDetailServiceImpl
		implements IMaterialBillDetailService {

	@Autowired
	private MaterialBillDetailMapper materialBillDetailMapper;

	@Override
	public int deleteByMaterialBill(
			MaterialBill materialBill) {
		return materialBillDetailMapper
				.deleteByMaterialBill(materialBill);
	}

	@Override
	public int addMaterialBillDetails(long materialBillId,
			String baseToolIds, String baseToolPosIds,
			String detailBillAmounts) {
		String[] baseToolId_arr = baseToolIds.split(",");
		String[] baseToolPosId_arr = baseToolPosIds
				.split(",");
		String[] detailBillAmount_arr = detailBillAmounts
				.split(",");
		int bool = 0;
		for (int i = 0; i < baseToolId_arr.length; i++) {
			MaterialBillDetail materialBillDetail = new MaterialBillDetail();
			materialBillDetail.setDetailId(-1l);
			materialBillDetail.setBillId(materialBillId);
			materialBillDetail.setBaseToolId(
					BaseUtil.strToLong(baseToolId_arr[i]));
			materialBillDetail.setPosId(BaseUtil
					.strToLong(baseToolPosId_arr[i]));
			materialBillDetail.setDetailBillAmount(BaseUtil
					.strToDouble(detailBillAmount_arr[i]));
			bool = materialBillDetailMapper
					.insertSelective(materialBillDetail);
		}
		return bool;
	}

	@Override
	public Map<String, Object> selectMaterialBillDetailsForList(
			MaterialBill materialBill) {
		List<Map<String, Object>> materialBillDetails = materialBillDetailMapper
				.selectMaterialBillDetailsForList(
						materialBill);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", materialBillDetails);
		map.put("total", materialBillDetails.size());
		return map;
	}

	@Override
	public Map<String, Object> selectMaterialBillDetailsForPage(
			Map<String, Object> param) {
		List<Map<String, Object>> materialBillDetails = materialBillDetailMapper
				.selectMaterialBillDetailsForPage(param);
		int total = materialBillDetailMapper
				.selectCountOfMaterialBillDetailsForPage(
						param);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", materialBillDetails);
		map.put("total", total);
		return map;
	}

}
