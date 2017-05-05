package com.gqj.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gqj.dao.CheckBillDetailMapper;
import com.gqj.entity.CheckBill;
import com.gqj.entity.CheckBillDetail;
import com.gqj.entity.Tool;
import com.gqj.service.ICheckBillDetailService;
import com.gqj.service.IToolService;

@Service
public class CheckBillDetailServiceImpl implements ICheckBillDetailService {

	@Autowired
	private CheckBillDetailMapper checkBillDetailMapper;

	@Override
	public int deleteByCheckBill(CheckBill checkBill) {
		return checkBillDetailMapper.deleteByCheckBill(checkBill);
	}

	@Autowired
	private IToolService toolService;

	@Override
	public int addCheckBillDetails(CheckBill checkBill) {
		int bool = 0;
		long toolDeptId = checkBill.getBillDeptId();
		Tool tool = new Tool();
		tool.setToolDeptId(toolDeptId);
		List<Tool> tools = toolService.selectToolsForList(tool);
		for (Tool item : tools) {
			CheckBillDetail checkBillDetail = new CheckBillDetail();
			checkBillDetail.setDetailId(-1L);
			checkBillDetail.setBillId(checkBill.getBillId());
			checkBillDetail.setBaseToolId(item.getToolId());
			checkBillDetail.setBaseToolCode(item.getToolCode());
			checkBillDetail.setBaseToolStatus(0L);
			bool = checkBillDetailMapper.insertSelective(checkBillDetail);
		}
		return bool;
	}

	@Override
	public Map<String, Object> updateCheckBillDetail(CheckBillDetail checkBillDetail) {
		int bool = checkBillDetailMapper.updateByPrimaryKeySelective(checkBillDetail);
		Map<String, Object> map = new HashMap<String, Object>();
		if (bool == 0) {
			map.put("success", false);
			map.put("msg", "检查失败，请联系管理员");
		} else {
			map.put("success", true);
			map.put("msg", "检查结果保存成功");
		}
		return map;
	}

	@Override
	public Map<String, Object> selectCheckBillDetailsForList(CheckBill checkBill) {
		List<Map<String, Object>> checkBillDetails = checkBillDetailMapper.selectCheckBillDetailsForList(checkBill);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", checkBillDetails);
		map.put("total", checkBillDetails.size());
		return map;
	}

	@Override
	public Map<String, Object> selectCheckBillDetailsForPage(Map<String, Object> param) {
		List<Map<String, Object>> checkBillDetails = checkBillDetailMapper.selectCheckBillDetailsForPage(param);
		int total = checkBillDetailMapper.selectCountOfCheckBillDetailsForPage(param);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", checkBillDetails);
		map.put("total", total);
		return map;
	}

}
