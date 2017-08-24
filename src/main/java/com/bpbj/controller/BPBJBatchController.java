package com.bpbj.controller;

import java.util.Date;
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
import com.base.admin.entity.User;
import com.base.admin.service.IDeptService;
import com.base.admin.service.IUserService;
import com.base.controller.BaseController;
import com.base.util.BaseUtil;
import com.base.util.DateUtil;
import com.bpbj.entity.BaseTool;
import com.bpbj.entity.Batch;
import com.bpbj.entity.Manufacturer;
import com.bpbj.entity.PlugIn;
import com.bpbj.entity.PlugInTrack;
import com.bpbj.entity.Position;
import com.bpbj.entity.Storage;
import com.bpbj.service.IBPBJBaseToolService;
import com.bpbj.service.IBPBJBatchService;
import com.bpbj.service.IBPBJManufacturerService;
import com.bpbj.service.IBPBJPlugInService;
import com.bpbj.service.IBPBJPlugInTrackService;
import com.bpbj.service.IBPBJPositionService;
import com.bpbj.service.IBPBJSequenceService;
import com.bpbj.service.IBPBJStorageService;

@Controller
@RequestMapping("/bpbj/plugin_batch")
public class BPBJBatchController extends BaseController {
	public static final Logger LOGGER = Logger.getLogger(BPBJBatchController.class);

	@Autowired
	private IBPBJBaseToolService baseToolService;

	@Autowired
	private IBPBJBatchService batchService;

	@Autowired
	private IBPBJPlugInTrackService plugInTrackService;

	@Autowired
	private IDeptService deptService;

	@Autowired
	private IBPBJManufacturerService manufacturerService;

	@Autowired
	private IBPBJPositionService positionService;

	@Autowired
	private IBPBJSequenceService sequenceService;

	@Autowired
	private IBPBJStorageService storageService;

	@Autowired
	private IBPBJPlugInService plugInService;

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
	public Map<String, Object> addNewBatchsAndDetails(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String batchCode = request.getParameter("BATCH_CODE");
		String batchType = request.getParameter("BATCH_TYPE");
		String storeId = request.getParameter("STORE_ID");
		String posId = request.getParameter("POS_ID");
		String baseToolId = request.getParameter("BASE_TOOL_ID");
		String batchRemark = request.getParameter("BATCH_REMARK");
		String plugInCode = request.getParameter("PLUGIN_CODE");
		String plugInManDate = request.getParameter("PLUGIN_MAN_DATE");
		// 设置批次信息
		Batch batch = new Batch();
		batch.setBatchCode(batchCode);
		batch.setBatchType(BaseUtil.strToLong(batchType));
		batch.setBatchDeptId(getSessionUser(request, response).getUserDeptId());
		batch.setBatchCreateUserId(getSessionUser(request, response).getUserId());
		batch.setBatchCreateTime(new Date());
		batch.setBatchRemark(batchRemark);
		// 设置插件信息
		PlugIn plugIn = new PlugIn();
		plugIn.setPlugInCode(plugInCode);
		if (storeId != null && storeId != "") {
			plugIn.setStoreId(BaseUtil.strToLong(storeId));
		}
		if (posId != null && posId != "") {
			plugIn.setPosId(BaseUtil.strToLong(posId));
		}
		plugIn.setPlugInDeptId(getSessionUser(request, response).getUserDeptId());
		plugIn.setPlugInRemark(batchRemark);
		plugIn.setPlugInManDate(DateUtil.StringToDate(plugInManDate));
		plugIn.setBaseToolId(BaseUtil.strToLong(baseToolId));
		// 设置插件轨迹信息
		PlugInTrack plugInTrack = new PlugInTrack();
		if (storeId != null && storeId != "") {
			plugInTrack.setStoreId(BaseUtil.strToLong(storeId));
		}
		if (posId != null && posId != "") {
			plugInTrack.setPosId(BaseUtil.strToLong(posId));
		}
		if ("3".equals(batchType)) {
			PlugIn plugIn2 = plugInService.selectPlugInForObject(plugIn);
			PlugInTrack plugInTrack2 = new PlugInTrack();
			plugInTrack2.setPlugInId(plugIn2.getPlugInId());
			plugInTrack2 = plugInTrackService.selectPlugInTracksForList(plugInTrack2).get(0);
			plugIn.setPosId(plugInTrack2.getPosId());
			plugIn.setStoreId(plugInTrack2.getStoreId());
		} else if ("1".equals(batchType) || "4".equals(batchType)) {
			plugIn.setPosId(0L);
			plugIn.setStoreId(0L);
			plugInTrack.setPosId(0L);
			plugInTrack.setStoreId(0L);
		}
		return batchService.addNewBatchsAndDetails(batch, plugIn, plugInTrack);
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
	public Map<String, Object> delBatchs(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
	public Map<String, Object> confirmBatchs(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String batchIds = request.getParameter("BATCH_IDS");
		String batchType = request.getParameter("BATCH_TYPE");
		Batch batch = new Batch();
		batch.setBatchType(BaseUtil.strToLong(batchType));
		batch.setIds(batchIds);
		batch.setBatchCreateTime(new Date());
		batch.setBatchConfirmUserId(getSessionUser(request, response).getUserId());
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
	@RequestMapping("/delPlugInAndTrack.do")
	@ResponseBody
	public Map<String, Object> delPlugInAndTrack(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String plugInId = request.getParameter("TOOL_ID");
		String trackId = request.getParameter("TRACK_ID");
		String batchId = request.getParameter("BATCH_ID");
		PlugIn plugIn = new PlugIn();
		plugIn.setPlugInId(BaseUtil.strToLong(plugInId));
		PlugInTrack plugInTrack = new PlugInTrack();
		plugInTrack.setTrackId(BaseUtil.strToLong(trackId));
		plugInTrack.setPlugInId(BaseUtil.strToLong(plugInId));
		Batch batch = new Batch();
		batch.setBatchId(BaseUtil.strToLong(batchId));
		return batchService.delPlugInAndTrack(plugIn, plugInTrack, batch);
	}

	/**
	 * 弹出选择插件管理操作页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/openAddPlugInsUI.do", method = RequestMethod.GET)
	public ModelAndView openAddPlugInsUI(HttpServletRequest request, HttpServletResponse response) {
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
			mv.setViewName("/bpbj/plugin_batch/addPlugInsUIForCheckIn");
		} else {
			mv.setViewName("/bpbj/plugin_batch/addPlugInsUI");
		}
		return mv;
	}

	/**
	 * 跳转到仓位管理操作页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/openEditUI.do", method = RequestMethod.GET)
	public ModelAndView openEditUI(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("/bpbj/plugIn_batch/editUI");
	}

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
	 * 分页查询插件列表
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
		String baseToolType = request.getParameter("BASE_TOOL_TYPE");
		BaseTool baseTool = new BaseTool();
		baseTool.setCurrPage(Integer.parseInt(page));
		baseTool.setPageSize(Integer.parseInt(rows));
		baseTool.setKeyWord(keyWord);
		baseTool.setBaseToolType(BaseUtil.strToLong(baseToolType));
		baseTool.setBaseToolModel(basePlugInModel);
		baseTool.setBaseToolSpec(basePlugInSpec);
		return baseToolService.selectBaseToolsForPage(baseTool);
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
	public Map<String, Object> queryBatchsPage(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String keyWord = request.getParameter("keyWord");
		String batchType = request.getParameter("BATCH_TYPE");
		Batch batch = new Batch();
		batch.setCurrPage(Integer.parseInt(page));
		batch.setPageSize(Integer.parseInt(rows));
		batch.setKeyWord(keyWord);
		batch.setBatchDeptId(getSessionUser(request, response).getUserDeptId());
		batch.setBatchType(BaseUtil.strToLong(batchType));

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
	@RequestMapping("/queryPlugInTracksPage.do")
	@ResponseBody
	public Map<String, Object> queryPlugInTracksPage(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String keyWord = request.getParameter("keyWord");
		String batchId = request.getParameter("BATCH_ID");
		PlugInTrack plugInTrack = new PlugInTrack();
		plugInTrack.setCurrPage(Integer.parseInt(page));
		plugInTrack.setPageSize(Integer.parseInt(rows));
		plugInTrack.setKeyWord(keyWord);
		plugInTrack.setBatchId(BaseUtil.strToLong(batchId));
		return plugInTrackService.selectPlugInTracksForPage(plugInTrack);
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
	 * 查询新的批次
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/queryNewBatchCode.do")
	@ResponseBody
	public String queryNewBatchCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
	public Map<String, Object> queryUsersPage(HttpServletRequest request, HttpServletResponse response)
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
		return new ModelAndView("/bpbj/plugin_batch/index");
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
	public Map<String, Object> updateBatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Batch batch = new Batch();
		return batchService.updateBatch(batch);
	}

}
