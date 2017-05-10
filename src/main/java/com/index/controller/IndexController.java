package com.index.controller;

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
import com.base.controller.BaseController;
import com.base.util.BaseUtil;
import com.index.util.BaseSysParam;

@Controller
@RequestMapping("/")
public class IndexController extends BaseController {

	public static final Logger LOGGER = Logger.getLogger(IndexController.class);

	@RequestMapping(value = "/index.do", method = RequestMethod.GET)
	public String toIndex(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		Menu menu = new Menu();
		menu.setMenuId(66L);
		String url = request.getContextPath() + "/homePage.do";
		User user = getSessionUser(request, response);
		String deptCode = user.getUserDeptCode();
		if ("JBYB".equals(deptCode) || "JBEB".equals(deptCode) || "JBSB".equals(deptCode)) {
			url = "/bpbj/homepage/index.do";
		} else if ("GLRY".equals(deptCode) || "WYZ".equals(deptCode) || "AFZ".equals(deptCode)) {
			url = "/gqj/homepage/index.do";
		}
		request.setAttribute("url", url);
		return "/index";
	}

	@RequestMapping(value = "/homePage.do", method = RequestMethod.GET)
	public String toHomePage(HttpServletRequest request, Model model) {
		return "/homepage";
	}

	@Autowired
	private IMenuService menuService;

	@RequestMapping("/queryMenuList.do")
	@ResponseBody
	public Map<String, Object> queryMenuList(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		User user = (User) request.getSession().getAttribute("user");
		List<Map<String, Object>> menus = menuService.queryMenusForList(user);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", menus);
		return map;
	}

	@Autowired
	private IUserService userService;

	@RequestMapping("/login.do")
	public String login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String sysRootPath = request.getSession().getServletContext().getRealPath("");
		BaseSysParam.setSysRootPath(sysRootPath);
		// 查询用户
		String userCode = request.getParameter("userCode");
		String userPassWord = request.getParameter("userPassWord");
		if (userCode != null && !"".equals(userCode) && userPassWord != null && !"".equals(userPassWord)) {

			userPassWord = BaseUtil.MD5(userPassWord).substring(0, 20);
			User user = new User();
			user.setUserCode(userCode);
			user.setUserPassWord(userPassWord);
			user = userService.queryUserForSession(user);
			if (user != null) {
				// 设置session
				request.getSession().setAttribute("user", user);
				// 跳转
				String url = "";
				String deptCode = user.getUserDeptCode();
				if ("JBYB".equals(deptCode) || "JBEB".equals(deptCode) || "JBSB".equals(deptCode)) {
					url = "/gqj/bpbj/plugin_inventory/index2.do";
				} else if ("GLRY".equals(deptCode) || "WYZ".equals(deptCode) || "AFZ".equals(deptCode)) {
					url = "/gqj/gqj/homepage/index.do";
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
	public String logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.getSession().removeAttribute("user");
		return "/login";
	}
}
