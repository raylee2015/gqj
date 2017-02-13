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

import com.base.controller.BaseController;
import com.base.util.BaseUtil;
import com.gqj.entity.Manufacturer;
import com.gqj.entity.Position;
import com.gqj.entity.Storage;
import com.gqj.entity.ToolType;
import com.gqj.service.IManufacturerService;
import com.gqj.service.IMaterialBillDetailService;
import com.gqj.service.IMaterialInventoryService;
import com.gqj.service.IPositionService;
import com.gqj.service.IStorageService;
import com.gqj.service.IToolTypeService;

@Controller
@RequestMapping("/gqj/material_inventory")
public class MaterialInventoryController extends BaseController {
	public static final Logger LOGGER = Logger
			.getLogger(MaterialInventoryController.class);

	@Autowired
	private IMaterialInventoryService materialInventoryService;

	@Autowired
	private IMaterialBillDetailService materialBillDetailService;

	@Autowired
	private IPositionService positionService;

	@Autowired
	private IStorageService storageService;

	@Autowired
	private IManufacturerService manufacturerService;

	@Autowired
	private IToolTypeService toolTypeService;

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
	 * 弹出选择仓位管理操作页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/openChoosePositionUI.do", method = RequestMethod.GET)
	public ModelAndView openChoosePositionUI(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView(
				"/gqj/material_inventory/choosePositionUI");
	}

	/**
	 * 弹出选择仓库管理操作页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/openChooseStorageUI.do", method = RequestMethod.GET)
	public ModelAndView openChooseStorageUI(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView(
				"/gqj/material_inventory/chooseStorageUI");
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
	 * 分页查询出入库明细列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryMaterialBillDetailsForPage.do")
	@ResponseBody
	public Map<String, Object> queryMaterialBillDetailsForPage(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String posId = request.getParameter("POS_ID");
		String baseToolId = request.getParameter("BASE_TOOL_ID");
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("currPage", page);
		param.put("pageSize", rows);
		param.put("baseToolId", baseToolId);
		param.put("posId", posId);
		param.put("confirmUserIdIsNotNull", 1);
		return materialBillDetailService
				.selectMaterialBillDetailsForPage(param);
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
		String deptType = request.getParameter("DEPT_TYPE");
		Storage storage = new Storage();
		storage.setCurrPage(BaseUtil.strToInt(page));
		storage.setPageSize(BaseUtil.strToInt(rows));
		if ("ALL".equals(deptType)) {

		} else if ("MY".equals(deptType)) {
			storage.setStoreDeptId(storageDeptId);
		}
		storage.setKeyWord(keyWord);
		return storageService.selectStoragesForPage(storage);
	}

	/**
	 * 分页查询库存列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryMaterialInventorysPage.do")
	@ResponseBody
	public Map<String, Object> queryMaterialInventorysPage(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String keyWord = request.getParameter("keyWord");
		String storeId = request.getParameter("STORE_ID");
		String posId = request.getParameter("POS_ID");
		String baseToolTypeId = request
				.getParameter("BASE_TOOL_TYPE_ID");
		String manufacturerId = request.getParameter("MANUFACTURER_ID");
		String baseToolModel = request.getParameter("BASE_TOOL_MODEL");
		String baseToolSpec = request.getParameter("BASE_TOOL_SPEC");
		String deptType = request.getParameter("DEPT_TYPE");
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("currPage", page);
		param.put("pageSize", rows);
		param.put("keyWord", keyWord);
		param.put("storeId", storeId);
		param.put("posId", posId);
		if ("ALL".equals(deptType)) {

		} else if ("MY".equals(deptType)) {
			param.put("storeDeptId",
					getSessionUser(request, response).getUserDeptId());
		}
		param.put("baseToolTypeId", baseToolTypeId);
		param.put("manufacturerId", manufacturerId);
		param.put("baseToolModel", baseToolModel);
		param.put("baseToolSpec", baseToolSpec);
		return materialInventoryService
				.selectMaterialInventorysForPage(param);
	}

	/**
	 * 跳转到库存查询首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/index.do", method = RequestMethod.GET)
	public ModelAndView toIndex() {
		return new ModelAndView("/gqj/material_inventory/index");
	}

}
