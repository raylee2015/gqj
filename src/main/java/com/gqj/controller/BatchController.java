package com.gqj.controller;

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
import com.gqj.entity.Batch;
import com.gqj.service.IBatchService;

@Controller
@RequestMapping("/gqj/tool_bill")
public class BatchController extends BaseController {
	public static final Logger LOGGER = Logger
			.getLogger(BatchController.class);

	@Autowired
	private IBatchService batchService;

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
		String posName = request.getParameter("POS_NAME");
		String storeId = request.getParameter("STORE_ID");
		long posDeptId = getSessionUser(request, response)
				.getUserDeptId();
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
	 * 跳转到仓位管理首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/index.do", method = RequestMethod.GET)
	public ModelAndView toIndex() {
		return new ModelAndView("/gqj/tool_bill/index");
	}

	/**
	 * 跳转到仓位管理操作页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/openEditUI.do", method = RequestMethod.GET)
	public ModelAndView openEditUI(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView("/gqj/batch/editUI");
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
		String posId = request.getParameter("POS_ID");
		String posName = request.getParameter("POS_NAME");
		String storeId = request.getParameter("STORE_ID");
		long posDeptId = getSessionUser(request, response)
				.getUserDeptId();
		Batch batch = new Batch();
		return batchService.updateBatch(batch);
	}

}
