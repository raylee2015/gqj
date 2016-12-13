package com.base.admin.controller;

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
import com.base.admin.service.IMenuService;

@Controller
@RequestMapping("/base/admin")
public class AdminIndexController {

	public static final Logger LOGGER = Logger
			.getLogger(AdminIndexController.class);

	@RequestMapping(value = "/index.do", method = RequestMethod.GET)
	public String toIndex(HttpServletRequest request, Model model) {
		return "/base/admin/index";
	}

	@Autowired
	private IMenuService menuService;

	@RequestMapping("/queryMenuList.do")
	@ResponseBody
	public Map<String, Object> queryMenuList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List<Map<String, Object>> depts = menuService
				.selectMenusForList(new Menu());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", depts);
		return map;
	}
}
