package com.base.admin.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/base/admin")
public class AdminIndexController {

	public static final Logger LOGGER = Logger
			.getLogger(AdminIndexController.class);

	@RequestMapping(value = "/index.do", method = RequestMethod.GET)
	public String toIndex(HttpServletRequest request, Model model) {
		return "/base/admin/index";
	}
}
