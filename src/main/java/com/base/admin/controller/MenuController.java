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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.base.admin.entity.Menu;
import com.base.admin.service.IMenuService;

@Controller
@RequestMapping("/base/admin/menu")
public class MenuController {

	public static final Logger LOGGER = Logger
			.getLogger(MenuController.class);

	/**
	 * 跳转到菜单管理首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/index.do", method = RequestMethod.GET)
	public ModelAndView toIndex() {
		return new ModelAndView("/base/admin/menu");
	}

	@Autowired
	private IMenuService menuService;

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
		String keyWord = request.getParameter("keyWord");
		Menu menu = new Menu();
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
	public Map<String, Object> addNewMenu(
			@RequestParam("MENU_NAME") String menuName,
			@RequestParam("MENU_LEVEL") String menuLevel,
			@RequestParam("MENU_URL") String menuUrl,
			@RequestParam("MENU_SORT") long menuSort,
			@RequestParam("UP_MENU_ID") long upMenuId,
			@RequestParam("MENU_EXT_CODE") String menuExtCode,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Menu menu = new Menu();
		menu.setMenuId(-1l);
		menu.setMenuUrl(menuUrl);
		menu.setMenuLevel(menuLevel);
		menu.setMenuName(menuName);
		menu.setMenuSort(menuSort);
		menu.setUpMenuId(upMenuId);
		menu.setMenuExtCode(menuExtCode);
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
	public Map<String, Object> updateMenu(
			@RequestParam("MENU_ID") long menuId,
			@RequestParam("MENU_NAME") String menuName,
			@RequestParam("MENU_LEVEL") String menuLevel,
			@RequestParam("MENU_URL") String menuUrl,
			@RequestParam("MENU_SORT") long menuSort,
			@RequestParam("UP_MENU_ID") long upMenuId,
			@RequestParam("MENU_EXT_CODE") String menuExtCode,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Menu menu = new Menu();
		menu.setMenuId(menuId);
		menu.setMenuUrl(menuUrl);
		menu.setMenuLevel(menuLevel);
		menu.setMenuName(menuName);
		menu.setMenuSort(menuSort);
		menu.setUpMenuId(upMenuId);
		menu.setMenuExtCode(menuExtCode);
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
			@RequestParam(value = "MENU_IDS") String menuIds,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
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

}
