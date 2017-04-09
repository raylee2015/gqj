package com.gqj.service.impl;

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
import com.gqj.dao.MaterialBillDetailMapper;
import com.gqj.dao.MaterialBillMapper;
import com.gqj.entity.MaterialBill;
import com.gqj.service.IMaterialBillDetailService;
import com.gqj.service.IMaterialBillService;
import com.gqj.service.IMaterialInventoryService;
import com.gqj.service.ISequenceService;
import com.index.util.BaseSysParam;

import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;

@Service
public class MaterialBillServiceImpl
		implements IMaterialBillService {

	@Autowired
	private MaterialBillMapper materialBillMapper;

	@Autowired
	private MaterialBillDetailMapper materialBillDetailMapper;

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

	@Autowired
	private ISequenceService sequenceService;

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
			MaterialBill materialBill, User user)
			throws ParsePropertyException,
			InvalidFormatException, IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		String materialBillIds = materialBill.getIds();
		String[] materialBillId_arr = materialBillIds
				.split(",");
		int bool = 1;
		ArrayList<String> exportFileList = new ArrayList<String>();
		for (String materialBillId : materialBillId_arr) {
			MaterialBill param = new MaterialBill();
			param.setBillId(
					BaseUtil.strToLong(materialBillId));
			Map<String, Object> resultOfMaterialBill = materialBillMapper
					.selectMaterialBillForObject(param);
			List<Map<String, Object>> resultOfMaterialBillList = materialBillDetailMapper
					.selectMaterialBillDetailsForList(
							materialBill);
			int sequence = 1;
			List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
			for (Map<String, Object> item : resultOfMaterialBillList) {
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
							resultOfMaterialBill
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
							"/com/gqj/resources/template/toolCodeList.xls")
					.getPath();
			String targetPath = BaseSysParam
					.getSysRootPath() + "/tempFile/"
					+ resultOfMaterialBill.get("BILL_CODE")
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
