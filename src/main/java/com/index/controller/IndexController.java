package com.index.controller;

import java.sql.Clob;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.base.admin.entity.Menu;
import com.base.admin.entity.User;
import com.base.admin.service.IMenuService;
import com.base.admin.service.IUserService;
import com.base.util.BaseUtil;
import com.index.util.BaseSysParam;

@Controller
@RequestMapping("/")
public class IndexController {

	public static final Logger LOGGER = Logger
			.getLogger(IndexController.class);

	@RequestMapping(value = "/index.do", method = RequestMethod.GET)
	public String toIndex(HttpServletRequest request,
			Model model) {
		Menu menu = new Menu();
		menu.setMenuId(66L);
		Map<String, Object> result = menuService
				.queryMenusForObject(menu);
		String url = request.getContextPath()
				+ "/homePage.do";
		if (result.get("MENU_URL") != null && !"-".equals(
				result.get("MENU_URL").toString())) {
			url = request.getContextPath()
					+ result.get("MENU_URL").toString();
		}
		request.setAttribute("url", url);
		return "/index";
	}

	@RequestMapping(value = "/homePage.do", method = RequestMethod.GET)
	public String toHomePage(HttpServletRequest request,
			Model model) {
		return "/homepage";
	}

	@Autowired
	private IMenuService menuService;

	@RequestMapping("/queryMenuList.do")
	@ResponseBody
	public Map<String, Object> queryMenuList(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		User user = (User) request.getSession()
				.getAttribute("user");
		List<Map<String, Object>> menus = menuService
				.queryMenusForList(user);
		for (Map<String, Object> item : menus) {
			if (item.get(
					"VIEW_MENU_UP_INNER_CODE") instanceof Clob) {
				Clob clob = (Clob) item
						.get("VIEW_MENU_UP_INNER_CODE");
				String viewMenuUpInnerCode = "";
				if (clob != null) {
					viewMenuUpInnerCode = clob.getSubString(
							(long) 1, (int) clob.length());
					item.put("VIEW_MENU_UP_INNER_CODE",
							viewMenuUpInnerCode);
				}
			}
			if (item.get(
					"VIEW_MENU_INNER_CODE") instanceof Clob) {
				Clob clob = (Clob) item
						.get("VIEW_MENU_INNER_CODE");
				String viewMenuInnerCode = "";
				if (clob != null) {
					viewMenuInnerCode = clob.getSubString(
							(long) 1, (int) clob.length());
					item.put("VIEW_MENU_INNER_CODE",
							viewMenuInnerCode);
				}
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", menus);
		return map;
	}

	@Autowired
	private IUserService userService;

	@RequestMapping("/login.do")
	public String login(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String sysRootPath = request.getSession()
				.getServletContext().getRealPath("");
		BaseSysParam.setSysRootPath(sysRootPath);
		// 查询用户
		String userCode = request.getParameter("userCode");
		String userPassWord = request
				.getParameter("userPassWord");
		if (userCode != null && !"".equals(userCode)
				&& userPassWord != null
				&& !"".equals(userPassWord)) {

			userPassWord = BaseUtil.MD5(userPassWord)
					.substring(0, 20);
			User user = new User();
			user.setUserCode(userCode);
			user.setUserPassWord(userPassWord);
			user = userService.queryUserForSession(user);
			if (user != null) {
				// 设置session
				request.getSession().setAttribute("user",
						user);
				// 跳转
				Menu menu = new Menu();
				menu.setMenuId(66L);
				Map<String, Object> result = menuService
						.queryMenusForObject(menu);
				String url = request.getContextPath()
						+ "/homePage.do";
				if (result.get("MENU_URL") != null && !"-"
						.equals(result.get("MENU_URL")
								.toString())) {
					url = request.getContextPath() + result
							.get("MENU_URL").toString();
				}
				request.setAttribute("url", url);
				return "/index";
			} else {
				return "/login";
			}
		} else {
			return "/login";
		}
	}

	@RequestMapping("/logout.do")
	public String logout(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.getSession().removeAttribute("user");
		return "/login";
	}
}
