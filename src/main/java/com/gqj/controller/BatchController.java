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

import com.base.admin.entity.Dept;
import com.base.admin.service.IDeptService;
import com.base.controller.BaseController;
import com.base.util.BaseUtil;
import com.base.util.DateUtil;
import com.gqj.entity.Batch;
import com.gqj.entity.Manufacturer;
import com.gqj.entity.Position;
import com.gqj.entity.Storage;
import com.gqj.entity.ToolType;
import com.gqj.service.IBaseToolService;
import com.gqj.service.IBatchService;
import com.gqj.service.IManufacturerService;
import com.gqj.service.IPositionService;
import com.gqj.service.ISequenceService;
import com.gqj.service.IStorageService;
import com.gqj.service.IToolTypeService;

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
	@RequestMapping("/addNewBatch.do")
	@ResponseBody
	public Map<String, Object> addNewBatch(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Batch batch = new Batch();
		return batchService.addNewBatch(batch);
	}

	/**
	 * 删除仓位
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
		String batchIds = request.getParameter("POS_IDS");
		Batch batch = new Batch();
		batch.setIds(batchIds);
		return batchService.deleteBatchs(batch);
	}

	/**
	 * 弹出选择工器具管理操作页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/openAddToolsUI.do", method = RequestMethod.GET)
	public ModelAndView openAddToolsUI(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView("/gqj/tool_batch/addToolsUI");
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

	@Autowired
	private IDeptService deptService;

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
		HashMap<String, Object> param = new HashMap<>();
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
		HashMap<String, Object> param = new HashMap<>();
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

}
