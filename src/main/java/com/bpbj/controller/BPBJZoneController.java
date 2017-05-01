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

import com.base.admin.service.IDictionaryService;
import com.base.controller.BaseController;
import com.base.util.BaseUtil;
import com.bpbj.entity.Zone;
import com.bpbj.service.IBPBJZoneService;

@Controller
@RequestMapping("/bpbj/zone")
public class BPBJZoneController extends BaseController {
	public static final Logger LOGGER = Logger
			.getLogger(BPBJZoneController.class);

	@Autowired
	private IBPBJZoneService zoneService;

	/**
	 * 添加变电站信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addNewZone.do")
	@ResponseBody
	public Map<String, Object> addNewZone(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String storeName = request
				.getParameter("ZONE_NAME");
		Zone zone = new Zone();
		zone.setZoneId(-1l);
		zone.setZoneName(storeName);
		return zoneService.addNewZone(zone);
	}

	/**
	 * 删除变电站
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delZones.do")
	@ResponseBody
	public Map<String, Object> delZones(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String zoneIds = request
				.getParameter("ZONE_IDS");
		Zone zone = new Zone();
		zone.setIds(zoneIds);
		return zoneService.deleteZones(zone);
	}

	/**
	 * 分页查询变电站列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryZonesPage.do")
	@ResponseBody
	public Map<String, Object> queryZonesPage(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String keyWord = request.getParameter("keyWord");
		Zone zone = new Zone();
		zone.setCurrPage(Integer.parseInt(page));
		zone.setPageSize(Integer.parseInt(rows));
		zone.setKeyWord(keyWord);
		return zoneService
				.selectZonesForPage(zone);
	}

	/**
	 * 跳转到变电站管理首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/index.do", method = RequestMethod.GET)
	public ModelAndView toIndex() {
		return new ModelAndView("/bpbj/zone/index");
	}

	/**
	 * 跳转到变电站管理操作页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/openEditUI.do", method = RequestMethod.GET)
	public ModelAndView openEditUI(
			HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView("/bpbj/zone/editUI");
	}

	/**
	 * 更新变电站信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateZone.do")
	@ResponseBody
	public Map<String, Object> updateZone(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String storeId = request.getParameter("ZONE_ID");
		String storeName = request
				.getParameter("ZONE_NAME");
		Zone zone = new Zone();
		zone.setZoneId(BaseUtil.strToLong(storeId));
		zone.setZoneName(storeName);
		return zoneService.updateZone(zone);
	}
	
	@Autowired
	private IDictionaryService dictionaryService;
	
	/**
	 * 查询间隔类别下拉列表
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/queryZoneTypeDropList.do")
	@ResponseBody
	public void queryMenuLevelDropList(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.getWriter().print(dictionaryService
				.getDictionarysByDicCode("ZONG_TYPE"));
		response.getWriter().flush();
		response.getWriter().close();
	}

}
