package com.index.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.base.admin.entity.User;
import com.base.admin.service.IUserService;
import com.base.util.BaseUtil;

@Controller
@RequestMapping("/login")
public class LoginController {

	@Autowired
	private IUserService userService;

	public static final Logger LOGGER = Logger
			.getLogger(LoginController.class);

	@RequestMapping("/login.do")
	public String login(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 查询用户
		String userCode = request.getParameter("userCode");
		String userPassWord = request.getParameter("userPassWord");
		userPassWord = BaseUtil.MD5(userPassWord).substring(0, 20);
		User user = new User();
		user.setUserCode(userCode);
		user.setUserPassWord(userPassWord);
		List<User> list = userService.selectUsersForList(user);
		if (list.size() == 1) {
			// 设置session
			request.getSession().setAttribute("user", list.get(0));
			// 跳转
			return "/index";
		} else {
			return "/login";
		}

	}
}
