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
import com.bpbj.entity.Storage;
import com.bpbj.service.IBPBJStorageService;

@Controller
@RequestMapping("/bpbj/storage")
public class BPBJStorageController extends BaseController {
	public static final Logger LOGGER = Logger
			.getLogger(BPBJStorageController.class);

	@Autowired
	private IBPBJStorageService storageService;

	/**
	 * 添加仓库信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addNewStorage.do")
	@ResponseBody
	public Map<String, Object> addNewStorage(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String storeName = request
				.getParameter("STORE_NAME");
		long storeDeptId = getSessionUser(request, response)
				.getUserDeptId();
		Storage storage = new Storage();
		storage.setStoreId(-1l);
		storage.setStoreName(storeName);
		storage.setStoreDeptId(storeDeptId);
		return storageService.addNewStorage(storage);
	}

	/**
	 * 删除仓库
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delStorages.do")
	@ResponseBody
	public Map<String, Object> delStorages(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String storageIds = request
				.getParameter("STORE_IDS");
		Storage storage = new Storage();
		storage.setIds(storageIds);
		return storageService.deleteStorages(storage);
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
	 * 跳转到仓库管理首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/index.do", method = RequestMethod.GET)
	public ModelAndView toIndex() {
		return new ModelAndView("/bpbj/storage/index");
	}

	/**
	 * 跳转到仓库管理操作页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/openEditUI.do", method = RequestMethod.GET)
	public ModelAndView openEditUI(
			HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView("/bpbj/storage/editUI");
	}

	/**
	 * 更新仓库信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateStorage.do")
	@ResponseBody
	public Map<String, Object> updateStorage(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String storeId = request.getParameter("STORE_ID");
		String storeName = request
				.getParameter("STORE_NAME");
		long storeDeptId = getSessionUser(request, response)
				.getUserDeptId();
		Storage storage = new Storage();
		storage.setStoreId(BaseUtil.strToLong(storeId));
		storage.setStoreName(storeName);
		storage.setStoreDeptId(storeDeptId);
		return storageService.updateStorage(storage);
	}

}
