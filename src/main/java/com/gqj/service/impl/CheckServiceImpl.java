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
import com.gqj.dao.CheckDetailMapper;
import com.gqj.dao.CheckMapper;
import com.gqj.entity.Check;
import com.gqj.service.ICheckDetailService;
import com.gqj.service.ICheckService;
import com.gqj.service.IMaterialInventoryService;
import com.gqj.service.ISequenceService;
import com.index.util.BaseSysParam;

import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;

@Service
public class CheckServiceImpl
		implements ICheckService {

	@Autowired
	private CheckMapper checkMapper;

	@Autowired
	private CheckDetailMapper checkDetailMapper;

	@Autowired
	private IMaterialInventoryService materialInventoryService;

	@Autowired
	private ICheckDetailService checkDetailService;

	@Override
	public Map<String, Object> deleteChecksAndDetails(
			Check check) {
		Map<String, Object> map = new HashMap<String, Object>();
		int bool = checkDetailService
				.deleteByCheck(check);
		bool = checkMapper
				.deleteByPrimaryKeys(check);
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
			Check check, User user)
			throws ParsePropertyException,
			InvalidFormatException, IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		String checkIds = check.getIds();
		String[] checkId_arr = checkIds
				.split(",");
		int bool = 1;
		ArrayList<String> exportFileList = new ArrayList<String>();
		for (String checkId : checkId_arr) {
			Check param = new Check();
			param.setBillId(
					BaseUtil.strToLong(checkId));
			Map<String, Object> resultOfCheck = checkMapper
					.selectCheckForObject(param);
			List<Map<String, Object>> resultOfCheckList = checkDetailMapper
					.selectCheckDetailsForList(
							check);
			int sequence = 1;
			List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
			for (Map<String, Object> item : resultOfCheckList) {
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
							resultOfCheck
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
					+ resultOfCheck.get("BILL_CODE")
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
	public Map<String, Object> addChecksAndDetails(
			Check check, String baseToolIds,
			String baseToolPosIds,
			String detailBillAmounts) {
		Map<String, Object> map = new HashMap<String, Object>();
		int bool = checkMapper
				.insertSelective(check);
		bool = checkDetailService
				.addCheckDetails(
						check.getBillId(),
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
	public Map<String, Object> selectChecksForPage(
			Check check) {
		List<Map<String, Object>> checks = checkMapper
				.selectChecksForPage(check);
		// 转化日期
		for (Map<String, Object> item : checks) {
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
		int count = checkMapper
				.selectCountOfChecksForPage(
						check);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", checks);
		map.put("total", count);
		return map;
	}

	@Override
	public Map<String, Object> updateChecksAndDetails(
			Check check, String baseToolIds,
			String baseToolPosIds,
			String detailBillAmounts) {
		check.setIds(
				check.getBillId().toString());
		int bool = checkDetailService
				.deleteByCheck(check);
		bool = checkMapper
				.updateByPrimaryKeySelective(check);
		bool = checkDetailService
				.addCheckDetails(
						check.getBillId(),
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
	public Map<String, Object> takeChecks(
			Check check) {
		int bool = checkMapper
				.updateByPrimaryKeySelective(check);
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
	public synchronized Map<String, Object> confirmChecksAndDetails(
			Check check) {
		// a-修改单据
		checkMapper
				.updateByPrimaryKeySelective(check);
		// b-修改库存
		// 1.查询单据明细
		Map<String, Object> materialDetailMap = checkDetailService
				.selectCheckDetailsForList(
						check);
		ArrayList<HashMap<String, Object>> checkDetailList = (ArrayList<HashMap<String, Object>>) materialDetailMap
				.get("rows");
		// 2.修改库存
		Map<String, Object> map = materialInventoryService
				.updateMaterialInventory(check,
						checkDetailList);
		return map;
	}

}
