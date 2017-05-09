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
import com.bpbj.entity.PlugIn;
import com.bpbj.entity.PlugInTrack;
import com.bpbj.service.IBPBJBatchService;
import com.bpbj.service.IBPBJHomePageService;
import com.bpbj.service.IBPBJSequenceService;
import com.bpbj.service.IBPBJPlugInService;
import com.bpbj.service.IBPBJPlugInTrackService;
import com.bpbj.util.BatchType;
import com.bpbj.util.PlugInStatus;

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
	private IBPBJPlugInService plugInService;
	
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
	@RequestMapping("/queryPlugInInventorysPage.do")
	@ResponseBody
	public Map<String, Object> queryPlugInInventorysPage(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		long plugInDeptId = getSessionUser(request, response)
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
			param.put("plugInDeptId", plugInDeptId);
		} else if ("OVER_TEST".equals(dateType)) {
			param.put("plugInDeptId", plugInDeptId);
			param.put("plugInStatus", PlugInStatus.CHECK_IN);
			// 计算超期的日期
			int days = BaseUtil.strToInt(paramService
					.queryParamsForMap("BEFORE_TEST_DAYS"));
			String date = DateUtil.addDay(DateUtil.getNow(),
					days);
			param.put("overDays", date);
		} else if ("OVER_REJECT".equals(dateType)) {
			param.put("plugInDeptId", plugInDeptId);
			param.put("plugInStatus", PlugInStatus.CHECK_IN);
			// 当天日期
			param.put("overRejectDays", DateUtil.getNow());
		}
		return plugInService.selectPlugInsForPage(param);
	}

	/**
	 * 弹出选择工器具管理操作页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/openAddPlugInsUI.do", method = RequestMethod.GET)
	public String openAddPlugInsUI(HttpServletRequest request,
			HttpServletResponse response) {
		return "/bpbj/homepage/addPlugInsUIForReturn";
	}

	@Autowired
	private IUserService userService;

	@Autowired
	private IBPBJBatchService batchService;

	@Autowired
	private IBPBJPlugInTrackService plugInTrackService;

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
		String basePlugInId = request.getParameter("BASE_TOOL_ID");
		String basePlugInName = request.getParameter("BASE_TOOL_NAME");
		String basePlugInManName = request
				.getParameter("BASE_TOOL_MANUFACTURER_NAME");
		String basePlugInTypeId = request
				.getParameter("BASE_TOOL_TYPE_ID");
		String basePlugInTypeName = request
				.getParameter("BASE_TOOL_TYPE_NAME");
		String basePlugInModel = request.getParameter("BASE_TOOL_MODEL");
		String basePlugInSpec = request.getParameter("BASE_TOOL_SPEC");
		String batchRemark = request.getParameter("BATCH_REMARK");
		String batchTakeDeptId = request
				.getParameter("BATCH_TAKE_DEPT_ID");
		String plugInCode = request.getParameter("TOOL_CODE");
		String plugInBox = request.getParameter("TOOL_BOX");
		String plugInDate = request.getParameter("TOOL_TEST_DATE");
		String plugInRejectDate = request
				.getParameter("TOOL_REJECT_DATE");
		String plugInManufactureDate = request
				.getParameter("TOOL_MANUFACTURE_DATE");
		String plugInPurchaseDate = request
				.getParameter("TOOL_PURCHASE_DATE");
		String plugInDateCircle = request
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
		PlugIn plugIn = new PlugIn();
		plugIn.setPlugInCode(plugInCode);
		if (storeId != null && storeId != "") {
			plugIn.setStoreId(BaseUtil.strToLong(storeId));
		}
		if (posId != null && posId != "") {
			plugIn.setPosId(BaseUtil.strToLong(posId));
		}
		plugIn.setPlugInDeptId(
				getSessionUser(request, response).getUserDeptId());
		plugIn.setPlugInRemark(batchRemark);

		PlugInTrack plugInTrack = new PlugInTrack();
		if (storeId != null && storeId != "") {
			plugInTrack.setStoreId(BaseUtil.strToLong(storeId));
		}
		if (posId != null && posId != "") {
			plugInTrack.setPosId(BaseUtil.strToLong(posId));
		}
		return batchService.addNewBatchsAndDetails(batch, plugIn,
				plugInTrack);
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

}
