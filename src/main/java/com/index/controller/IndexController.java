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

import com.base.admin.entity.User;
import com.base.admin.service.IMenuService;
import com.base.admin.service.IUserService;
import com.base.util.BaseUtil;

@Controller
@RequestMapping("/")
public class IndexController {

	public static final Logger LOGGER = Logger
			.getLogger(IndexController.class);

	@RequestMapping(value = "/index.do", method = RequestMethod.GET)
	public String toIndex(HttpServletRequest request, Model model) {
		return "/index";
	}

	@Autowired
	private IMenuService menuService;

	@RequestMapping("/queryMenuList.do")
	@ResponseBody
	public Map<String, Object> queryMenuList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		User user = (User) request.getSession().getAttribute("user");
		List<Map<String, Object>> depts = menuService
				.selectMenusForList(user);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", depts);
		return map;
	}

	@Autowired
	private IUserService userService;

	@RequestMapping("/login.do")
	public String login(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 查询用户
		String userCode = request.getParameter("userCode");
		String userPassWord = request.getParameter("userPassWord");
		if (userCode != null && !"".equals(userCode)
				&& userPassWord != null && !"".equals(userPassWord)) {

			userPassWord = BaseUtil.MD5(userPassWord).substring(0, 20);
			User user = new User();
			user.setUserCode(userCode);
			user.setUserPassWord(userPassWord);
			user = userService.selectUsersForObject(user);
			if (user != null) {
				// 设置session
				request.getSession().setAttribute("user", user);
				// 跳转
				return "/index";
			} else {
				return "/login";
			}
		} else {
			return "/login";
		}

	}
}
