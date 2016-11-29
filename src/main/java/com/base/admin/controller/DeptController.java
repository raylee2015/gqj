package com.base.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.base.admin.entity.Dept;
import com.base.admin.service.IDeptService;

@Controller
@RequestMapping("/base/admin/dept")
public class DeptController {

	public static final Logger LOGGER = Logger
			.getLogger(DeptController.class);

	@RequestMapping(value = "/index.do", method = RequestMethod.GET)
	public String toIndex() {
		return "dept";
	}

	@Autowired
	private IDeptService deptService;

	@RequestMapping("/deptList.do")
	@ResponseBody
	/**
	 * 分页查询部门列表
	 * 
	 * @param page
	 *            页数
	 * @param rows
	 *            显示行数
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> selectDeptsForGrid(int page, int rows,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List<Dept> depts = deptService.selectDeptsForList(null);
		int count = deptService.selectCountOfDeptsForPage(null);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", depts);
		map.put("total", count);
		return map;
	}

	@RequestMapping("/deptTree.do")
	@ResponseBody
	public void selectDeptsForTree(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setCharacterEncoding("UTF-8");
		response.getWriter()
				.print(deptService.selectDeptsForTree().toLowerCase());
		response.getWriter().flush();
		response.getWriter().close();
	}

}
