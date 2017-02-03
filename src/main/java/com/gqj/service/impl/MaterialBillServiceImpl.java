package com.gqj.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gqj.dao.MaterialBillMapper;
import com.gqj.entity.MaterialBill;
import com.gqj.service.IMaterialBillDetailService;
import com.gqj.service.IMaterialBillService;

@Service
public class MaterialBillServiceImpl
		implements IMaterialBillService {

	@Autowired
	private MaterialBillMapper materialBillMapper;

	@Autowired
	private IMaterialBillDetailService materialBillDetailService;

	@Override
	public Map<String, Object> deleteMaterialBillsAndDetails(
			MaterialBill materialBill) {
		Map<String, Object> map = new HashMap<>();
		int bool = materialBillDetailService
				.deleteByMaterialBill(materialBill);
		bool = materialBillMapper
				.deleteByPrimaryKeys(materialBill);
		if (bool == 0) {
			map.put("success", false);
			map.put("msg", "删除失败，请联系管理员");
		} else {
			map.put("success", true);
			map.put("msg", "删除成功");
		}
		return map;
	}

	@Override
	public Map<String, Object> addMaterialBillsAndDetails(
			MaterialBill materialBill, String tools) {
		Map<String, Object> map = new HashMap<String, Object>();
		int bool = materialBillMapper
				.insertSelective(materialBill);
		bool = materialBillDetailService
				.addMaterialBillDetails(
						materialBill.getBillId(), tools);
		if (bool == 0) {
			map.put("success", false);
			map.put("msg", "保存出错，请联系管理员");
		} else {
			map.put("success", true);
			map.put("msg", "保存成功");
		}
		return map;
	}

	@Override
	public Map<String, Object> selectMaterialBillsForPage(
			MaterialBill materialBill) {
		List<Map<String, Object>> materialBills = materialBillMapper
				.selectMaterialBillsForPage(materialBill);
		int count = materialBillMapper
				.selectCountOfMaterialBillsForPage(
						materialBill);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", materialBills);
		map.put("total", count);
		return map;
	}

	@Override
	public Map<String, Object> selectMaterialBillsForList(
			MaterialBill materialBill) {
		// List<Map<String, Object>> materialBills = materialBillMapper
		// .selectMaterialBillsForList(materialBill);
		Map<String, Object> map = new HashMap<String, Object>();
		// map.put("rows", materialBills);
		// map.put("total", materialBills.size());
		return map;
	}

	@Override
	public Map<String, Object> updateMaterialBillsAndDetails(
			MaterialBill materialBill, String tools) {
		int bool = materialBillDetailService
				.deleteByMaterialBill(materialBill);
		bool = materialBillMapper
				.updateByPrimaryKeySelective(materialBill);
		bool = materialBillDetailService
				.addMaterialBillDetails(
						materialBill.getBillId(), tools);
		Map<String, Object> map = new HashMap<String, Object>();
		if (bool == 0) {
			map.put("success", false);
			map.put("msg", "保存出错，请联系管理员");
		} else {
			map.put("success", true);
			map.put("msg", "保存成功");
		}
		return map;
	}

}
