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
import com.gqj.entity.Manufacturer;
import com.gqj.service.IManufacturerService;

@Controller
@RequestMapping("/gqj/manufacturer")
public class ManufacturerController extends BaseController {
	public static final Logger LOGGER = Logger
			.getLogger(ManufacturerController.class);

	@Autowired
	private IManufacturerService manufacturerService;

	/**
	 * 添加厂家信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addNewManufacturer.do")
	@ResponseBody
	public Map<String, Object> addNewManufacturer(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String manName = request.getParameter("MAN_NAME");
		Manufacturer manufacturer = new Manufacturer();
		manufacturer.setManId(-1l);
		manufacturer.setManName(manName);
		return manufacturerService
				.addNewManufacturer(manufacturer);
	}

	/**
	 * 删除厂家
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delManufacturers.do")
	@ResponseBody
	public Map<String, Object> delManufacturers(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String manufacturerIds = request
				.getParameter("MAN_IDS");
		Manufacturer manufacturer = new Manufacturer();
		manufacturer.setIds(manufacturerIds);
		return manufacturerService
				.deleteManufacturers(manufacturer);
	}

	/**
	 * 分页查询厂家列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryManufacturersPage.do")
	@ResponseBody
	public Map<String, Object> queryManufacturersPage(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String keyWord = request.getParameter("keyWord");
		Manufacturer manufacturer = new Manufacturer();
		manufacturer.setCurrPage(Integer.parseInt(page));
		manufacturer.setPageSize(Integer.parseInt(rows));
		manufacturer.setKeyWord(keyWord);
		return manufacturerService
				.selectManufacturersForPage(manufacturer);
	}

	/**
	 * 跳转到厂家管理首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/index.do", method = RequestMethod.GET)
	public ModelAndView toIndex() {
		return new ModelAndView("/gqj/manufacturer/index");
	}

	/**
	 * 跳转到厂家管理操作页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/openEditUI.do", method = RequestMethod.GET)
	public ModelAndView openEditUI(
			HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView("/gqj/manufacturer/editUI");
	}

	/**
	 * 更新厂家信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateManufacturer.do")
	@ResponseBody
	public Map<String, Object> updateManufacturer(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String manId = request.getParameter("MAN_ID");
		String manName = request.getParameter("MAN_NAME");
		Manufacturer manufacturer = new Manufacturer();
		manufacturer.setManId(BaseUtil.strToLong(manId));
		manufacturer.setManName(manName);
		return manufacturerService
				.updateManufacturer(manufacturer);
	}

}
