package com.gqj.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gqj.dao.MaterialInventoryMapper;
import com.gqj.entity.MaterialBill;
import com.gqj.entity.MaterialInventory;
import com.gqj.service.IMaterialInventoryService;

@Service
public class MaterialInventoryServiceImpl
		implements IMaterialInventoryService {

	@Autowired
	private MaterialInventoryMapper materialInventoryMapper;

	@Override
	public Map<String, Object> selectMaterialInventorysForPage(
			Map<String, Object> param) {
		List<Map<String, Object>> materialInventorys = materialInventoryMapper
				.selectMaterialInventorysForPage(param);
		int count = materialInventoryMapper
				.selectCountOfMaterialInventorysForPage(
						param);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", materialInventorys);
		map.put("total", count);
		return map;
	}

	@Override
	public Map<String, Object> updateMaterialInventory(
			MaterialBill materialBill,
			ArrayList<HashMap<String, Object>> materialBillDetailList) {
		Map<String, Object> map = new HashMap<String, Object>();
		int bool = 0;
		for (HashMap<String, Object> materialBillDetail : materialBillDetailList) {
			// 准备数据
			Long storeId = Long.parseLong(materialBillDetail
					.get("STORE_ID").toString());
			Long posId = Long.parseLong(materialBillDetail
					.get("POS_ID").toString());
			Long baseToolId = Long.parseLong(
					materialBillDetail.get("BASE_TOOL_ID")
							.toString());
			Double detailBillAmount = Double
					.parseDouble(materialBillDetail
							.get("DETAIL_BILL_AMOUNT")
							.toString());
			MaterialInventory materialInventory = new MaterialInventory();
			materialInventory.setBaseToolId(baseToolId);
			materialInventory.setStoreId(storeId);
			materialInventory.setPosId(posId);
			materialInventory
					.setInventAmount(detailBillAmount);
			// 查询库存
			List<Map<String, Object>> materialInventoryList = materialInventoryMapper
					.selectMaterialInventorysForList(
							materialInventory);
			if (materialInventoryList.size() == 0) {
				// 增加记录
				bool = materialInventoryMapper
						.insertSelective(materialInventory);
			} else {
				// 修改记录
				long materialBillType = materialBill
						.getBillType();
				// 当入库、退仓时，增加库存
				if (materialBillType == 0
						|| materialBillType == 3) {
					materialInventory.setInventAmount(
							materialInventory
									.getInventAmount()
									+ Double.parseDouble(
											materialBillDetail
													.get("DETAIL_BILL_AMOUNT")
													.toString()));
				} else {// 当出库、转仓、报废时，减少库存
					materialInventory.setInventAmount(
							materialInventory
									.getInventAmount()
									- Double.parseDouble(
											materialBillDetail
													.get("DETAIL_BILL_AMOUNT")
													.toString()));
				}
				bool = materialInventoryMapper
						.updateByPrimaryKeySelective(
								materialInventory);
			}
		}

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
