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

import com.base.controller.BaseController;
import com.base.util.BaseUtil;
import com.bpbj.entity.Manufacturer;
import com.bpbj.entity.Position;
import com.bpbj.entity.Storage;
import com.bpbj.service.IBPBJAccessoryBillDetailService;
import com.bpbj.service.IBPBJAccessoryInventoryService;
import com.bpbj.service.IBPBJManufacturerService;
import com.bpbj.service.IBPBJPositionService;
import com.bpbj.service.IBPBJStorageService;

@Controller
@RequestMapping("/bpbj/accessory_inventory")
public class BPBJAccessoryInventoryController extends BaseController {
	public static final Logger LOGGER = Logger
			.getLogger(BPBJAccessoryInventoryController.class);

	@Autowired
	private IBPBJAccessoryInventoryService accessoryInventoryService;

	@Autowired
	private IBPBJAccessoryBillDetailService accessoryBillDetailService;

	@Autowired
	private IBPBJPositionService positionService;

	@Autowired
	private IBPBJStorageService storageService;

	@Autowired
	private IBPBJManufacturerService manufacturerService;
	
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
				"/bpbj/accessory_inventory/choosePositionUI");
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
				"/bpbj/accessory_inventory/chooseStorageUI");
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
	@RequestMapping("/queryAccessoryBillDetailsForPage.do")
	@ResponseBody
	public Map<String, Object> queryAccessoryBillDetailsForPage(
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
		return accessoryBillDetailService
				.selectAccessoryBillDetailsForPage(param);
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
		String keyWord = request.getParameter("keyWord");
		Storage storage = new Storage();
		storage.setCurrPage(BaseUtil.strToInt(page));
		storage.setPageSize(BaseUtil.strToInt(rows));
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
	@RequestMapping("/queryAccessoryInventorysPage.do")
	@ResponseBody
	public Map<String, Object> queryAccessoryInventorysPage(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String keyWord = request.getParameter("keyWord");
		String storeId = request.getParameter("STORE_ID");
		String posId = request.getParameter("POS_ID");
		String manufacturerId = request.getParameter("MAN_ID");
		String baseToolModel = request.getParameter("BASE_TOOL_MODEL");
		String baseToolSpec = request.getParameter("BASE_TOOL_SPEC");
		String baseToolStation = request.getParameter("BASE_TOOL_STATION");
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("currPage", page);
		param.put("pageSize", rows);
		param.put("keyWord", keyWord);
		param.put("storeId", storeId);
		param.put("posId", posId);
		param.put("manId", manufacturerId);
		param.put("baseToolModel", baseToolModel);
		param.put("baseToolSpec", baseToolSpec);
		param.put("baseToolStation", baseToolStation);
		return accessoryInventoryService
				.selectAccessoryInventorysForPage(param);
	}

	/**
	 * 跳转到库存查询首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/index.do", method = RequestMethod.GET)
	public ModelAndView toIndex() {
		return new ModelAndView("/bpbj/accessory_inventory/index");
	}

}
