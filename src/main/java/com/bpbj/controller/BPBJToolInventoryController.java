package com.bpbj.controller;

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
import com.base.util.DateUtil;
import com.bpbj.entity.Manufacturer;
import com.bpbj.entity.Position;
import com.bpbj.entity.Storage;
import com.bpbj.entity.PlugInTrack;
import com.bpbj.entity.ToolType;
import com.bpbj.service.IBPBJManufacturerService;
import com.bpbj.service.IBPBJPositionService;
import com.bpbj.service.IBPBJStorageService;
import com.bpbj.service.IBPBJToolService;
import com.bpbj.service.IBPBJToolTrackService;
import com.bpbj.service.IBPBJToolTypeService;
import com.bpbj.util.ToolStatus;

@Controller
@RequestMapping("/bpbj/tool_inventory")
public class BPBJToolInventoryController
		extends BaseController {
	public static final Logger LOGGER = Logger
			.getLogger(BPBJToolInventoryController.class);

	@Autowired
	private IBPBJPositionService positionService;

	@Autowired
	private IBPBJToolTrackService toolTrackService;

	@Autowired
	private IBPBJStorageService storageService;

	@Autowired
	private IBPBJToolService toolService;

	@Autowired
	private IBPBJManufacturerService manufacturerService;

	@Autowired
	private IBPBJToolTypeService toolTypeService;

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
				"/bpbj/tool_inventory/choosePositionUI");
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
				"/bpbj/tool_inventory/chooseStorageUI");
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
	 * 分页查询工器具批次列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryToolTracksForPage.do")
	@ResponseBody
	public Map<String, Object> queryToolTracksForPage(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String keyWord = request.getParameter("keyWord");
		String toolId = request.getParameter("TOOL_ID");
		PlugInTrack toolTrack = new PlugInTrack();
		toolTrack.setCurrPage(BaseUtil.strToInt(page));
		toolTrack.setPageSize(BaseUtil.strToInt(rows));
		toolTrack.setKeyWord(keyWord);
		toolTrack.setToolId(BaseUtil.strToLong(toolId));
		return toolTrackService
				.selectToolTracksForPage(toolTrack);
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
		String dateType = request.getParameter("DATE_TYPE");
		String keyWord = request.getParameter("keyWord");
		Storage storage = new Storage();
		storage.setCurrPage(BaseUtil.strToInt(page));
		storage.setPageSize(BaseUtil.strToInt(rows));
		if (!"ALL".equals(dateType)) {
			long storageDeptId = getSessionUser(request,
					response).getUserDeptId();
			storage.setStoreDeptId(storageDeptId);
		}

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

		param.put("baseToolTypeId", baseToolTypeId);
		param.put("manufacturerId", manufacturerId);
		param.put("baseToolModel", baseToolModel);
		param.put("baseToolSpec", baseToolSpec);
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
			param.put("overTestDays", date);
		} else if ("OVER_REJECT".equals(dateType)) {
			param.put("toolDeptId", toolDeptId);
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
		return new ModelAndView(
				"/bpbj/tool_inventory/index");
	}

}
