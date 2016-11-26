package com.sample.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sample.entity.Sample;
import com.sample.service.ISampleService;

@Controller
@RequestMapping("/sample")
public class SampleController {

	public static final Logger LOGGER = Logger.getLogger(SampleController.class);

	@Autowired
	private ISampleService sampleService;

	@RequestMapping("/sampleTest")
	public String sampleTest(HttpServletRequest request, Model model) throws Exception {
		List<Sample> samples = sampleService.queryUsers();
		model.addAttribute("users", samples);
		return "showUser";
	}
}
