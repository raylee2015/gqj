package com.base.admin.controller;

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
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addNewMenu.do")
	@ResponseBody
	public Map<String, Object> addNewMenu(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String menuName = request.getParameter("MENU_NAME");
		String menuLevel = request
				.getParameter("MENU_LEVEL");
		String menuUrl = request.getParameter("MENU_URL");
		String menuSort = request.getParameter("MENU_SORT");
		String upMenuId = request
				.getParameter("UP_MENU_ID");
		String menuExtCode = request
				.getParameter("MENU_EXT_CODE");
		String menuInnerCode = request
				.getParameter("MENU_INNER_CODE");
		Menu menu = new Menu();
		menu.setMenuId(-1l);
		menu.setMenuUrl(menuUrl);
		menu.setMenuLevel(menuLevel);
		menu.setMenuName(menuName);
		menu.setMenuSort(BaseUtil.strToLong(menuSort));
		menu.setUpMenuId(BaseUtil.strToLong(upMenuId));
		menu.setMenuExtCode(menuExtCode);
		menu.setMenuInnerCode(menuInnerCode);
		return menuService.addNewMenu(menu);
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
	public Map<String, Object> delMenus(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String menuIds = request.getParameter("MENU_IDS");
		Menu menu = new Menu();
		menu.setIds(menuIds);
		return menuService.deleteMenus(menu);
	}

	/**
	 * 跳转到菜单管理操作页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/openEditUI.do", method = RequestMethod.GET)
	public ModelAndView openEditUI(
			HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView("/base/admin/menu/editUI");
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
	public void queryMenuLevelDropList(
			HttpServletRequest request,
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
	@RequestMapping("/queryMenusPage.do")
	@ResponseBody
	public Map<String, Object> queryMenusPage(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String menuInnerCode = request
				.getParameter("menuInnerCode");
		String keyWord = request.getParameter("keyWord");
		Menu menu = new Menu();
		menu.setMenuInnerCode(menuInnerCode);
		menu.setCurrPage(Integer.parseInt(page));
		menu.setPageSize(Integer.parseInt(rows));
		menu.setKeyWord(keyWord);
		return menuService.queryMenusForPage(menu);
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
		System.out.println(
				"---------------------------------");
		System.out.println(
				menuService.queryMenusForTree(new Menu())
						.toLowerCase());
		System.out.println(
				"---------------------------------");
		response.getWriter()
				.print(menuService
						.queryMenusForTree(new Menu())
						.toLowerCase());
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
		return new ModelAndView("/base/admin/menu/index");
	}

	/**
	 * @Description 更新菜单信息
	 * @Author RayLee
	 * @Version 1.0
	 * @date 2016年12月2日
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateMenu.do")
	@ResponseBody
	public Map<String, Object> updateMenu(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String menuId = request.getParameter("MENU_ID");
		String menuName = request.getParameter("MENU_NAME");
		String menuLevel = request
				.getParameter("MENU_LEVEL");
		String menuUrl = request.getParameter("MENU_URL");
		String menuSort = request.getParameter("MENU_SORT");
		String upMenuId = request
				.getParameter("UP_MENU_ID");
		String menuExtCode = request
				.getParameter("MENU_EXT_CODE");
		String menuInnerCode = request
				.getParameter("MENU_INNER_CODE");
		Menu menu = new Menu();
		menu.setMenuId(BaseUtil.strToLong(menuId));
		menu.setMenuUrl(menuUrl);
		menu.setMenuLevel(menuLevel);
		menu.setMenuName(menuName);
		menu.setMenuSort(BaseUtil.strToLong(menuSort));
		menu.setUpMenuId(BaseUtil.strToLong(upMenuId));
		menu.setMenuExtCode(menuExtCode);
		menu.setMenuInnerCode(menuInnerCode);
		return menuService.updateMenu(menu);
	}

}
