package com.base.admin.controller;

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

import com.base.admin.entity.Menu;
import com.base.admin.entity.Param;
import com.base.admin.service.IMenuService;
import com.base.admin.service.IParamService;
import com.base.util.BaseUtil;

@Controller
@RequestMapping("/base/admin/param")
public class ParamController {
	public static final Logger LOGGER = Logger
			.getLogger(ParamController.class);

	@Autowired
	private IMenuService menuService;

	@Autowired
	private IParamService paramService;

	/**
	 * 添加全局参数信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addNewParam.do")
	@ResponseBody
	public Map<String, Object> addNewParam(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String paramKey = request.getParameter("PARAM_KEY");
		String paramValue = request.getParameter("PARAM_VALUE");
		String paramRemark = request.getParameter("PARAM_REMARK");
		String menuId = request.getParameter("MENU_ID");
		Param param = new Param();
		param.setParamId(-1l);
		param.setParamKey(paramKey);
		param.setParamRemark(paramRemark);
		param.setParamValue(paramValue);
		param.setMenuId(BaseUtil.strToLong(menuId));
		return paramService.insertSelective(param);
	}

	/**
	 * 删除全局参数
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delParams.do")
	@ResponseBody
	public Map<String, Object> delParams(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String paramIds = request.getParameter("PARAM_IDS");
		Map<String, Object> map = new HashMap<>();
		Param param = new Param();
		param.setIds(paramIds);
		int bool = paramService.deleteByPrimaryKeys(param);
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
	 * 跳转到全局参数管理操作页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/openEditUI.do", method = RequestMethod.GET)
	public ModelAndView openEditUI(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView("/base/admin/param/editUI");
	}

	/**
	 * 查询系统树
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/queryMenuTree.do")
	@ResponseBody
	public void queryMenuTree(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Menu menu = new Menu();
		// 查询子系统
		menu.setMenuLevel("1");
		response.getWriter()
				.print(menuService.queryMenusForTree(menu));
		response.getWriter().flush();
		response.getWriter().close();
	}

	/**
	 * 分页查询全局参数列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryParamsPage.do")
	@ResponseBody
	public Map<String, Object> queryParamsPage(
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String menuId = request.getParameter("MENU_ID");
		String keyWord = request.getParameter("keyWord");
		Param param = new Param();
		param.setCurrPage(Integer.parseInt(page));
		param.setPageSize(Integer.parseInt(rows));
		param.setKeyWord(keyWord);
		param.setMenuId(BaseUtil.strToLong(menuId));
		return paramService.queryParamsForPage(param);
	}

	/**
	 * 跳转到全局参数管理首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/index.do", method = RequestMethod.GET)
	public ModelAndView toIndex() {
		return new ModelAndView("/base/admin/param/index");
	}

	/**
	 * 更新全局参数信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateParam.do")
	@ResponseBody
	public Map<String, Object> updateParam(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String paramId = request.getParameter("PARAM_ID");
		String paramKey = request.getParameter("PARAM_KEY");
		String paramValue = request.getParameter("PARAM_VALUE");
		String paramRemark = request.getParameter("PARAM_REMARK");
		String menuId = request.getParameter("MENU_ID");
		Map<String, Object> map = new HashMap<String, Object>();
		Param param = new Param();
		param.setParamId(BaseUtil.strToLong(paramId));
		param.setParamKey(paramKey);
		param.setParamRemark(paramRemark);
		param.setParamValue(paramValue);
		param.setMenuId(BaseUtil.strToLong(menuId));
		int bool = paramService.updateByPrimaryKeySelective(param);
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
