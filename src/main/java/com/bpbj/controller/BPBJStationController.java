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
import com.bpbj.entity.Station;
import com.bpbj.service.IBPBJStationService;

@Controller
@RequestMapping("/bpbj/station")
public class BPBJStationController extends BaseController {
	public static final Logger LOGGER = Logger
			.getLogger(BPBJStationController.class);

	@Autowired
	private IBPBJStationService stationService;

	/**
	 * 添加变电站信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addNewStation.do")
	@ResponseBody
	public Map<String, Object> addNewStation(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String storeName = request
				.getParameter("STATION_NAME");
		Station station = new Station();
		station.setStationId(-1l);
		station.setStationName(storeName);
		return stationService.addNewStation(station);
	}

	/**
	 * 删除变电站
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delStations.do")
	@ResponseBody
	public Map<String, Object> delStations(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String stationIds = request
				.getParameter("STATION_IDS");
		Station station = new Station();
		station.setIds(stationIds);
		return stationService.deleteStations(station);
	}

	/**
	 * 分页查询变电站列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryStationsPage.do")
	@ResponseBody
	public Map<String, Object> queryStationsPage(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String keyWord = request.getParameter("keyWord");
		Station station = new Station();
		station.setCurrPage(Integer.parseInt(page));
		station.setPageSize(Integer.parseInt(rows));
		station.setKeyWord(keyWord);
		return stationService
				.selectStationsForPage(station);
	}

	/**
	 * 跳转到变电站管理首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/index.do", method = RequestMethod.GET)
	public ModelAndView toIndex() {
		return new ModelAndView("/bpbj/station/index");
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
		return new ModelAndView("/bpbj/station/editUI");
	}

	/**
	 * 更新变电站信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateStation.do")
	@ResponseBody
	public Map<String, Object> updateStation(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String storeId = request.getParameter("STATION_ID");
		String storeName = request
				.getParameter("STATION_NAME");
		Station station = new Station();
		station.setStationId(BaseUtil.strToLong(storeId));
		station.setStationName(storeName);
		return stationService.updateStation(station);
	}

}
