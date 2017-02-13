package com.gqj.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.util.DateUtil;
import com.gqj.dao.MaterialBillMapper;
import com.gqj.entity.MaterialBill;
import com.gqj.service.IMaterialBillDetailService;
import com.gqj.service.IMaterialBillService;
import com.gqj.service.IMaterialInventoryService;

@Service
public class MaterialBillServiceImpl
		implements IMaterialBillService {

	@Autowired
	private MaterialBillMapper materialBillMapper;

	@Autowired
	private IMaterialInventoryService materialInventoryService;

	@Autowired
	private IMaterialBillDetailService materialBillDetailService;

	@Override
	public Map<String, Object> deleteMaterialBillsAndDetails(
			MaterialBill materialBill) {
		Map<String, Object> map = new HashMap<String, Object>();
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
			MaterialBill materialBill, String baseToolIds,
			String baseToolPosIds,
			String detailBillAmounts) {
		Map<String, Object> map = new HashMap<String, Object>();
		int bool = materialBillMapper
				.insertSelective(materialBill);
		bool = materialBillDetailService
				.addMaterialBillDetails(
						materialBill.getBillId(),
						baseToolIds, baseToolPosIds,
						detailBillAmounts);
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
		// 转化日期
		for (Map<String, Object> item : materialBills) {
			if (item.get("BILL_CREATE_TIME") != null) {
				item.put("BILL_CREATE_TIME",
						DateUtil.getDate(
								item.get("BILL_CREATE_TIME")
										.toString()));
			}
			if (item.get("BILL_CONFIRM_TIME") != null) {
				item.put("BILL_CONFIRM_TIME",
						DateUtil.getDate(item
								.get("BILL_CONFIRM_TIME")
								.toString()));
			}
			if (item.get("BILL_TAKE_TIME") != null) {
				item.put("BILL_TAKE_TIME",
						DateUtil.getDate(
								item.get("BILL_TAKE_TIME")
										.toString()));
			}
		}
		int count = materialBillMapper
				.selectCountOfMaterialBillsForPage(
						materialBill);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", materialBills);
		map.put("total", count);
		return map;
	}

	@Override
	public Map<String, Object> updateMaterialBillsAndDetails(
			MaterialBill materialBill, String baseToolIds,
			String baseToolPosIds,
			String detailBillAmounts) {
		materialBill.setIds(
				materialBill.getBillId().toString());
		int bool = materialBillDetailService
				.deleteByMaterialBill(materialBill);
		bool = materialBillMapper
				.updateByPrimaryKeySelective(materialBill);
		bool = materialBillDetailService
				.addMaterialBillDetails(
						materialBill.getBillId(),
						baseToolIds, baseToolPosIds,
						detailBillAmounts);
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

	@Override
	public Map<String, Object> takeMaterialBills(
			MaterialBill materialBill) {
		int bool = materialBillMapper
				.updateByPrimaryKeySelective(materialBill);
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

	@SuppressWarnings("unchecked")
	@Override
	public synchronized Map<String, Object> confirmMaterialBillsAndDetails(
			MaterialBill materialBill) {
		// a-修改单据
		materialBillMapper
				.updateByPrimaryKeySelective(materialBill);
		// b-修改库存
		// 1.查询单据明细
		Map<String, Object> materialDetailMap = materialBillDetailService
				.selectMaterialBillDetailsForList(
						materialBill);
		ArrayList<HashMap<String, Object>> materialBillDetailList = (ArrayList<HashMap<String, Object>>) materialDetailMap
				.get("rows");
		// 2.修改库存
		Map<String, Object> map = materialInventoryService
				.updateMaterialInventory(materialBill,
						materialBillDetailList);
		return map;
	}

}
