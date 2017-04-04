package com.gqj.controller;

import java.util.HashMap;
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

import com.base.admin.service.IParamService;
import com.base.controller.BaseController;
import com.base.util.BaseUtil;
import com.base.util.DateStyle;
import com.base.util.DateUtil;
import com.gqj.entity.Manufacturer;
import com.gqj.entity.Position;
import com.gqj.entity.Storage;
import com.gqj.entity.Tool;
import com.gqj.entity.ToolType;
import com.gqj.service.IBaseToolService;
import com.gqj.service.IManufacturerService;
import com.gqj.service.IPositionService;
import com.gqj.service.IStorageService;
import com.gqj.service.IToolService;
import com.gqj.service.IToolTypeService;
import com.gqj.util.ToolStatus;

@Controller
@RequestMapping("/gqj/tool")
public class ToolController extends BaseController {
	public static final Logger LOGGER = Logger
			.getLogger(ToolController.class);

	@Autowired
	private IPositionService positionService;

	@Autowired
	private IStorageService storageService;

	@Autowired
	private IToolService toolService;

	@Autowired
	private IManufacturerService manufacturerService;

	@Autowired
	private IToolTypeService toolTypeService;

	@Autowired
	private IParamService paramService;

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
	 * 弹出选择仓位管理操作页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/openChoosePositionUI.do", method = RequestMethod.GET)
	public ModelAndView openChoosePositionUI(
			HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView(
				"/gqj/tool/choosePositionUI");
	}

	/**
	 * 弹出工器具信息管理操作页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/openEditToolUI.do", method = RequestMethod.GET)
	public ModelAndView openEditToolUI(
			HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView("/gqj/tool/editToolUI");
	}

	@Autowired
	private IBaseToolService baseToolService;

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
	 * 更新工器具信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateTool.do")
	@ResponseBody
	public Map<String, Object> updateTool(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String toolId = request.getParameter("TOOL_ID");
		String toolCode = request.getParameter("TOOL_CODE");
		String toolTestDate = request
				.getParameter("TOOL_TEST_DATE");
		String toolRejectDate = request
				.getParameter("TOOL_REJECT_DATE");
		String toolManufactureDate = request
				.getParameter("TOOL_MANUFACTURE_DATE");
		String toolPurchaseDate = request
				.getParameter("TOOL_PURCHASE_DATE");
		String toolTestDateCircle = request
				.getParameter("TOOL_TEST_DATE_CIRCLE");
		String baseToolId = request
				.getParameter("BASE_TOOL_ID");
		String storeId = request.getParameter("STORE_ID");
		String positionId = request.getParameter("POS_ID");
		String toolBox = request.getParameter("TOOL_BOX");
		String toolRemark = request
				.getParameter("TOOL_REMARK");
		Tool tool = new Tool();
		tool.setToolId(BaseUtil.strToLong(toolId));
		tool.setToolCode(toolCode);
		tool.setToolTestDate(
				DateUtil.StringToDate(toolTestDate));
		tool.setToolRejectDate(
				DateUtil.StringToDate(toolRejectDate));
		tool.setToolManufactureDate(
				DateUtil.StringToDate(toolManufactureDate));
		tool.setToolPurchaseDate(
				DateUtil.StringToDate(toolPurchaseDate));
		tool.setToolTestDateCircle(
				BaseUtil.strToDouble(toolTestDateCircle));
		tool.setBaseToolId(BaseUtil.strToLong(baseToolId));
		tool.setStoreId(BaseUtil.strToLong(storeId));
		tool.setPosId(BaseUtil.strToLong(positionId));
		tool.setToolBox(toolBox);
		tool.setToolRemark(toolRemark);
		tool.setToolNextTestDate(DateUtil.addMonth(
				DateUtil.StringToDate(toolTestDate,
						DateStyle.YYYY_MM_DD),
				Integer.parseInt(toolTestDateCircle)));
		return toolService.updateTool(tool);
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
				"/gqj/tool/chooseStorageUI");
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
		String storeId = request.getParameter("STORE_ID");
		String posId = request.getParameter("POS_ID");
		String baseToolTypeId = request
				.getParameter("BASE_TOOL_TYPE_ID");
		String manufacturerId = request
				.getParameter("MANUFACTURER_ID");
		String baseToolModel = request
				.getParameter("BASE_TOOL_MODEL");
		String baseToolSpec = request
				.getParameter("BASE_TOOL_SPEC");
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("storeId", storeId);
		param.put("posId", posId);
		param.put("keyWord", keyWord);
		param.put("currPage", page);
		param.put("pageSize", rows);
		param.put("toolDeptId", toolDeptId);
		param.put("baseToolTypeId", baseToolTypeId);
		param.put("manufacturerId", manufacturerId);
		param.put("baseToolModel", baseToolModel);
		param.put("baseToolSpec", baseToolSpec);
		if ("ALL".equals(dateType)) {
			// 不设置参数
		} else if ("OVER_TEST".equals(dateType)) {
			param.put("toolStatus", ToolStatus.CHECK_IN);
			// 计算超期的日期
			int days = BaseUtil.strToInt(paramService
					.queryParamsForMap("BEFORE_TEST_DAYS"));
			String date = DateUtil.addDay(DateUtil.getNow(),
					days);
			param.put("overTestDays", date);
		} else if ("OVER_REJECT".equals(dateType)) {
			param.put("toolStatus", ToolStatus.CHECK_IN);
			// 当天日期
			param.put("overRejectDays", DateUtil.getNow());
		}
		return toolService.selectToolsForPage(param);
	}

	/**
	 * 跳转到库存查询首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/index.do", method = RequestMethod.GET)
	public ModelAndView toIndex() {
		return new ModelAndView("/gqj/tool/index");
	}

}
