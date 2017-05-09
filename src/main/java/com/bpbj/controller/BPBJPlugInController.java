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
import com.base.util.DateUtil;
import com.bpbj.entity.BaseTool;
import com.bpbj.entity.Manufacturer;
import com.bpbj.entity.PlugIn;
import com.bpbj.entity.Position;
import com.bpbj.entity.Storage;
import com.bpbj.service.IBPBJBaseToolService;
import com.bpbj.service.IBPBJManufacturerService;
import com.bpbj.service.IBPBJPlugInService;
import com.bpbj.service.IBPBJPositionService;
import com.bpbj.service.IBPBJStorageService;

@Controller
@RequestMapping("/bpbj/plugIn")
public class BPBJPlugInController extends BaseController {
	public static final Logger LOGGER = Logger.getLogger(BPBJPlugInController.class);

	@Autowired
	private IBPBJPositionService positionService;

	@Autowired
	private IBPBJStorageService storageService;

	@Autowired
	private IBPBJPlugInService plugInService;

	@Autowired
	private IBPBJManufacturerService manufacturerService;

	/**
	 * 查询下拉列表
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/queryBasePlugInManufacturerDropList.do")
	@ResponseBody
	public void queryBasePlugInManufacturerDropList(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.getWriter().print(manufacturerService.selectManufacturersForList(new Manufacturer()));
		response.getWriter().flush();
		response.getWriter().close();
	}

	/**
	 * 弹出选择仓位管理操作页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/openChoosePositionUI.do", method = RequestMethod.GET)
	public ModelAndView openChoosePositionUI(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("/bpbj/plugIn/choosePositionUI");
	}

	/**
	 * 弹出工器具信息管理操作页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/openEditPlugInUI.do", method = RequestMethod.GET)
	public ModelAndView openEditPlugInUI(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("/bpbj/plugIn/editPlugInUI");
	}

	@Autowired
	private IBPBJBaseToolService baseToolService;

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
	public Map<String, Object> queryBaseToolsPage(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String keyWord = request.getParameter("keyWord");
		String basePlugInModel = request.getParameter("BASE_TOOL_MODEL");
		String basePlugInSpec = request.getParameter("BASE_TOOL_SPEC");
		String manId = request.getParameter("MAN_ID");
		BaseTool baseTool = new BaseTool();
		baseTool.setCurrPage(Integer.parseInt(page));
		baseTool.setPageSize(Integer.parseInt(rows));
		baseTool.setKeyWord(keyWord);
		// 只是选择插件一类的信息
		baseTool.setBaseToolType(1L);
		baseTool.setBaseToolModel(basePlugInModel);
		baseTool.setBaseToolSpec(basePlugInSpec);
		baseTool.setManId(BaseUtil.strToLong(manId));
		return baseToolService.selectBaseToolsForPage(baseTool);
	}

	/**
	 * 更新工器具信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updatePlugIn.do")
	@ResponseBody
	public Map<String, Object> updatePlugIn(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String plugInId = request.getParameter("PLUGIN_ID");
		String plugInCode = request.getParameter("PLUGIN_CODE");
		String plugInManufactureDate = request.getParameter("PLUGIN_MAN_DATE");
		String baseToolId = request.getParameter("BASE_TOOL_ID");
		String storeId = request.getParameter("STORE_ID");
		String positionId = request.getParameter("POS_ID");
		String plugInRemark = request.getParameter("PLUGIN_REMARK");
		PlugIn plugIn = new PlugIn();
		plugIn.setPlugInId(BaseUtil.strToLong(plugInId));
		plugIn.setPlugInCode(plugInCode);
		plugIn.setPlugInManDate(DateUtil.StringToDate(plugInManufactureDate));
		plugIn.setBaseToolId(BaseUtil.strToLong(baseToolId));
		plugIn.setStoreId(BaseUtil.strToLong(storeId));
		plugIn.setPosId(BaseUtil.strToLong(positionId));
		plugIn.setPlugInRemark(plugInRemark);
		return plugInService.updatePlugIn(plugIn);
	}

	/**
	 * 弹出选择仓库管理操作页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/openChooseStorageUI.do", method = RequestMethod.GET)
	public ModelAndView openChooseStorageUI(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("/bpbj/plugIn/chooseStorageUI");
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
	 * 分页查询仓库列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryPlugInInventorysPage.do")
	@ResponseBody
	public Map<String, Object> queryPlugInInventorysPage(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		long plugInDeptId = getSessionUser(request, response).getUserDeptId();
		String keyWord = request.getParameter("keyWord");
		String storeId = request.getParameter("STORE_ID");
		String posId = request.getParameter("POS_ID");
		String manufacturerId = request.getParameter("MAN_ID");
		String basePlugInModel = request.getParameter("BASE_TOOL_MODEL");
		String basePlugInSpec = request.getParameter("BASE_TOOL_SPEC");
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("storeId", storeId);
		param.put("posId", posId);
		param.put("keyWord", keyWord);
		param.put("currPage", page);
		param.put("pageSize", rows);
		param.put("plugInDeptId", plugInDeptId);
		param.put("basePlugInTypeId", 1);
		param.put("manufacturerId", manufacturerId);
		param.put("basePlugInModel", basePlugInModel);
		param.put("basePlugInSpec", basePlugInSpec);
		return plugInService.selectPlugInsForPage(param);
	}

	/**
	 * 跳转到库存查询首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/index.do", method = RequestMethod.GET)
	public ModelAndView toIndex() {
		return new ModelAndView("/bpbj/plugIn/index");
	}

}
