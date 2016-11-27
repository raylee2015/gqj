package com.sample.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sample.entity.Sample;
import com.sample.service.ISampleService;

@Controller
@RequestMapping("/sample")
public class SampleController {

	public static final Logger LOGGER = Logger.getLogger(SampleController.class);

	@Autowired
	private ISampleService sampleService;

	@RequestMapping(value = "/index.do", method = RequestMethod.GET)
	public String sampleTest() {
		return "sampleTest";
	}

	@RequestMapping("/sampleTestList.do")
	@ResponseBody
	public Map<String, Object> sampleTestList(HttpServletResponse request, HttpServletResponse response)
			throws Exception {
		List<Sample> samples = sampleService.querySamples();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", samples);
		map.put("total", 20);
		return map;
	}
}
