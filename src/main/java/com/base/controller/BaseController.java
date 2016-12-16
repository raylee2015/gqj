package com.base.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.base.admin.entity.User;

/**
 * 基础的控制器，主要用来放置一些公共方法
 * 
 * @author JMSCADMIN
 *
 */
public class BaseController {

	/**
	 * @Description 获取登陆用户
	 * @Author RayLee
	 * @Version 1.0
	 * @date 2016年12月16日
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	protected User getSessionUser(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			request.getRequestDispatcher("/WEB-INF/login.jsp")
					.forward(request, response);
			return null;
		}
		return user;
	}

}
