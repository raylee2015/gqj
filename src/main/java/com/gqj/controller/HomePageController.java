package com.gqj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.base.controller.BaseController;

@Controller
@RequestMapping("/gqj/homepage")
public class HomePageController extends BaseController {

	/**
	 * 跳转到首页管理首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/index.do", method = RequestMethod.GET)
	public ModelAndView toIndex() {
		return new ModelAndView("/gqj/homepage");
	}

}
