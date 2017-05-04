package com.bpbj.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bpbj.dao.BPBJAccessoryInventoryMapper;
import com.bpbj.entity.AccessoryBill;
import com.bpbj.entity.AccessoryInventory;
import com.bpbj.service.IBPBJAccessoryInventoryService;

@Service
public class BPBJAccessoryInventoryServiceImpl
		implements IBPBJAccessoryInventoryService {

	@Autowired
	private BPBJAccessoryInventoryMapper accessoryInventoryMapper;

	@Override
	public Map<String, Object> selectAccessoryInventorysForPage(
			Map<String, Object> param) {
		List<Map<String, Object>> accessoryInventorys = accessoryInventoryMapper
				.selectAccessoryInventorysForPage(param);
		int count = accessoryInventoryMapper
				.selectCountOfAccessoryInventorysForPage(
						param);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", accessoryInventorys);
		map.put("total", count);
		return map;
	}

	@Override
	public Map<String, Object> updateAccessoryInventory(
			AccessoryBill accessoryBill,
			ArrayList<HashMap<String, Object>> accessoryBillDetailList) {
		Map<String, Object> map = new HashMap<String, Object>();
		int bool = 0;
		for (HashMap<String, Object> accessoryBillDetail : accessoryBillDetailList) {
			// 准备数据
			Long storeId = Long.parseLong(accessoryBillDetail
					.get("STORE_ID").toString());
			Long posId = Long.parseLong(accessoryBillDetail
					.get("POS_ID").toString());
			Long baseToolId = Long.parseLong(
					accessoryBillDetail.get("BASE_TOOL_ID")
							.toString());
			Double detailBillAmount = Double
					.parseDouble(accessoryBillDetail
							.get("DETAIL_BILL_AMOUNT")
							.toString());
			AccessoryInventory accessoryInventory = new AccessoryInventory();
			accessoryInventory.setBaseToolId(baseToolId);
			accessoryInventory.setStoreId(storeId);
			accessoryInventory.setPosId(posId);
			accessoryInventory
					.setInventAmount(detailBillAmount);
			// 查询库存
			List<Map<String, Object>> accessoryInventoryList = accessoryInventoryMapper
					.selectAccessoryInventorysForList(
							accessoryInventory);
			if (accessoryInventoryList.size() == 0) {
				// 增加记录
				bool = accessoryInventoryMapper
						.insertSelective(accessoryInventory);
			} else {
				// 修改记录
				long accessoryBillType = accessoryBill
						.getBillType();
				double amountOfInventory = Double
						.parseDouble(accessoryInventoryList
								.get(0).get("INVENT_AMOUNT")
								.toString());
				double amountOfBill = Double
						.parseDouble(accessoryBillDetail
								.get("DETAIL_BILL_AMOUNT")
								.toString());
				// 当入库、退仓时，增加库存
				if (accessoryBillType == 0
						|| accessoryBillType == 3) {

					accessoryInventory.setInventAmount(
							amountOfInventory
									+ amountOfBill);
				} else {// 当出库、转仓、报废时，减少库存
					accessoryInventory.setInventAmount(
							amountOfInventory
									- amountOfBill);
				}
				bool = accessoryInventoryMapper
						.updateByPrimaryKeySelective(
								accessoryInventory);
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
