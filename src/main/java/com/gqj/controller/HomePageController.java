package com.gqj.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.base.controller.BaseController;
import com.gqj.service.IHomePageService;

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
		return new ModelAndView("/gqj/homepage/homepage");
	}

	@Autowired
	private IHomePageService homePageService;

	/**
	 * 查询需要归还的工器具
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/queryNeedReturnTools.do")
	@ResponseBody
	public Map<String, Object> queryNeedReturnTools(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String keyWord = request.getParameter("keyWord");
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("keyWord", keyWord);
		param.put("currPage", page);
		param.put("pageSize", rows);
		param.put("tool_dept_id",
				getSessionUser(request, response)
						.getUserDeptId());
		return homePageService
				.selectNeedReturnToolsForPage(param);
	}

}
