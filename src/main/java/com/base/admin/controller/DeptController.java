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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.base.admin.entity.Dept;
import com.base.admin.service.IDeptService;

@Controller
@RequestMapping("/base/admin/dept")
public class DeptController {

	public static final Logger LOGGER = Logger
			.getLogger(DeptController.class);

	@RequestMapping(value = "/index.do", method = RequestMethod.GET)
	public ModelAndView toIndex() {
		return new ModelAndView("/base/admin/dept");
	}

	@Autowired
	private IDeptService deptService;

	@RequestMapping("/queryDeptPage.do")
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
	public Map<String, Object> queryDeptPage(int page, int rows,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		Dept dept = new Dept();
		dept.setCurrPage(page);
		dept.setPageSize(rows);
		List<Map<String, Object>> depts = deptService
				.selectDeptsForPage(dept);
		int count = deptService.selectCountOfDeptsForPage(dept);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", depts);
		map.put("total", count);
		return map;
	}

	@RequestMapping("/queryDeptTree.do")
	@ResponseBody
	public void queryDeptTree(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.getWriter()
				.print(deptService.selectDeptsForTree().toLowerCase());
		response.getWriter().flush();
		response.getWriter().close();
	}

	@RequestMapping("/addNewDept.do")
	@ResponseBody
	public Map<String, Object> addNewDept(
			@RequestParam("DEPT_NAME") String deptName,
			@RequestParam("DEPT_SORT") long deptSort,
			@RequestParam("UP_DEPT_ID") long upDeptId,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Dept dept = new Dept();
		dept.setDeptId(-1l);
		dept.setDeptName(deptName);
		dept.setDeptSort(deptSort);
		dept.setUpDeptId(upDeptId);
		int bool = deptService.insertSelective(dept);
		if (bool == 0) {
			map.put("success", false);
			map.put("msg", "保存出错，请联系管理员");
		} else {
			map.put("success", true);
			map.put("msg", "保存成功");
		}
		return map;
	}

	@RequestMapping("/updateDept.do")
	@ResponseBody
	public Map<String, Object> updateDept(
			@RequestParam("DEPT_ID") long deptId,
			@RequestParam("DEPT_NAME") String deptName,
			@RequestParam("DEPT_SORT") long deptSort,
			@RequestParam("UP_DEPT_ID") long upDeptId,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Dept dept = new Dept();
		dept.setDeptId(deptId);
		dept.setDeptName(deptName);
		dept.setDeptSort(deptSort);
		dept.setUpDeptId(upDeptId);
		int bool = deptService.updateByPrimaryKeySelective(dept);
		if (bool == 0) {
			map.put("success", false);
			map.put("msg", "保存出错，请联系管理员");
		} else {
			map.put("success", true);
			map.put("msg", "保存成功");
		}
		return map;
	}
	
	@RequestMapping("/delDepts.do")
	@ResponseBody
	public Map<String, Object> delDepts(
			@RequestParam("DEPT_IDS") String deptIds,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int bool = deptService.deleteByPrimaryKeys(deptIds);
		if (bool == 0) {
			map.put("success", false);
			map.put("msg", "删除失败，请联系管理员");
		} else {
			map.put("success", true);
			map.put("msg", "删除成功");
		}
		return map;
	}

}
