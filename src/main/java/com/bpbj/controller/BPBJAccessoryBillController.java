package com.bpbj.controller;

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
import com.bpbj.entity.DemandPlan;
import com.bpbj.entity.Manufacturer;
import com.bpbj.entity.AccessoryBill;
import com.bpbj.entity.BaseTool;
import com.bpbj.entity.Position;
import com.bpbj.entity.Storage;
import com.bpbj.entity.ToolType;
import com.bpbj.service.IBPBJBaseToolService;
import com.bpbj.service.IBPBJDemandPlanService;
import com.bpbj.service.IBPBJManufacturerService;
import com.bpbj.service.IBPBJAccessoryBillDetailService;
import com.bpbj.service.IBPBJAccessoryBillService;
import com.bpbj.service.IBPBJAccessoryInventoryService;
import com.bpbj.service.IBPBJPositionService;
import com.bpbj.service.IBPBJSequenceService;
import com.bpbj.service.IBPBJStorageService;
import com.bpbj.service.IBPBJToolTypeService;

@Controller
@RequestMapping("/bpbj/accessory_bill")
public class BPBJAccessoryBillController extends BaseController {
	public static final Logger LOGGER = Logger.getLogger(BPBJAccessoryBillController.class);

	@Autowired
	private IBPBJBaseToolService baseToolService;

	@Autowired
	private IBPBJDemandPlanService demandPlanService;

	@Autowired
	private IDeptService deptService;

	@Autowired
	private IBPBJManufacturerService manufacturerService;

	@Autowired
	private IBPBJAccessoryBillDetailService accessoryBillDetailService;

	@Autowired
	private IBPBJAccessoryBillService accessoryBillService;

	@Autowired
	private IBPBJAccessoryInventoryService accessoryInventoryService;

	@Autowired
	private IBPBJPositionService positionService;

	@Autowired
	private IBPBJSequenceService sequenceService;

	@Autowired
	private IBPBJStorageService storageService;

	@Autowired
	private IBPBJToolTypeService toolTypeService;

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
	public Map<String, Object> exportTools(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String billIds = request.getParameter("BILL_IDS");
		AccessoryBill accessoryBill = new AccessoryBill();
		accessoryBill.setIds(billIds);
		Map<String, Object> result = accessoryBillService.exportTools(accessoryBill, getSessionUser(request, response));
		ArrayList<String> exportFileList = (ArrayList<String>) result.get("exportFileList");
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
	@RequestMapping("/addNewAccessoryBillsAndDetails.do")
	@ResponseBody
	@Transactional
	public Map<String, Object> addNewAccessoryBillsAndDetails(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String accessoryBillCode = request.getParameter("BILL_CODE");
		String accessoryBillRemark = request.getParameter("BILL_REMARK");
		String accessoryBillType = request.getParameter("BILL_TYPE");
		String storeId = request.getParameter("STORE_ID");
		String baseToolIds = request.getParameter("BASE_TOOL_IDS");
		String baseToolPosIds = request.getParameter("BASE_TOOL_POS_IDS");
		String detailBillAmounts = request.getParameter("DETAIL_BILL_AMOUNTS");
		long accessoryBillDeptId = getSessionUser(request, response).getUserDeptId();
		AccessoryBill accessoryBill = new AccessoryBill();
		accessoryBill.setBillId(-1l);
		accessoryBill.setBillCode(accessoryBillCode);
		accessoryBill.setBillRemark(accessoryBillRemark);
		accessoryBill.setStoreId(BaseUtil.strToLong(storeId));
		accessoryBill.setBillCreateUserId(getSessionUser(request, response).getUserId());
		accessoryBill.setBillType(BaseUtil.strToLong(accessoryBillType));
		accessoryBill.setBillDeptId(accessoryBillDeptId);
		accessoryBill.setBillCreateTime(new Date());
		return accessoryBillService.addAccessoryBillsAndDetails(accessoryBill, baseToolIds, baseToolPosIds,
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
	@RequestMapping("/confirmAccessoryBills.do")
	@ResponseBody
	public Map<String, Object> confirmAccessoryBills(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String accessoryBillIds = request.getParameter("BILL_IDS");
		String accessoryBillType = request.getParameter("BILL_TYPE");
		AccessoryBill accessoryBill = new AccessoryBill();
		accessoryBill.setIds(accessoryBillIds);
		accessoryBill.setBillType(BaseUtil.strToLong(accessoryBillType));
		accessoryBill.setBillConfirmTime(new Date());
		accessoryBill.setBillConfirmUserId(getSessionUser(request, response).getUserId());
		return accessoryBillService.confirmAccessoryBillsAndDetails(accessoryBill);
	}

	/**
	 * 删除出入库单据
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delAccessoryBills.do")
	@ResponseBody
	public Map<String, Object> delAccessoryBills(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String accessoryBillIds = request.getParameter("BILL_IDS");
		AccessoryBill accessoryBill = new AccessoryBill();
		accessoryBill.setIds(accessoryBillIds);
		return accessoryBillService.deleteAccessoryBillsAndDetails(accessoryBill);
	}

	/**
	 * 弹出选择备品备件管理操作页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/openChooseBaseToolUI.do", method = RequestMethod.GET)
	public ModelAndView openChooseBaseToolUI(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("/bpbj/accessory_bill/chooseBaseToolUI");
	}

	/**
	 * 弹出选择备品备件管理操作页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/openScanUI.do", method = RequestMethod.GET)
	public ModelAndView openScanUI(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("/bpbj/accessory_bill/scanUI");
	}

	/**
	 * 弹出选择需求计划管理操作页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/openChooseDemandPlanUI.do", method = RequestMethod.GET)
	public ModelAndView openChooseDemandPlanUI(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("/bpbj/accessory_bill/chooseDemandPlanUI");
	}

	/**
	 * 弹出选择部门管理操作页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/openChooseDeptUI.do", method = RequestMethod.GET)
	public ModelAndView openChooseDeptUI(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("/bpbj/accessory_bill/chooseDeptUI");
	}

	/**
	 * 弹出选择库存备品备件管理操作页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/openChooseAccessoryInventoryUI.do", method = RequestMethod.GET)
	public ModelAndView openChooseAccessoryInventoryUI(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("/bpbj/accessory_bill/chooseAccessoryInventoryUI");
	}

	/**
	 * 弹出选择仓位管理操作页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/openChoosePositionUI.do", method = RequestMethod.GET)
	public ModelAndView openChoosePositionUI(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("/bpbj/accessory_bill/choosePositionUI");
	}

	/**
	 * 弹出选择仓库管理操作页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/openChooseStorageUI.do", method = RequestMethod.GET)
	public ModelAndView openChooseStorageUI(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("/bpbj/accessory_bill/chooseStorageUI");
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
	public void queryBaseToolManufacturerDropList(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.getWriter().print(manufacturerService.selectManufacturersForList(new Manufacturer()));
		response.getWriter().flush();
		response.getWriter().close();
	}

	/**
	 * 分页查询备品备件列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryBaseToolsPage.do")
	@ResponseBody
	public Map<String, Object> queryBaseToolsPage(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String keyWord = request.getParameter("keyWord");
		String baseToolModel = request.getParameter("BASE_TOOL_MODEL");
		String baseToolSpec = request.getParameter("BASE_TOOL_SPEC");
		String baseToolType = request.getParameter("BASE_TOOL_TYPE");
		String manId = request.getParameter("MAN_ID");
		BaseTool baseTool = new BaseTool();
		baseTool.setCurrPage(Integer.parseInt(page));
		baseTool.setPageSize(Integer.parseInt(rows));
		baseTool.setKeyWord(keyWord);
		baseTool.setBaseToolType(BaseUtil.strToLong(baseToolType));
		baseTool.setBaseToolModel(baseToolModel);
		baseTool.setBaseToolSpec(baseToolSpec);
		baseTool.setManId(BaseUtil.strToLong(manId));
		return baseToolService.selectBaseToolsForPage(baseTool);
	}

	/**
	 * 分页查询备品备件列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryBaseToolForObject.do")
	@ResponseBody
	public Map<String, Object> queryBaseToolForObject(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String baseToolCode = request.getParameter("BASE_TOOL_CODE");
		BaseTool baseTool = new BaseTool();
		baseTool.setBaseToolCode(baseToolCode);
		return baseToolService.selectBaseToolForObject(baseTool);
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
	public void queryBaseToolTypeDropList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.getWriter().print(toolTypeService.selectToolTypesForList(new ToolType()));
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
	public Map<String, Object> queryDemandPlansPage(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String keyWord = request.getParameter("keyWord");
		DemandPlan demandPlan = new DemandPlan();
		demandPlan.setCurrPage(BaseUtil.strToInt(page));
		demandPlan.setPageSize(BaseUtil.strToInt(rows));
		demandPlan.setKeyWord(keyWord);
		return demandPlanService.selectDemandPlansForPage(demandPlan);
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
	public Map<String, Object> queryDeptsPage(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
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
	@RequestMapping("/queryAccessoryBillDetailsForList.do")
	@ResponseBody
	public Map<String, Object> queryAccessoryBillDetailsForList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String accessoryBillId = request.getParameter("BILL_ID");
		AccessoryBill accessoryBill = new AccessoryBill();
		accessoryBill.setBillId(BaseUtil.strToLong(accessoryBillId));
		return accessoryBillDetailService.selectAccessoryBillDetailsForList(accessoryBill);
	}

	/**
	 * 分页查询出入库单据列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryAccessoryBillsPage.do")
	@ResponseBody
	public Map<String, Object> queryAccessoryBillsPage(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		long accessoryBillDeptId = getSessionUser(request, response).getUserDeptId();
		String keyWord = request.getParameter("keyWord");
		String billType = request.getParameter("BILL_TYPE");
		AccessoryBill accessoryBill = new AccessoryBill();
		accessoryBill.setCurrPage(Integer.parseInt(page));
		accessoryBill.setPageSize(Integer.parseInt(rows));
		accessoryBill.setKeyWord(keyWord);
		accessoryBill.setBillType(BaseUtil.strToLong(billType));
		accessoryBill.setBillDeptId(accessoryBillDeptId);
		return accessoryBillService.selectAccessoryBillsForPage(accessoryBill);
	}

	/**
	 * 分页查询库存备品备件列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryAccessoryInventorysPage.do")
	@ResponseBody
	public Map<String, Object> queryAccessoryInventorysPage(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String keyWord = request.getParameter("keyWord");
		String baseToolTypeId = request.getParameter("BASE_TOOL_TYPE_ID");
		String manufacturerId = request.getParameter("MANUFACTURER_ID");
		String baseToolModel = request.getParameter("BASE_TOOL_MODEL");
		String baseToolSpec = request.getParameter("BASE_TOOL_SPEC");
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("keyWord", keyWord);
		param.put("currPage", page);
		param.put("pageSize", rows);
		param.put("STORE_DEPT_ID", getSessionUser(request, response).getUserDeptId());
		param.put("baseToolTypeId", baseToolTypeId);
		param.put("manufacturerId", manufacturerId);
		param.put("baseToolModel", baseToolModel);
		param.put("baseToolSpec", baseToolSpec);
		return accessoryInventoryService.selectAccessoryInventorysForPage(param);
	}

	/**
	 * 查询新的批次
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/queryNewAccessoryBillCode.do")
	@ResponseBody
	public String queryNewAccessoryBillCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
	public Map<String, Object> queryPositionsPage(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String keyWord = request.getParameter("keyWord");
		String storeId = request.getParameter("STORE_ID");
		Position position = new Position();
		position.setCurrPage(BaseUtil.strToInt(page));
		position.setPageSize(BaseUtil.strToInt(rows));
		position.setKeyWord(keyWord);
		position.setStoreId(BaseUtil.strToLong(storeId));
		return positionService.selectPositionsForPage(position);
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
	public Map<String, Object> queryStoragesPage(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		long storageDeptId = getSessionUser(request, response).getUserDeptId();
		String keyWord = request.getParameter("keyWord");
		Storage storage = new Storage();
		storage.setCurrPage(BaseUtil.strToInt(page));
		storage.setPageSize(BaseUtil.strToInt(rows));
		storage.setStoreDeptId(storageDeptId);
		storage.setKeyWord(keyWord);
		return storageService.selectStoragesForPage(storage);
	}

	/**
	 * 跳转到出入库单据管理首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/index.do", method = RequestMethod.GET)
	public ModelAndView toIndex() {
		return new ModelAndView("/bpbj/accessory_bill/index");
	}

	/**
	 * 更新出入库单据信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateAccessoryBillsAndDetails.do")
	@ResponseBody
	public Map<String, Object> updateAccessoryBillsAndDetails(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String accessoryBillId = request.getParameter("BILL_ID");
		String accessoryBillCode = request.getParameter("BILL_CODE");
		String accessoryBillRemark = request.getParameter("BILL_REMARK");
		String accessoryBillType = request.getParameter("BILL_TYPE");
		String storeId = request.getParameter("STORE_ID");
		String baseToolIds = request.getParameter("BASE_TOOL_IDS");
		String baseToolPosIds = request.getParameter("BASE_TOOL_POS_IDS");
		String detailBillAmounts = request.getParameter("DETAIL_BILL_AMOUNTS");
		long accessoryBillDeptId = getSessionUser(request, response).getUserDeptId();
		AccessoryBill accessoryBill = new AccessoryBill();
		accessoryBill.setBillId(BaseUtil.strToLong(accessoryBillId));
		accessoryBill.setBillCode(accessoryBillCode);
		accessoryBill.setBillRemark(accessoryBillRemark);
		accessoryBill.setStoreId(BaseUtil.strToLong(storeId));
		accessoryBill.setBillCreateUserId(getSessionUser(request, response).getUserId());
		accessoryBill.setBillType(BaseUtil.strToLong(accessoryBillType));
		accessoryBill.setBillDeptId(accessoryBillDeptId);
		accessoryBill.setBillCreateTime(new Date());
		return accessoryBillService.updateAccessoryBillsAndDetails(accessoryBill, baseToolIds, baseToolPosIds,
				detailBillAmounts);
	}

}
