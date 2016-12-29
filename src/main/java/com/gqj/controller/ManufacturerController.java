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
		Map<String, Object> map = new HashMap<String, Object>();
		Manufacturer manufacturer = new Manufacturer();
		manufacturer.setManId(-1l);
		manufacturer.setManName(manName);
		int bool = manufacturerService
				.insertSelective(manufacturer);
		if (bool == 0) {
			map.put("success", false);
			map.put("msg", "保存出错，请联系管理员");
		} else {
			map.put("success", true);
			map.put("msg", "保存成功");
		}
		return map;
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
		Map<String, Object> map = new HashMap<>();
		Manufacturer manufacturer = new Manufacturer();
		manufacturer.setIds(manufacturerIds);
		int bool = manufacturerService
				.deleteByPrimaryKeys(manufacturer);
		if (bool == 0) {
			map.put("success", false);
			map.put("msg", "删除失败，请联系管理员");
		} else {
			map.put("success", true);
			map.put("msg", "删除成功");
		}
		return map;
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
		Map<String, Object> map = new HashMap<String, Object>();
		Manufacturer manufacturer = new Manufacturer();
		manufacturer.setManId(BaseUtil.strToLong(manId));
		manufacturer.setManName(manName);
		int bool = manufacturerService
				.updateByPrimaryKeySelective(manufacturer);
		if (bool == 0) {
			map.put("success", false);
			map.put("msg", "保存出错，请联系管理员");
		} else {
			map.put("success", true);
			map.put("msg", "保存成功");
		}
		return map;
	}

}
