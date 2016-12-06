package com.base.admin.controller;

import java.util.HashMap;
import java.util.List;
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
import com.base.admin.service.IDictionaryService;
import com.base.admin.service.IMenuService;
import com.base.util.BaseUtil;

@Controller
@RequestMapping("/base/admin/menu")
public class MenuController {

	public static final Logger LOGGER = Logger
			.getLogger(MenuController.class);

	@Autowired
	private IDictionaryService dictionaryService;

	@Autowired
	private IMenuService menuService;

	/**
	 * @Description 添加菜单信息
	 * @Author RayLee
	 * @Version 1.0
	 * @date 2016年12月2日
	 * @param menuName
	 *            菜单名称
	 * @param menuLevel
	 *            菜单级别（0根节点;1子系统;2菜单模块;3菜单项;4扩展权限）
	 * @param menuUrl
	 *            菜单连接
	 * @param menuSort
	 *            菜单排序号
	 * @param upMenuId
	 *            菜单父id
	 * @param menuExtCode
	 *            扩展权限代码
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addNewMenu.do")
	@ResponseBody
	public Map<String, Object> addNewMenu(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String menuName = request.getParameter("MENU_NAME");
		String menuLevel = request.getParameter("MENU_LEVEL");
		String menuUrl = request.getParameter("MENU_URL");
		String menuSort = request.getParameter("MENU_SORT");
		String upMenuId = request.getParameter("UP_MENU_ID");
		String menuExtCode = request.getParameter("MENU_EXT_CODE");
		String menuInnerCode = request.getParameter("MENU_INNER_CODE");
		Menu menu = new Menu();
		menu.setMenuId(-1l);
		menu.setMenuUrl(menuUrl);
		menu.setMenuLevel(menuLevel);
		menu.setMenuName(menuName);
		menu.setMenuSort(BaseUtil.strToLong(menuSort));
		menu.setUpMenuId(BaseUtil.strToLong(upMenuId));
		menu.setMenuExtCode(menuExtCode);
		menu.setMenuInnerCode(menuInnerCode);
		int bool = menuService.insertSelective(menu);
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
	 * 删除菜单
	 * 
	 * @param menuIds
	 *            菜单id串
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delMenus.do")
	@ResponseBody
	public Map<String, Object> delMenus(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String menuIds = request.getParameter("MENU_IDS");
		Map<String, Object> map = new HashMap<>();
		Menu menu = new Menu();
		menu.setIds(menuIds);
		int bool = menuService.deleteByPrimaryKeys(menu);
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
	 * 查询菜单级别下拉列表
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/queryMenuLevelDropList.do")
	@ResponseBody
	public void queryMenuLevelDropList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.getWriter().print(dictionaryService
				.getDictionarysByDicCode("MENU_LEVEL"));
		response.getWriter().flush();
		response.getWriter().close();
	}

	/**
	 * 分页查询菜单列表
	 * 
	 * @param page
	 *            页数
	 * @param rows
	 *            显示行数
	 * @param keyWord
	 *            关键字
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryMenuPage.do")
	@ResponseBody
	public Map<String, Object> queryMenuPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String menuInnerCode = request.getParameter("menuInnerCode");
		String keyWord = request.getParameter("keyWord");
		Menu menu = new Menu();
		menu.setMenuInnerCode(menuInnerCode);
		menu.setCurrPage(Integer.parseInt(page));
		menu.setPageSize(Integer.parseInt(rows));
		menu.setKeyWord(keyWord);
		List<Map<String, Object>> menus = menuService
				.selectMenusForPage(menu);
		int count = menuService.selectCountOfMenusForPage(menu);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", menus);
		map.put("total", count);
		return map;
	}

	/**
	 * 查询菜单树
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/queryMenuTree.do")
	@ResponseBody
	public void queryMenuTree(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.getWriter().print(menuService
				.selectMenusForTree(new Menu()).toLowerCase());
		response.getWriter().flush();
		response.getWriter().close();
	}

	/**
	 * 跳转到菜单管理首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/index.do", method = RequestMethod.GET)
	public ModelAndView toIndex() {
		return new ModelAndView("/base/admin/menu");
	}

	/**
	 * 更新级联数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateInnerData.do")
	@ResponseBody
	public Map<String, Object> updateInnerData(
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int bool = menuService.updataInnerData();
		if (bool == 0) {
			map.put("success", false);
			map.put("msg", "更新级联数据失败，请联系管理员");
		} else {
			map.put("success", true);
			map.put("msg", "更新级联数据成功");
		}
		return map;
	}

	/**
	 * @Description 更新菜单信息
	 * @Author RayLee
	 * @Version 1.0
	 * @date 2016年12月2日
	 * @param menuId
	 *            菜单id
	 * @param menuName
	 *            菜单名称
	 * @param menuLevel
	 *            菜单级别（0根节点;1子系统;2菜单模块;3菜单项;4扩展权限）
	 * @param menuUrl
	 *            菜单连接
	 * @param menuSort
	 *            菜单排序号
	 * @param upMenuId
	 *            菜单父id
	 * @param menuExtCode
	 *            扩展权限代码
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateMenu.do")
	@ResponseBody
	public Map<String, Object> updateMenu(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String menuId = request.getParameter("MENU_ID");
		String menuName = request.getParameter("MENU_NAME");
		String menuLevel = request.getParameter("MENU_LEVEL");
		String menuUrl = request.getParameter("MENU_URL");
		String menuSort = request.getParameter("MENU_SORT");
		String upMenuId = request.getParameter("UP_MENU_ID");
		String menuExtCode = request.getParameter("MENU_EXT_CODE");
		String menuInnerCode = request.getParameter("MENU_INNER_CODE");
		Map<String, Object> map = new HashMap<String, Object>();
		Menu menu = new Menu();
		menu.setMenuId(BaseUtil.strToLong(menuId));
		menu.setMenuUrl(menuUrl);
		menu.setMenuLevel(menuLevel);
		menu.setMenuName(menuName);
		menu.setMenuSort(BaseUtil.strToLong(menuSort));
		menu.setUpMenuId(BaseUtil.strToLong(upMenuId));
		menu.setMenuExtCode(menuExtCode);
		menu.setMenuInnerCode(menuInnerCode);
		int bool = menuService.updateByPrimaryKeySelective(menu);
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
