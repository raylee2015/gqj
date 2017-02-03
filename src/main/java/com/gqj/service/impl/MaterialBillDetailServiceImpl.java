package com.gqj.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.gqj.entity.MaterialBill;
import com.gqj.service.IMaterialBillDetailService;

@Service
public class MaterialBillDetailServiceImpl
		implements IMaterialBillDetailService {

	@Override
	public int deleteByMaterialBill(
			MaterialBill materialBill) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int addMaterialBillDetails(long materialBillId,
			String tools) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Map<String, Object> selectMaterialBillDetailsForList(
			MaterialBill materialBill) {
		// TODO Auto-generated method stub
		return null;
	}

	// @Autowired
	// private MaterialBillDetailMapper materialBillDetailMapper;
	//
	// @Override
	// public int deleteByMaterialBill(
	// MaterialBill materialBill) {
	// return materialBillDetailMapper
	// .deleteByMaterialBill(materialBill);
	// }
	//
	// @Override
	// public int addMaterialBillDetails(long materialBillId,
	// String toolIds) {
	// String[] toolId_arr = toolIds.split(",");
	// int bool = 0;
	// for (int i = 0; i < toolId_arr.length; i++) {
	// MaterialBillDetail materialBillDetail = new MaterialBillDetail();
	// materialBillDetail.setDetailId(-1l);
	// materialBillDetail.setBillId(materialBillId);
	// materialBillDetail.setBaseToolId(
	// BaseUtil.strToLong(toolId_arr[i]));
	// bool = materialBillDetailMapper
	// .insertSelective(materialBillDetail);
	// }
	// return bool;
	// }
	//
	// @Override
	// public Map<String, Object> selectMaterialBillDetailsForPage(
	// MaterialBillDetail materialBill) {
	// List<Map<String, Object>> materialBills = materialBillDetailMapper
	// .selectMaterialBillDetailsForPage(
	// materialBill);
	// int count = materialBillDetailMapper
	// .selectCountOfMaterialBillDetailsForPage(
	// materialBill);
	// Map<String, Object> map = new HashMap<String, Object>();
	// map.put("rows", materialBills);
	// map.put("total", count);
	// return map;
	// }
	//
	// @Override
	// public Map<String, Object> selectMaterialBillDetailsForList(
	// MaterialBill materialBill) {
	//// List<Map<String, Object>> materialBillDetails =
	// materialBillDetailMapper
	//// .selectMaterialBillDetailsForList(
	//// materialBill);
	// Map<String, Object> map = new HashMap<String, Object>();
	// // map.put("rows", materialBillDetails);
	// // map.put("total", materialBillDetails.size());
	// return map;
	// }
	//
	// @Override
	// public int updateMaterialBillDetail(
	// MaterialBillDetail materialBill) {
	// return materialBillDetailMapper
	// .updateByPrimaryKeySelective(materialBill);
	// }

}
