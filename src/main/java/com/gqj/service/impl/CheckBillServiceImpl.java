package com.gqj.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.util.DateUtil;
import com.gqj.dao.CheckBillMapper;
import com.gqj.entity.CheckBill;
import com.gqj.service.ICheckBillDetailService;
import com.gqj.service.ICheckBillService;

@Service
public class CheckBillServiceImpl implements ICheckBillService {

	@Autowired
	private CheckBillMapper checkBillMapper;

	@Autowired
	private ICheckBillDetailService checkBillDetailService;

	@Override
	public Map<String, Object> deleteCheckBillsAndDetails(CheckBill checkBill) {
		Map<String, Object> map = new HashMap<String, Object>();
		int bool = checkBillDetailService.deleteByCheckBill(checkBill);
		bool = checkBillMapper.deleteByPrimaryKeys(checkBill);
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
	public Map<String, Object> addCheckBillsAndDetails(CheckBill checkBill) {
		Map<String, Object> map = new HashMap<String, Object>();
		int bool = checkBillMapper.insertSelective(checkBill);
		bool = checkBillDetailService.addCheckBillDetails(checkBill);
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
	public Map<String, Object> updateCheckBill(CheckBill checkBill) {
		Map<String, Object> map = new HashMap<String, Object>();
		int bool = checkBillMapper.updateByPrimaryKeySelective(checkBill);
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
	public Map<String, Object> selectCheckBillsForPage(CheckBill checkBill) {
		List<Map<String, Object>> checkBills = checkBillMapper.selectCheckBillsForPage(checkBill);
		// 转化日期
		for (Map<String, Object> item : checkBills) {
			if (item.get("BILL_CREATE_TIME") != null) {
				item.put("BILL_CREATE_TIME", DateUtil.getDate(item.get("BILL_CREATE_TIME").toString()));
			}
			if (item.get("BILL_CONFIRM_TIME") != null) {
				item.put("BILL_CONFIRM_TIME", DateUtil.getDate(item.get("BILL_CONFIRM_TIME").toString()));
			}
			if (item.get("BILL_TAKE_TIME") != null) {
				item.put("BILL_TAKE_TIME", DateUtil.getDate(item.get("BILL_TAKE_TIME").toString()));
			}
		}
		int count = checkBillMapper.selectCountOfCheckBillsForPage(checkBill);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", checkBills);
		map.put("total", count);
		return map;
	}

}
