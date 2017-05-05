package com.gqj.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.base.admin.entity.User;
import com.base.admin.service.IDictionaryService;
import com.base.controller.BaseController;
import com.base.util.BaseUtil;
import com.base.util.DateUtil;
import com.gqj.entity.CheckBill;
import com.gqj.entity.CheckBillDetail;
import com.gqj.service.ICheckBillDetailService;
import com.gqj.service.ICheckBillService;

@Controller
@RequestMapping("/gqj/check_bill")
public class CheckBillController extends BaseController {
	public static final Logger LOGGER = Logger.getLogger(CheckBillController.class);

	@Autowired
	private ICheckBillService checkBillService;

	/**
	 * 添加出入库单据信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addNewCheckBillsAndDetails.do")
	@ResponseBody
	@Transactional
	public Map<String, Object> addNewCheckBillsAndDetails(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		User user = getSessionUser(request, response);
		CheckBill checkBill = new CheckBill();
		checkBill.setBillId(-1l);
		checkBill.setBillCode(user.getUserDeptName() + "-" + DateUtil.getNow() + "-月度检验");
		checkBill.setBillCreateUserId(user.getUserId());
		checkBill.setBillDeptId(user.getUserDeptId());
		checkBill.setBillCreateTime(new Date());
		checkBill.setBillStatus(0L);
		return checkBillService.addCheckBillsAndDetails(checkBill);
	}

	/**
	 * 删除出入库单据
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delCheckBills.do")
	@ResponseBody
	public Map<String, Object> delCheckBills(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String checkBillIds = request.getParameter("BILL_IDS");
		CheckBill checkBill = new CheckBill();
		checkBill.setIds(checkBillIds);
		return checkBillService.deleteCheckBillsAndDetails(checkBill);
	}

	/**
	 * 确认完成出入库单据
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/comfirmCheckBills.do")
	@ResponseBody
	public Map<String, Object> comfirmCheckBills(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String checkBillIds = request.getParameter("BILL_IDS");
		CheckBill checkBill = new CheckBill();
		checkBill.setIds(checkBillIds);
		checkBill.setBillStatus(1L);
		return checkBillService.updateCheckBill(checkBill);
	}

	/**
	 * 弹出选择扫描操作页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/scanUIForTool.do", method = RequestMethod.GET)
	public ModelAndView scanUIForTool(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("/gqj/check_bill/scanUI");
	}

	/**
	 * 分页查询出入库单据列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryCheckBillsPage.do")
	@ResponseBody
	public Map<String, Object> queryCheckBillsPage(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String keyWord = request.getParameter("keyWord");
		CheckBill checkBill = new CheckBill();
		checkBill.setCurrPage(Integer.parseInt(page));
		checkBill.setPageSize(Integer.parseInt(rows));
		checkBill.setKeyWord(keyWord);
		return checkBillService.selectCheckBillsForPage(checkBill);
	}

	@Autowired
	private ICheckBillDetailService checkBillDetailService;

	/**
	 * 分页查询出入库单据列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryCheckBillDetailsForPage.do")
	@ResponseBody
	public Map<String, Object> queryCheckBillDetailsForPage(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String keyWord = request.getParameter("keyWord");
		String billId = request.getParameter("BILL_ID");
		String baseToolNormalFlag = request.getParameter("BASE_TOOL_NORMAL_FLAG");
		String baseToolStatus = request.getParameter("BASE_TOOL_STATUS");
		Map<String, Object> param = new HashMap<>();
		param.put("currPage", page);
		param.put("pageSize", rows);
		param.put("keyWord", keyWord);
		param.put("billId", billId);
		param.put("baseToolNormalFlag", baseToolNormalFlag);
		param.put("baseToolStatus", baseToolStatus);
		return checkBillDetailService.selectCheckBillDetailsForPage(param);
	}

	/**
	 * 更新工器具检查状态
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateCheckBillDetail.do")
	@ResponseBody
	public Map<String, Object> updateCheckBillDetail(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String baseToolCode = request.getParameter("BASE_TOOL_CODE");
		String baseToolNormalFlag = request.getParameter("BASE_TOOL_NORMAL_FALG");
		String baseToolRemark = request.getParameter("DETAIL_REMARK");
		CheckBillDetail checkBillDetail = new CheckBillDetail();
		checkBillDetail.setBaseToolCode(baseToolCode);
		checkBillDetail.setBaseToolStatus(1L);
		checkBillDetail.setBaseToolRemark(baseToolRemark);
		checkBillDetail.setBaseToolNormalFlag(BaseUtil.strToLong(baseToolNormalFlag));
		return checkBillDetailService.updateCheckBillDetail(checkBillDetail);
	}

	/**
	 * 跳转到出入库单据管理首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/index.do", method = RequestMethod.GET)
	public ModelAndView toIndex() {
		return new ModelAndView("/gqj/check_bill/index");
	}

	@Autowired
	private IDictionaryService dictionaryService;

	/**
	 * 查询下拉列表
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/queryYesOrNoFlagDropList.do")
	@ResponseBody
	public void queryNormalFlagDropList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.getWriter().print(dictionaryService.getDictionarysByDicCode("YESORNO"));
		response.getWriter().flush();
		response.getWriter().close();
	}

}