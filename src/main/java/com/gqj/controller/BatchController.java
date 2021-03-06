package com.gqj.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.base.admin.entity.Dept;
import com.base.admin.entity.User;
import com.base.admin.service.IDeptService;
import com.base.admin.service.IUserService;
import com.base.controller.BaseController;
import com.base.util.BaseUtil;
import com.base.util.DateStyle;
import com.base.util.DateUtil;
import com.gqj.entity.Batch;
import com.gqj.entity.Manufacturer;
import com.gqj.entity.Position;
import com.gqj.entity.Storage;
import com.gqj.entity.Tool;
import com.gqj.entity.ToolTrack;
import com.gqj.entity.ToolType;
import com.gqj.service.IBaseToolService;
import com.gqj.service.IBatchService;
import com.gqj.service.IManufacturerService;
import com.gqj.service.IPositionService;
import com.gqj.service.ISequenceService;
import com.gqj.service.IStorageService;
import com.gqj.service.IToolTrackService;
import com.gqj.service.IToolTypeService;
import com.gqj.util.BatchType;
import com.gqj.util.ToolStatus;

@Controller
@RequestMapping("/gqj/tool_batch")
public class BatchController extends BaseController {
	public static final Logger LOGGER = Logger
			.getLogger(BatchController.class);

	@Autowired
	private IBaseToolService baseToolService;

	@Autowired
	private IBatchService batchService;

	@Autowired
	private IToolTrackService toolTrackService;

	@Autowired
	private IDeptService deptService;

	@Autowired
	private IManufacturerService manufacturerService;

	@Autowired
	private IPositionService positionService;

	@Autowired
	private ISequenceService sequenceService;

	@Autowired
	private IStorageService storageService;

	@Autowired
	private IToolTypeService toolTypeService;

	/**
	 * 添加仓位信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addNewBatchsAndDetails.do")
	@ResponseBody
	public Map<String, Object> addNewBatchsAndDetails(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String batchCode = request.getParameter("BATCH_CODE");
		String batchType = request.getParameter("BATCH_TYPE");
		String storeId = request.getParameter("STORE_ID");
		String batchReturnUserId = request
				.getParameter("BATCH_RETURN_USER_ID");
		String posId = request.getParameter("POS_ID");
		String storeName = request.getParameter("STORE_NAME");
		String posName = request.getParameter("POS_NAME");
		String baseToolId = request.getParameter("BASE_TOOL_ID");
		String baseToolName = request.getParameter("BASE_TOOL_NAME");
		String baseToolManName = request
				.getParameter("BASE_TOOL_MANUFACTURER_NAME");
		String baseToolTypeId = request
				.getParameter("BASE_TOOL_TYPE_ID");
		String baseToolTypeName = request
				.getParameter("BASE_TOOL_TYPE_NAME");
		String baseToolModel = request.getParameter("BASE_TOOL_MODEL");
		String baseToolSpec = request.getParameter("BASE_TOOL_SPEC");
		String batchRemark = request.getParameter("BATCH_REMARK");
		String batchTakeDeptId = request
				.getParameter("BATCH_TAKE_DEPT_ID");
		String toolCode = request.getParameter("TOOL_CODE");
		String toolBox = request.getParameter("TOOL_BOX");
		String toolTestDate = request.getParameter("TOOL_TEST_DATE");
		String toolRejectDate = request
				.getParameter("TOOL_REJECT_DATE");
		String toolManufactureDate = request
				.getParameter("TOOL_MANUFACTURE_DATE");
		String toolPurchaseDate = request
				.getParameter("TOOL_PURCHASE_DATE");
		String toolTestDateCircle = request
				.getParameter("TOOL_TEST_DATE_CIRCLE");
		Batch batch = new Batch();
		batch.setBatchCode(batchCode);
		batch.setBatchType(BaseUtil.strToLong(batchType));
		batch.setBatchDeptId(
				getSessionUser(request, response).getUserDeptId());
		batch.setBatchCreateUserId(
				getSessionUser(request, response).getUserId());
		batch.setBatchCreateTime(new Date());
		batch.setBatchTakeDeptId(BaseUtil.strToLong(batchTakeDeptId));
		batch.setBatchRemark(batchRemark);
		batch.setBatchReturnUserId(
				BaseUtil.strToLong(batchReturnUserId));
		Tool tool = new Tool();
		tool.setToolCode(toolCode);
		if (storeId != null && storeId != "") {
			tool.setStoreId(BaseUtil.strToLong(storeId));
		}
		if (posId != null && posId != "") {
			tool.setPosId(BaseUtil.strToLong(posId));
		}
		if (toolBox != null && toolBox != "") {
			tool.setToolBox(toolBox);
		}
		tool.setToolDeptId(
				getSessionUser(request, response).getUserDeptId());
		if (toolTestDate != null && toolTestDate != "") {
			tool.setToolTestDate(DateUtil.StringToDate(toolTestDate,
					DateStyle.YYYY_MM_DD));
		}
		if (toolRejectDate != null && toolRejectDate != "") {
			tool.setToolRejectDate(DateUtil.StringToDate(toolRejectDate,
					DateStyle.YYYY_MM_DD));
		}
		if (toolTestDateCircle != null && toolTestDateCircle != "") {
			tool.setToolTestDateCircle(
					Double.parseDouble(toolTestDateCircle));
			tool.setToolNextTestDate(DateUtil.addMonth(
					DateUtil.StringToDate(toolTestDate,
							DateStyle.YYYY_MM_DD),
					Integer.parseInt(toolTestDateCircle)));
		}
		if (toolManufactureDate != null && toolManufactureDate != "") {
			tool.setToolManufactureDate(DateUtil.StringToDate(
					toolManufactureDate, DateStyle.YYYY_MM_DD));
		}
		if (toolPurchaseDate != null && toolPurchaseDate != "") {
			tool.setToolPurchaseDate(DateUtil.StringToDate(
					toolPurchaseDate, DateStyle.YYYY_MM_DD));
		}
		if (baseToolId != null && baseToolId != "") {
			tool.setBaseToolId(BaseUtil.strToLong(baseToolId));
		}
		tool.setToolRemark(batchRemark);

		ToolTrack toolTrack = new ToolTrack();
		toolTrack.setBatchCode(batchCode);
		toolTrack.setToolCode(toolCode);
		if (storeId != null && storeId != "") {
			toolTrack.setStoreId(BaseUtil.strToLong(storeId));
		}
		if (posId != null && posId != "") {
			toolTrack.setPosId(BaseUtil.strToLong(posId));
		}
		if (toolBox != null && toolBox != "") {
			toolTrack.setToolBox(toolBox);
		}
		toolTrack.setTrackCreateUserId(
				getSessionUser(request, response).getUserId());
		toolTrack.setTrackCreateTime(new Date());
		if (baseToolId != null && baseToolId != "") {
			toolTrack.setBaseToolId(BaseUtil.strToLong(baseToolId));
		}
		if (toolTestDate != null && toolTestDate != "") {
			toolTrack.setToolTestDate(DateUtil
					.StringToDate(toolTestDate, DateStyle.YYYY_MM_DD));
		}
		if (toolRejectDate != null && toolRejectDate != "") {
			toolTrack.setToolRejectDate(DateUtil.StringToDate(
					toolRejectDate, DateStyle.YYYY_MM_DD));
		}
		if (toolTestDateCircle != null && toolTestDateCircle != "") {
			toolTrack.setToolTestDateCircle(
					Double.parseDouble(toolTestDateCircle));
			toolTrack.setToolNextTestDate(DateUtil.addMonth(
					DateUtil.StringToDate(toolTestDate,
							DateStyle.YYYY_MM_DD),
					Integer.parseInt(toolTestDateCircle)));
		}
		if (baseToolId != null && baseToolId != "") {
			toolTrack.setBaseToolName(baseToolName);
			toolTrack.setBaseToolTypeId(
					BaseUtil.strToLong(baseToolTypeId));
			toolTrack.setBaseToolTypeName(baseToolTypeName);
			toolTrack.setBaseToolModel(baseToolModel);
			toolTrack.setBaseToolSpec(baseToolSpec);
			toolTrack.setBaseToolManufacturerName(baseToolManName);
		}
		if (toolManufactureDate != null && toolManufactureDate != "") {
			toolTrack.setToolManufactureDate(DateUtil.StringToDate(
					toolManufactureDate, DateStyle.YYYY_MM_DD));
		}
		if (toolPurchaseDate != null && toolPurchaseDate != "") {
			toolTrack.setToolPurchaseDate(DateUtil.StringToDate(
					toolPurchaseDate, DateStyle.YYYY_MM_DD));
		}
		if (posName != null && posName != "") {
			toolTrack.setPosName(posName);
		}
		if (storeName != null && storeName != "") {
			toolTrack.setStoreName(storeName);
		}
		toolTrack.setToolDeptId(
				getSessionUser(request, response).getUserDeptId());

		if (BaseUtil.strToLong(batchType) == BatchType.RETURN
				|| BaseUtil.strToLong(batchType) == BatchType.SELF_RETURN) {
			// 查询工器具的本部门最早入库的位置
			ToolTrack temp = new ToolTrack();
			temp.setToolCode(toolCode);
			temp.setToolDeptId(
					getSessionUser(request, response).getUserDeptId());
			temp.setToolStatus(ToolStatus.CHECK_IN);
			List<ToolTrack> track = toolTrackService
					.selectToolTracksForList(temp);
			tool.setStoreId(track.get(0).getStoreId());
			toolTrack.setStoreId(track.get(0).getStoreId());
			tool.setPosId(track.get(0).getPosId());
			toolTrack.setPosId(track.get(0).getPosId());
			toolTrack.setStoreName(track.get(0).getStoreName());
			toolTrack.setPosName(track.get(0).getPosName());
			tool.setToolBox(track.get(0).getToolBox());
			toolTrack.setToolBox(track.get(0).getToolBox());
		}
		return batchService.addNewBatchsAndDetails(batch, tool,
				toolTrack);
	}

	/**
	 * 删除批次
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delBatchs.do")
	@ResponseBody
	public Map<String, Object> delBatchs(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String batchIds = request.getParameter("BATCH_IDS");
		Batch batch = new Batch();
		batch.setIds(batchIds);
		return batchService.deleteBatchs(batch);
	}

	/**
	 * 确认批次
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/confirmBatchs.do")
	@ResponseBody
	public Map<String, Object> confirmBatchs(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String batchIds = request.getParameter("BATCH_IDS");
		String batchType = request.getParameter("BATCH_TYPE");
		Batch batch = new Batch();
		batch.setBatchType(BaseUtil.strToLong(batchType));
		batch.setIds(batchIds);
		batch.setBatchCreateTime(new Date());
		batch.setBatchConfirmUserId(
				getSessionUser(request, response).getUserId());
		return batchService.confirmBatchs(batch);
	}

	/**
	 * 删除出入库记录
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delToolAndTrack.do")
	@ResponseBody
	public Map<String, Object> delToolAndTrack(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String toolId = request.getParameter("TOOL_ID");
		String trackId = request.getParameter("TRACK_ID");
		String batchId = request.getParameter("BATCH_ID");
		Tool tool = new Tool();
		tool.setToolId(BaseUtil.strToLong(toolId));
		ToolTrack toolTrack = new ToolTrack();
		toolTrack.setTrackId(BaseUtil.strToLong(trackId));
		toolTrack.setToolId(BaseUtil.strToLong(toolId));
		Batch batch = new Batch();
		batch.setBatchId(BaseUtil.strToLong(batchId));
		return batchService.delToolAndTrack(tool, toolTrack, batch);
	}

	/**
	 * 弹出选择工器具管理操作页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/openAddToolsUI.do", method = RequestMethod.GET)
	public ModelAndView openAddToolsUI(HttpServletRequest request,
			HttpServletResponse response) {
		String batchId = request.getParameter("BATCH_ID");
		if (batchId != null && !"".equals(batchId)) {
			Batch param = new Batch();
			param.setBatchId(BaseUtil.strToLong(batchId));
			Batch batch = batchService.selectBatchsForObject(param);
			request.setAttribute("BATCH_CODE", batch.getBatchCode());
		}
		String batchType = request.getParameter("BATCH_TYPE");
		ModelAndView mv = new ModelAndView();
		if ("0".equals(batchType)) {
			mv.setViewName("/gqj/tool_batch/addToolsUIForCheckIn");
		} else {
			mv.setViewName("/gqj/tool_batch/addToolsUI");
		}
		return mv;
	}

	/**
	 * 跳转到仓位管理操作页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/openEditUI.do", method = RequestMethod.GET)
	public ModelAndView openEditUI(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView("/gqj/tool_batch/editUI");
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
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.getWriter().print(manufacturerService
				.selectManufacturersForList(new Manufacturer()));
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
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String keyWord = request.getParameter("keyWord");
		String baseToolTypeId = request
				.getParameter("BASE_TOOL_TYPE_ID");
		String manufacturerId = request.getParameter("MANUFACTURER_ID");
		String baseToolModel = request.getParameter("BASE_TOOL_MODEL");
		String baseToolSpec = request.getParameter("BASE_TOOL_SPEC");
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("keyWord", keyWord);
		param.put("currPage", page);
		param.put("pageSize", rows);
		param.put("baseToolTypeId", baseToolTypeId);
		param.put("manufacturerId", manufacturerId);
		param.put("baseToolModel", baseToolModel);
		param.put("baseToolSpec", baseToolSpec);
		return baseToolService.selectBaseToolsForPage(param);
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
	public void queryBaseToolTypeDropList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.getWriter().print(
				toolTypeService.selectToolTypesForList(new ToolType()));
		response.getWriter().flush();
		response.getWriter().close();
	}

	/**
	 * 分页查询仓位列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryBatchsPage.do")
	@ResponseBody
	public Map<String, Object> queryBatchsPage(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String keyWord = request.getParameter("keyWord");
		String batchType = request.getParameter("BATCH_TYPE");
		Batch batch = new Batch();
		batch.setCurrPage(Integer.parseInt(page));
		batch.setPageSize(Integer.parseInt(rows));
		batch.setKeyWord(keyWord);
		if ("7".equals(batchType)) {
			batch.setBatchTakeDeptId(
					getSessionUser(request, response).getUserDeptId());
		} else {
			batch.setBatchDeptId(
					getSessionUser(request, response).getUserDeptId());
			batch.setBatchType(BaseUtil.strToLong(batchType));
		}

		return batchService.selectBatchsForPage(batch);
	}

	/**
	 * 分页查询仓位列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryToolTracksPage.do")
	@ResponseBody
	public Map<String, Object> queryToolTracksPage(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String keyWord = request.getParameter("keyWord");
		String batchId = request.getParameter("BATCH_ID");
		ToolTrack toolTrack = new ToolTrack();
		toolTrack.setCurrPage(Integer.parseInt(page));
		toolTrack.setPageSize(Integer.parseInt(rows));
		toolTrack.setKeyWord(keyWord);
		toolTrack.setToolCode(keyWord);
		toolTrack.setBatchId(BaseUtil.strToLong(batchId));
		return toolTrackService.selectToolTracksForPage(toolTrack);
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
			HttpServletRequest request, HttpServletResponse response)
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
	 * 查询新的批次
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/queryNewBatchCode.do")
	@ResponseBody
	public String queryNewBatchCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String batchType = request.getParameter("BATCH_TYPE");
		HashMap<String, Object> param = new HashMap<String, Object>();
		String rule1 = "";
		if ("0".equals(batchType)) {
			rule1 = "入库-";
		} else if ("1".equals(batchType)) {
			rule1 = "出库-";
		} else if ("2".equals(batchType)) {
			rule1 = "转仓-";
		} else if ("3".equals(batchType)) {
			rule1 = "退仓-";
		} else if ("4".equals(batchType)) {
			rule1 = "报废-";
		} else if ("5".equals(batchType)) {
			rule1 = "外站借用-";
		} else if ("6".equals(batchType)) {
			rule1 = "外站归还-";
		} else if ("8".equals(batchType)) {
			rule1 = "本站使用-";
		} else if ("9".equals(batchType)) {
			rule1 = "本站归还-";
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
			HttpServletRequest request, HttpServletResponse response)
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
	public Map<String, Object> queryStoragesPage(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		long storageDeptId = getSessionUser(request, response)
				.getUserDeptId();
		String keyWord = request.getParameter("keyWord");
		Storage storage = new Storage();
		storage.setCurrPage(BaseUtil.strToInt(page));
		storage.setPageSize(BaseUtil.strToInt(rows));
		storage.setStoreDeptId(storageDeptId);
		storage.setKeyWord(keyWord);
		return storageService.selectStoragesForPage(storage);
	}

	@Autowired
	private IUserService userService;

	/**
	 * 分页查询用户列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryUsersPage.do")
	@ResponseBody
	public Map<String, Object> queryUsersPage(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String keyWord = request.getParameter("keyWord");
		User user = new User();
		user.setCurrPage(BaseUtil.strToInt(page));
		user.setPageSize(BaseUtil.strToInt(rows));
		user.setKeyWord(keyWord);
		return userService.queryUsersForPage(user);
	}

	/**
	 * 跳转到仓位管理首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/index.do", method = RequestMethod.GET)
	public ModelAndView toIndex() {
		return new ModelAndView("/gqj/tool_batch/index");
	}

	/**
	 * 更新仓位信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateBatch.do")
	@ResponseBody
	public Map<String, Object> updateBatch(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Batch batch = new Batch();
		return batchService.updateBatch(batch);
	}

	/**
	 * 领用出入库单据
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/takeBatchs.do")
	@ResponseBody
	public Map<String, Object> takeBatchs(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String batchIds = request.getParameter("BATCH_IDS");
		Batch batch = new Batch();
		batch.setIds(batchIds);
		batch.setBatchTakeTime(new Date());
		batch.setBatchTakeUserId(
				getSessionUser(request, response).getUserId());
		return batchService.updateBatchs(batch);
	}

}
