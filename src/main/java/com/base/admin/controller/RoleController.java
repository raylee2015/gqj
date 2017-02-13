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
import com.base.admin.entity.Role;
import com.base.admin.service.IMenuService;
import com.base.admin.service.IRoleService;
import com.base.util.BaseUtil;

@Deprecated
@Controller
@RequestMapping("/base/admin/role")
public class RoleController {

	public static final Logger LOGGER = Logger
			.getLogger(RoleController.class);

	@Autowired
	private IMenuService menuService;

	@Autowired
	private IRoleService roleService;

	@RequestMapping("/addNewRole.do")
	@ResponseBody
	public Map<String, Object> addNewRole(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String roleName = request.getParameter("ROLE_NAME");
		String roleSort = request.getParameter("ROLE_SORT");
		String menuId = request.getParameter("MENU_ID");
		Role role = new Role();
		role.setRoleId(-1l);
		role.setRoleName(roleName);
		role.setRoleSort(BaseUtil.strToLong(roleSort));
		role.setMenuId(BaseUtil.strToLong(menuId));
		int bool = roleService.insertSelective(role);
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
	 * 删除角色
	 * 
	 * @param roleIds
	 *            角色id串
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delRoles.do")
	@ResponseBody
	public Map<String, Object> delRoles(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String roleIds = request.getParameter("ROLE_IDS");
		Map<String, Object> map = new HashMap<String, Object>();
		Role role = new Role();
		role.setIds(roleIds);
		int bool = roleService.deleteByPrimaryKeys(role);
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
	 * 查询角色树
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
				.queryMenusForTree(new Menu()).toLowerCase());
		response.getWriter().flush();
		response.getWriter().close();
	}

	/**
	 * 分页查询角色列表
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
	@RequestMapping("/queryRolePage.do")
	@ResponseBody
	public Map<String, Object> queryRolePage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String keyWord = request.getParameter("keyWord");
		String menuId = request.getParameter("MENU_ID");
		Role role = new Role();
		role.setMenuId(BaseUtil.strToLong(menuId));
		role.setCurrPage(Integer.parseInt(page));
		role.setPageSize(Integer.parseInt(rows));
		role.setKeyWord(keyWord);
		List<Map<String, Object>> roles = roleService
				.selectRolesForPage(role);
		int count = roleService.selectCountOfRolesForPage(role);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", roles);
		map.put("total", count);
		return map;
	}

	/**
	 * 跳转到角色管理首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/index.do", method = RequestMethod.GET)
	public ModelAndView toIndex() {
		return new ModelAndView("/base/admin/role");
	}

	@RequestMapping("/updateRole.do")
	@ResponseBody
	public Map<String, Object> updateRole(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String roleId = request.getParameter("ROLE_ID");
		String roleName = request.getParameter("ROLE_NAME");
		String roleSort = request.getParameter("ROLE_SORT");
		String menuId = request.getParameter("MENU_ID");
		Map<String, Object> map = new HashMap<String, Object>();
		Role role = new Role();
		role.setRoleId(BaseUtil.strToLong(roleId));
		role.setRoleName(roleName);
		role.setMenuId(BaseUtil.strToLong(menuId));
		role.setRoleSort(BaseUtil.strToLong(roleSort));
		int bool = roleService.updateByPrimaryKeySelective(role);
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
