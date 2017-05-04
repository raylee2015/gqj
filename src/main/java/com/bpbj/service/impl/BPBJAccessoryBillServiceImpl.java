package com.bpbj.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.admin.entity.User;
import com.base.util.BaseUtil;
import com.base.util.DateUtil;
import com.bpbj.dao.BPBJAccessoryBillDetailMapper;
import com.bpbj.dao.BPBJAccessoryBillMapper;
import com.bpbj.entity.AccessoryBill;
import com.bpbj.service.IBPBJAccessoryBillDetailService;
import com.bpbj.service.IBPBJAccessoryBillService;
import com.bpbj.service.IBPBJAccessoryInventoryService;
import com.bpbj.service.IBPBJSequenceService;
import com.index.util.BaseSysParam;

import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;

@Service
public class BPBJAccessoryBillServiceImpl
		implements IBPBJAccessoryBillService {

	@Autowired
	private BPBJAccessoryBillMapper accessoryBillMapper;

	@Autowired
	private BPBJAccessoryBillDetailMapper accessoryBillDetailMapper;

	@Autowired
	private IBPBJAccessoryInventoryService accessoryInventoryService;

	@Autowired
	private IBPBJAccessoryBillDetailService accessoryBillDetailService;

	@Override
	public Map<String, Object> deleteAccessoryBillsAndDetails(
			AccessoryBill accessoryBill) {
		Map<String, Object> map = new HashMap<String, Object>();
		int bool = accessoryBillDetailService
				.deleteByAccessoryBill(accessoryBill);
		bool = accessoryBillMapper
				.deleteByPrimaryKeys(accessoryBill);
		if (bool == 0) {
			map.put("success", false);
			map.put("msg", "删除失败，请联系管理员");
		} else {
			map.put("success", true);
			map.put("msg", "删除成功");
		}
		return map;
	}

	@Autowired
	private IBPBJSequenceService sequenceService;

	/**
	 * 查询新的工器具号码
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private String queryNewToolCode(User user) {
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("rule1", user.getUserDeptCode() + "-");
		param.put("rule2", DateUtil.getNow() + "-");
		param.put("rule3", "@");
		param.put("rule4", "@");
		param.put("rule5", "@");
		param.put("seq", "4");
		return sequenceService.selectSequence(param);
	}

	@Override
	public Map<String, Object> exportTools(
			AccessoryBill accessoryBill, User user)
			throws ParsePropertyException,
			InvalidFormatException, IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		String accessoryBillIds = accessoryBill.getIds();
		String[] accessoryBillId_arr = accessoryBillIds
				.split(",");
		int bool = 1;
		ArrayList<String> exportFileList = new ArrayList<String>();
		for (String accessoryBillId : accessoryBillId_arr) {
			AccessoryBill param = new AccessoryBill();
			param.setBillId(
					BaseUtil.strToLong(accessoryBillId));
			Map<String, Object> resultOfAccessoryBill = accessoryBillMapper
					.selectAccessoryBillForObject(param);
			List<Map<String, Object>> resultOfAccessoryBillList = accessoryBillDetailMapper
					.selectAccessoryBillDetailsForList(
							accessoryBill);
			int sequence = 1;
			List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
			for (Map<String, Object> item : resultOfAccessoryBillList) {
				int toolAmount = (int) Double.parseDouble(
						item.get("DETAIL_BILL_AMOUNT")
								.toString());
				for (int i = 0; i < toolAmount; i++) {
					Map<String, Object> temp = new HashMap<String, Object>();
					String newToolCode = queryNewToolCode(
							user);
					temp.put("sequence", sequence++);
					temp.put("TOOL_CODE", newToolCode);
					temp.put("BASE_TOOL_NAME",
							item.get("BASE_TOOL_NAME")
									.toString());
					temp.put("BASE_TOOL_MODEL",
							item.get("BASE_TOOL_MODEL")
									.toString());
					temp.put("BASE_TOOL_SPEC",
							item.get("BASE_TOOL_SPEC")
									.toString());
					temp.put("BASE_TOOL_MANUFACTURER_NAME",
							item.get(
									"BASE_TOOL_MANUFACTURER_NAME")
									.toString());
					temp.put("BASE_TOOL_TYPE_NAME",
							item.get("BASE_TOOL_TYPE_NAME")
									.toString());
					temp.put("BASE_TOOL_DEPT_NAME",
							resultOfAccessoryBill
									.get("BILL_TAKE_DEPT_NAME")
									.toString());
					resultList.add(temp);
				}
			}
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("list", resultList);
			// 生成文件
			XLSTransformer transformer = new XLSTransformer();
			String templatePath = this.getClass()
					.getClassLoader()
					.getResource(
							"/com/bpbj/resources/template/toolCodeList.xls")
					.getPath();
			String targetPath = BaseSysParam
					.getSysRootPath() + "/tempFile/"
					+ resultOfAccessoryBill.get("BILL_CODE")
							.toString()
					+ "-工器具编码列表.xls";
			transformer.transformXLS(templatePath, data,
					targetPath);
			exportFileList.add(targetPath);
		}
		if (bool == 0) {
			map.put("success", false);
			map.put("msg", "导出失败，请联系管理员");
		} else {
			map.put("exportFileList", exportFileList);
			map.put("success", true);
			map.put("msg", "导出成功");
		}
		return map;
	}

	@Override
	public Map<String, Object> addAccessoryBillsAndDetails(
			AccessoryBill accessoryBill, String baseToolIds,
			String baseToolPosIds,
			String detailBillAmounts) {
		Map<String, Object> map = new HashMap<String, Object>();
		int bool = accessoryBillMapper
				.insertSelective(accessoryBill);
		bool = accessoryBillDetailService
				.addAccessoryBillDetails(
						accessoryBill.getBillId(),
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
	public Map<String, Object> selectAccessoryBillsForPage(
			AccessoryBill accessoryBill) {
		List<Map<String, Object>> accessoryBills = accessoryBillMapper
				.selectAccessoryBillsForPage(accessoryBill);
		// 转化日期
		for (Map<String, Object> item : accessoryBills) {
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
		int count = accessoryBillMapper
				.selectCountOfAccessoryBillsForPage(
						accessoryBill);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", accessoryBills);
		map.put("total", count);
		return map;
	}

	@Override
	public Map<String, Object> updateAccessoryBillsAndDetails(
			AccessoryBill accessoryBill, String baseToolIds,
			String baseToolPosIds,
			String detailBillAmounts) {
		accessoryBill.setIds(
				accessoryBill.getBillId().toString());
		int bool = accessoryBillDetailService
				.deleteByAccessoryBill(accessoryBill);
		bool = accessoryBillMapper
				.updateByPrimaryKeySelective(accessoryBill);
		bool = accessoryBillDetailService
				.addAccessoryBillDetails(
						accessoryBill.getBillId(),
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
	public Map<String, Object> takeAccessoryBills(
			AccessoryBill accessoryBill) {
		int bool = accessoryBillMapper
				.updateByPrimaryKeySelective(accessoryBill);
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
	public synchronized Map<String, Object> confirmAccessoryBillsAndDetails(
			AccessoryBill accessoryBill) {
		// a-修改单据
		accessoryBillMapper
				.updateByPrimaryKeySelective(accessoryBill);
		// b-修改库存
		// 1.查询单据明细
		Map<String, Object> accessoryDetailMap = accessoryBillDetailService
				.selectAccessoryBillDetailsForList(
						accessoryBill);
		ArrayList<HashMap<String, Object>> accessoryBillDetailList = (ArrayList<HashMap<String, Object>>) accessoryDetailMap
				.get("rows");
		// 2.修改库存
		Map<String, Object> map = accessoryInventoryService
				.updateAccessoryInventory(accessoryBill,
						accessoryBillDetailList);
		return map;
	}

}
