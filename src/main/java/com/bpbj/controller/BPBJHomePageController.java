package com.bpbj.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.base.admin.entity.User;
import com.base.admin.service.IParamService;
import com.base.admin.service.IUserService;
import com.base.controller.BaseController;
import com.base.util.BaseUtil;
import com.base.util.DateStyle;
import com.base.util.DateUtil;
import com.bpbj.entity.Batch;
import com.bpbj.entity.Tool;
import com.bpbj.entity.ToolTrack;
import com.bpbj.service.IBPBJBatchService;
import com.bpbj.service.IBPBJHomePageService;
import com.bpbj.service.IBPBJSequenceService;
import com.bpbj.service.IBPBJToolService;
import com.bpbj.service.IBPBJToolTrackService;
import com.bpbj.util.BatchType;
import com.bpbj.util.ToolStatus;

@Controller
@RequestMapping("/bpbj/homepage")
public class BPBJHomePageController extends BaseController {
	@Autowired
	private IBPBJSequenceService sequenceService;

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
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("rule1", "归还-");
		param.put("rule2", DateUtil.getNow() + "-");
		param.put("rule3", "@");
		param.put("rule4", "@");
		param.put("rule5", "@");
		param.put("seq", "4");
		return sequenceService.selectSequence(param);
	}
	
	@Autowired
	private IBPBJToolService toolService;
	
	@Autowired
	private IParamService paramService;
	
	/**
	 * 分页查询仓库列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryToolInventorysPage.do")
	@ResponseBody
	public Map<String, Object> queryToolInventorysPage(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		long toolDeptId = getSessionUser(request, response)
				.getUserDeptId();
		String keyWord = request.getParameter("keyWord");
		String dateType = request.getParameter("DATE_TYPE");
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("keyWord", keyWord);
		param.put("currPage", page);
		param.put("pageSize", rows);

		if ("ALL".equals(dateType)) {
			// 不设置参数
		} else if ("MY_DEPT".equals(dateType)) {
			param.put("toolDeptId", toolDeptId);
		} else if ("OVER_TEST".equals(dateType)) {
			param.put("toolDeptId", toolDeptId);
			param.put("toolStatus", ToolStatus.CHECK_IN);
			// 计算超期的日期
			int days = BaseUtil.strToInt(paramService
					.queryParamsForMap("BEFORE_TEST_DAYS"));
			String date = DateUtil.addDay(DateUtil.getNow(),
					days);
			param.put("overDays", date);
		} else if ("OVER_REJECT".equals(dateType)) {
			param.put("toolDeptId", toolDeptId);
			param.put("toolStatus", ToolStatus.CHECK_IN);
			// 当天日期
			param.put("overRejectDays", DateUtil.getNow());
		}
		return toolService.selectToolsForPage(param);
	}

	/**
	 * 弹出选择工器具管理操作页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/openAddToolsUI.do", method = RequestMethod.GET)
	public String openAddToolsUI(HttpServletRequest request,
			HttpServletResponse response) {
		return "/bpbj/homepage/addToolsUIForReturn";
	}

	@Autowired
	private IUserService userService;

	@Autowired
	private IBPBJBatchService batchService;

	@Autowired
	private IBPBJToolTrackService toolTrackService;

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
		String toolDate = request.getParameter("TOOL_TEST_DATE");
		String toolRejectDate = request
				.getParameter("TOOL_REJECT_DATE");
		String toolManufactureDate = request
				.getParameter("TOOL_MANUFACTURE_DATE");
		String toolPurchaseDate = request
				.getParameter("TOOL_PURCHASE_DATE");
		String toolDateCircle = request
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
		if (toolDate != null && toolDate != "") {
			tool.setToolTestDate(DateUtil.StringToDate(toolDate,
					DateStyle.YYYY_MM_DD));
		}
		if (toolRejectDate != null && toolRejectDate != "") {
			tool.setToolRejectDate(DateUtil.StringToDate(toolRejectDate,
					DateStyle.YYYY_MM_DD));
		}
		if (toolDateCircle != null && toolDateCircle != "") {
			tool.setToolTestDateCircle(
					Double.parseDouble(toolDateCircle));
			tool.setToolNextTestDate(DateUtil.addMonth(
					DateUtil.StringToDate(toolDate,
							DateStyle.YYYY_MM_DD),
					Integer.parseInt(toolDateCircle)));
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
		if (toolDate != null && toolDate != "") {
			toolTrack.setToolTestDate(DateUtil
					.StringToDate(toolDate, DateStyle.YYYY_MM_DD));
		}
		if (toolRejectDate != null && toolRejectDate != "") {
			toolTrack.setToolRejectDate(DateUtil.StringToDate(
					toolRejectDate, DateStyle.YYYY_MM_DD));
		}
		if (toolDateCircle != null && toolDateCircle != "") {
			toolTrack.setToolTestDateCircle(
					Double.parseDouble(toolDateCircle));
			toolTrack.setToolNextTestDate(DateUtil.addMonth(
					DateUtil.StringToDate(toolDate,
							DateStyle.YYYY_MM_DD),
					Integer.parseInt(toolDateCircle)));
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

		if (BaseUtil.strToLong(batchType) == BatchType.RETURN) {
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
	 * 跳转到首页管理首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/index.do", method = RequestMethod.GET)
	public ModelAndView toIndex() {
		return new ModelAndView("/bpbj/homepage/homepage");
	}

	@Autowired
	private IBPBJHomePageService homePageService;

	/**
	 * 查询需要归还的工器具
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/queryNeedReturnTools.do")
	@ResponseBody
	public Map<String, Object> queryNeedReturnTools(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String keyWord = request.getParameter("keyWord");
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("keyWord", keyWord);
		param.put("currPage", page);
		param.put("pageSize", rows);
		param.put("tool_dept_id",
				getSessionUser(request, response).getUserDeptId());
		return homePageService.selectNeedReturnToolsForPage(param);
	}

}
