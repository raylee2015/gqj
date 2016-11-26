package com.esemip.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.esemip.service.TestService;

@Controller
@RequestMapping("/test")
public class TestController {

	public static final Logger LOGGER = Logger.getLogger(TestController.class);

	@Autowired
	private TestService testService;

	@RequestMapping("/showUser")
	public String toIndex(HttpServletRequest request, Model model) throws Exception {
		String result = testService.testQuery();
		model.addAttribute("user", result);
		return "showUser";
	}

	@RequestMapping("/test")
	public void test(HttpServletRequest request, HttpServletResponse response) {
		try {
			String result = testService.testQuery();
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().print(result);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
