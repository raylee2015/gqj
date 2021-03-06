package com.gqj.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.base.admin.entity.Dept;
import com.base.admin.service.IDeptService;
import com.base.controller.BaseController;
import com.base.util.BaseUtil;
import com.base.util.DateUtil;
import com.gqj.entity.DemandPlan;
import com.gqj.entity.Manufacturer;
import com.gqj.entity.MaterialBill;
import com.gqj.entity.Position;
import com.gqj.entity.Storage;
import com.gqj.entity.ToolType;
import com.gqj.service.IBaseToolService;
import com.gqj.service.IDemandPlanService;
import com.gqj.service.IManufacturerService;
import com.gqj.service.IMaterialBillDetailService;
import com.gqj.service.IMaterialBillService;
import com.gqj.service.IMaterialInventoryService;
import com.gqj.service.IPositionService;
import com.gqj.service.ISequenceService;
import com.gqj.service.IStorageService;
import com.gqj.service.IToolTypeService;

@Controller
@RequestMapping("/gqj/material_bill")
public class MaterialBillController extends BaseController {
	public static final Logger LOGGER = Logger
			.getLogger(MaterialBillController.class);

	@Autowired
	private IBaseToolService baseToolService;

	@Autowired
	private IDemandPlanService demandPlanService;

	@Autowired
	private IDeptService deptService;

	@Autowired
	private IManufacturerService manufacturerService;

	@Autowired
	private IMaterialBillDetailService materialBillDetailService;

	@Autowired
	private IMaterialBillService materialBillService;

	@Autowired
	private IMaterialInventoryService materialInventoryService;

	@Autowired
	private IPositionService positionService;

	@Autowired
	private ISequenceService sequenceService;

	@Autowired
	private IStorageService storageService;

	@Autowired
	private IToolTypeService toolTypeService;

	/**
	 * 导出需求计划
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/exportTools.do")
	public Map<String, Object> exportTools(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String billIds = request.getParameter("BILL_IDS");
		MaterialBill materialBill = new MaterialBill();
		materialBill.setIds(billIds);
		Map<String, Object> result = materialBillService
				.exportTools(materialBill,
						getSessionUser(request, response));
		ArrayList<String> exportFileList = (ArrayList<String>) result
				.get("exportFileList");
		downLoadFile(request, response, exportFileList);
		return null;
	}

	/**
	 * 添加出入库单据信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addNewMaterialBillsAndDetails.do")
	@ResponseBody
	@Transactional
	public Map<String, Object> addNewMaterialBillsAndDetails(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String materialBillCode = request
				.getParameter("BILL_CODE");
		String planId = request.getParameter("PLAN_ID");
		String materialBillRemark = request
				.getParameter("BILL_REMARK");
		String materialBillType = request
				.getParameter("BILL_TYPE");
		String storeId = request.getParameter("STORE_ID");
		String baseToolIds = request
				.getParameter("BASE_TOOL_IDS");
		String baseToolPosIds = request
				.getParameter("BASE_TOOL_POS_IDS");
		String detailBillAmounts = request
				.getParameter("DETAIL_BILL_AMOUNTS");
		String billTakeDeptId = request
				.getParameter("BILL_TAKE_DEPT_ID");
		long materialBillDeptId = getSessionUser(request,
				response).getUserDeptId();
		MaterialBill materialBill = new MaterialBill();
		materialBill.setBillId(-1l);
		materialBill.setBillCode(materialBillCode);
		materialBill.setBillRemark(materialBillRemark);
		materialBill
				.setStoreId(BaseUtil.strToLong(storeId));
		materialBill.setBillCreateUserId(
				getSessionUser(request, response)
						.getUserId());
		materialBill.setBillType(
				BaseUtil.strToLong(materialBillType));
		materialBill.setBillDeptId(materialBillDeptId);
		materialBill.setBillCreateTime(new Date());
		materialBill.setPlanId(BaseUtil.strToLong(planId));
		materialBill.setBillTakeDeptId(
				BaseUtil.strToLong(billTakeDeptId));
		return materialBillService
				.addMaterialBillsAndDetails(materialBill,
						baseToolIds, baseToolPosIds,
						detailBillAmounts);
	}

	/**
	 * 确认出入库单据
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/confirmMaterialBills.do")
	@ResponseBody
	public Map<String, Object> confirmMaterialBills(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String materialBillIds = request
				.getParameter("BILL_IDS");
		String materialBillType = request
				.getParameter("BILL_TYPE");
		MaterialBill materialBill = new MaterialBill();
		materialBill.setIds(materialBillIds);
		materialBill.setBillType(
				BaseUtil.strToLong(materialBillType));
		materialBill.setBillConfirmTime(new Date());
		materialBill.setBillConfirmUserId(
				getSessionUser(request, response)
						.getUserId());
		return materialBillService
				.confirmMaterialBillsAndDetails(
						materialBill);
	}

	/**
	 * 领用出入库单据
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/takeMaterialBills.do")
	@ResponseBody
	public Map<String, Object> takeMaterialBills(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String materialBillIds = request
				.getParameter("BILL_IDS");
		MaterialBill materialBill = new MaterialBill();
		materialBill.setIds(materialBillIds);
		materialBill.setBillTakeTime(new Date());
		materialBill.setBillTakeUserId(
				getSessionUser(request, response)
						.getUserId());
		return materialBillService
				.takeMaterialBills(materialBill);
	}

	/**
	 * 删除出入库单据
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delMaterialBills.do")
	@ResponseBody
	public Map<String, Object> delMaterialBills(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String materialBillIds = request
				.getParameter("BILL_IDS");
		MaterialBill materialBill = new MaterialBill();
		materialBill.setIds(materialBillIds);
		return materialBillService
				.deleteMaterialBillsAndDetails(
						materialBill);
	}

	/**
	 * 弹出选择工器具管理操作页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/openChooseBaseToolUI.do", method = RequestMethod.GET)
	public ModelAndView openChooseBaseToolUI(
			HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView(
				"/gqj/material_bill/chooseBaseToolUI");
	}

	/**
	 * 弹出选择需求计划管理操作页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/openChooseDemandPlanUI.do", method = RequestMethod.GET)
	public ModelAndView openChooseDemandPlanUI(
			HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView(
				"/gqj/material_bill/chooseDemandPlanUI");
	}

	/**
	 * 弹出选择部门管理操作页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/openChooseDeptUI.do", method = RequestMethod.GET)
	public ModelAndView openChooseDeptUI(
			HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView(
				"/gqj/material_bill/chooseDeptUI");
	}

	/**
	 * 弹出选择库存工器具管理操作页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/openChooseMaterialInventoryUI.do", method = RequestMethod.GET)
	public ModelAndView openChooseMaterialInventoryUI(
			HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView(
				"/gqj/material_bill/chooseMaterialInventoryUI");
	}

	/**
	 * 弹出选择仓位管理操作页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/openChoosePositionUI.do", method = RequestMethod.GET)
	public ModelAndView openChoosePositionUI(
			HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView(
				"/gqj/material_bill/choosePositionUI");
	}

	/**
	 * 弹出选择仓库管理操作页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/openChooseStorageUI.do", method = RequestMethod.GET)
	public ModelAndView openChooseStorageUI(
			HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView(
				"/gqj/material_bill/chooseStorageUI");
	}

	/**
	 * 查询下拉列表
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/queryBaseToolManufacturerDropList.do")
	@ResponseBody
	public void queryBaseToolManufacturerDropList(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.getWriter()
				.print(manufacturerService
						.selectManufacturersForList(
								new Manufacturer()));
		response.getWriter().flush();
		response.getWriter().close();
	}

	/**
	 * 分页查询工器具列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryBaseToolsPage.do")
	@ResponseBody
	public Map<String, Object> queryBaseToolsPage(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String keyWord = request.getParameter("keyWord");
		String baseToolTypeId = request
				.getParameter("BASE_TOOL_TYPE_ID");
		String manufacturerId = request
				.getParameter("MANUFACTURER_ID");
		String baseToolModel = request
				.getParameter("BASE_TOOL_MODEL");
		String baseToolSpec = request
				.getParameter("BASE_TOOL_SPEC");
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("keyWord", keyWord);
		param.put("currPage", page);
		param.put("pageSize", rows);
		param.put("baseToolTypeId", baseToolTypeId);
		param.put("manufacturerId", manufacturerId);
		param.put("baseToolModel", baseToolModel);
		param.put("baseToolSpec", baseToolSpec);
		return baseToolService
				.selectBaseToolsForPage(param);
	}

	/**
	 * 查询下拉列表
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/queryBaseToolTypeDropList.do")
	@ResponseBody
	public void queryBaseToolTypeDropList(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.getWriter().print(toolTypeService
				.selectToolTypesForList(new ToolType()));
		response.getWriter().flush();
		response.getWriter().close();
	}

	/**
	 * 分页查询需求计划列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryDemandPlansPage.do")
	@ResponseBody
	public Map<String, Object> queryDemandPlansPage(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String keyWord = request.getParameter("keyWord");
		DemandPlan demandPlan = new DemandPlan();
		demandPlan.setCurrPage(BaseUtil.strToInt(page));
		demandPlan.setPageSize(BaseUtil.strToInt(rows));
		demandPlan.setKeyWord(keyWord);
		return demandPlanService
				.selectDemandPlansForPage(demandPlan);
	}

	/**
	 * 分页查询部门列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryDeptsPage.do")
	@ResponseBody
	public Map<String, Object> queryDeptsPage(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String keyWord = request.getParameter("keyWord");
		Dept dept = new Dept();
		dept.setCurrPage(BaseUtil.strToInt(page));
		dept.setPageSize(BaseUtil.strToInt(rows));
		dept.setKeyWord(keyWord);
		return deptService.selectDeptsForPage(dept);
	}

	/**
	 * 查询出入库单据明细列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryMaterialBillDetailsForList.do")
	@ResponseBody
	public Map<String, Object> queryMaterialBillDetailsForList(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String materialBillId = request
				.getParameter("BILL_ID");
		MaterialBill materialBill = new MaterialBill();
		materialBill.setBillId(
				BaseUtil.strToLong(materialBillId));
		return materialBillDetailService
				.selectMaterialBillDetailsForList(
						materialBill);
	}

	/**
	 * 分页查询出入库单据列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryMaterialBillsPage.do")
	@ResponseBody
	public Map<String, Object> queryMaterialBillsPage(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		long materialBillDeptId = getSessionUser(request,
				response).getUserDeptId();
		String keyWord = request.getParameter("keyWord");
		String billType = request.getParameter("BILL_TYPE");
		MaterialBill materialBill = new MaterialBill();
		materialBill.setCurrPage(Integer.parseInt(page));
		materialBill.setPageSize(Integer.parseInt(rows));
		materialBill.setKeyWord(keyWord);
		if (!"5".equals(billType)) {
			materialBill.setBillType(
					BaseUtil.strToLong(billType));
			materialBill.setBillDeptId(materialBillDeptId);
		} else {
			materialBill.setBillTakeDeptId(
					getSessionUser(request, response)
							.getUserDeptId());
		}
		return materialBillService
				.selectMaterialBillsForPage(materialBill);
	}

	/**
	 * 分页查询库存工器具列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryMaterialInventorysPage.do")
	@ResponseBody
	public Map<String, Object> queryMaterialInventorysPage(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String keyWord = request.getParameter("keyWord");
		String baseToolTypeId = request
				.getParameter("BASE_TOOL_TYPE_ID");
		String manufacturerId = request
				.getParameter("MANUFACTURER_ID");
		String baseToolModel = request
				.getParameter("BASE_TOOL_MODEL");
		String baseToolSpec = request
				.getParameter("BASE_TOOL_SPEC");
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("keyWord", keyWord);
		param.put("currPage", page);
		param.put("pageSize", rows);
		param.put("STORE_DEPT_ID",
				getSessionUser(request, response)
						.getUserDeptId());
		param.put("baseToolTypeId", baseToolTypeId);
		param.put("manufacturerId", manufacturerId);
		param.put("baseToolModel", baseToolModel);
		param.put("baseToolSpec", baseToolSpec);
		return materialInventoryService
				.selectMaterialInventorysForPage(param);
	}

	/**
	 * 查询新的批次
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/queryNewMaterialBillCode.do")
	@ResponseBody
	public String queryNewMaterialBillCode(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String billType = request.getParameter("BILL_TYPE");
		HashMap<String, Object> param = new HashMap<String, Object>();
		String rule1 = "";
		if ("0".equals(billType)) {
			rule1 = "入库-";
		} else if ("1".equals(billType)) {
			rule1 = "出库-";
		} else if ("2".equals(billType)) {
			rule1 = "转仓-";
		} else if ("3".equals(billType)) {
			rule1 = "退仓-";
		} else if ("4".equals(billType)) {
			rule1 = "报废-";
		}
		param.put("rule1", rule1);
		param.put("rule2", DateUtil.getNow() + "-");
		param.put("rule3", "@");
		param.put("rule4", "@");
		param.put("rule5", "@");
		param.put("seq", "4");
		return sequenceService.selectSequence(param);
	}

	/**
	 * 分页查询仓位列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryPositionsPage.do")
	@ResponseBody
	public Map<String, Object> queryPositionsPage(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String keyWord = request.getParameter("keyWord");
		String storeId = request.getParameter("STORE_ID");
		Position position = new Position();
		position.setCurrPage(BaseUtil.strToInt(page));
		position.setPageSize(BaseUtil.strToInt(rows));
		position.setKeyWord(keyWord);
		position.setStoreId(BaseUtil.strToLong(storeId));
		return positionService
				.selectPositionsForPage(position);
	}

	/**
	 * 分页查询仓库列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryStoragesPage.do")
	@ResponseBody
	public Map<String, Object> queryStoragesPage(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		long storageDeptId = getSessionUser(request,
				response).getUserDeptId();
		String keyWord = request.getParameter("keyWord");
		Storage storage = new Storage();
		storage.setCurrPage(BaseUtil.strToInt(page));
		storage.setPageSize(BaseUtil.strToInt(rows));
		storage.setStoreDeptId(storageDeptId);
		storage.setKeyWord(keyWord);
		return storageService
				.selectStoragesForPage(storage);
	}

	/**
	 * 跳转到出入库单据管理首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/index.do", method = RequestMethod.GET)
	public ModelAndView toIndex() {
		return new ModelAndView("/gqj/material_bill/index");
	}

	/**
	 * 更新出入库单据信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateMaterialBillsAndDetails.do")
	@ResponseBody
	public Map<String, Object> updateMaterialBillsAndDetails(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String planId = request.getParameter("PLAN_ID");
		String materialBillId = request
				.getParameter("BILL_ID");
		String materialBillCode = request
				.getParameter("BILL_CODE");
		String materialBillRemark = request
				.getParameter("BILL_REMARK");
		String materialBillType = request
				.getParameter("BILL_TYPE");
		String storeId = request.getParameter("STORE_ID");
		String baseToolIds = request
				.getParameter("BASE_TOOL_IDS");
		String baseToolPosIds = request
				.getParameter("BASE_TOOL_POS_IDS");
		String detailBillAmounts = request
				.getParameter("DETAIL_BILL_AMOUNTS");
		String billTakeDeptId = request
				.getParameter("BILL_TAKE_DEPT_ID");
		long materialBillDeptId = getSessionUser(request,
				response).getUserDeptId();
		MaterialBill materialBill = new MaterialBill();
		materialBill.setBillId(
				BaseUtil.strToLong(materialBillId));
		materialBill.setBillCode(materialBillCode);
		materialBill.setBillRemark(materialBillRemark);
		materialBill
				.setStoreId(BaseUtil.strToLong(storeId));
		materialBill.setBillCreateUserId(
				getSessionUser(request, response)
						.getUserId());
		materialBill.setBillType(
				BaseUtil.strToLong(materialBillType));
		materialBill.setBillDeptId(materialBillDeptId);
		materialBill.setBillCreateTime(new Date());
		materialBill.setPlanId(BaseUtil.strToLong(planId));
		materialBill.setBillTakeDeptId(
				BaseUtil.strToLong(billTakeDeptId));
		return materialBillService
				.updateMaterialBillsAndDetails(materialBill,
						baseToolIds, baseToolPosIds,
						detailBillAmounts);
	}

}
