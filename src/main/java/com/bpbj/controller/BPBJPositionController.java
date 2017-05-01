package com.bpbj.controller;

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
import com.bpbj.entity.Position;
import com.bpbj.entity.Storage;
import com.bpbj.service.IBPBJPositionService;
import com.bpbj.service.IBPBJStorageService;

@Controller
@RequestMapping("/bpbj/position")
public class BPBJPositionController extends BaseController {
	public static final Logger LOGGER = Logger
			.getLogger(BPBJPositionController.class);

	@Autowired
	private IBPBJPositionService positionService;

	@Autowired
	private IBPBJStorageService storageService;

	/**
	 * 添加仓位信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addNewPosition.do")
	@ResponseBody
	public Map<String, Object> addNewPosition(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String posName = request.getParameter("POS_NAME");
		String storeId = request.getParameter("STORE_ID");
		long posDeptId = getSessionUser(request, response)
				.getUserDeptId();
		Position position = new Position();
		position.setPosId(-1l);
		position.setStoreId(BaseUtil.strToLong(storeId));
		position.setPosName(posName);
		position.setPosDeptId(posDeptId);
		return positionService.addNewPosition(position);
	}

	/**
	 * 删除仓位
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delPositions.do")
	@ResponseBody
	public Map<String, Object> delPositions(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String positionIds = request
				.getParameter("POS_IDS");
		Position position = new Position();
		position.setIds(positionIds);
		return positionService.deletePositions(position);
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
		long posDeptId = getSessionUser(request, response)
				.getUserDeptId();
		String keyWord = request.getParameter("keyWord");
		Position position = new Position();
		position.setCurrPage(Integer.parseInt(page));
		position.setPageSize(Integer.parseInt(rows));
		position.setKeyWord(keyWord);
		position.setPosDeptId(posDeptId);
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
	@RequestMapping("/queryStoragePage.do")
	@ResponseBody
	public Map<String, Object> queryStoragePage(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		long storeDeptId = getSessionUser(request, response)
				.getUserDeptId();
		String keyWord = request.getParameter("keyWord");
		Storage storage = new Storage();
		storage.setCurrPage(Integer.parseInt(page));
		storage.setPageSize(Integer.parseInt(rows));
		storage.setKeyWord(keyWord);
		storage.setStoreDeptId(storeDeptId);
		return storageService
				.selectStoragesForPage(storage);
	}

	/**
	 * 根据条件查询仓位列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryPositionObject.do")
	@ResponseBody
	public Map<String, Object> queryPositionObject(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String posId = request.getParameter("POS_ID");
		Position position = new Position();
		position.setPosId(BaseUtil.strToLong(posId));
		return positionService
				.selectPositionsForObject(position);
	}

	/**
	 * 跳转到仓位管理首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/index.do", method = RequestMethod.GET)
	public ModelAndView toIndex() {
		return new ModelAndView("/bpbj/position/index");
	}

	/**
	 * 跳转到仓位管理操作页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/openEditUI.do", method = RequestMethod.GET)
	public ModelAndView openEditUI(
			HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView("/bpbj/position/editUI");
	}

	/**
	 * 更新仓位信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updatePosition.do")
	@ResponseBody
	public Map<String, Object> updatePosition(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String posId = request.getParameter("POS_ID");
		String posName = request.getParameter("POS_NAME");
		String storeId = request.getParameter("STORE_ID");
		long posDeptId = getSessionUser(request, response)
				.getUserDeptId();
		Position position = new Position();
		position.setPosId(BaseUtil.strToLong(posId));
		position.setStoreId(BaseUtil.strToLong(storeId));
		position.setPosName(posName);
		position.setPosDeptId(posDeptId);
		return positionService.updatePosition(position);
	}

}
