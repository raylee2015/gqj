package com.base.admin.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.base.admin.entity.Dept;
import com.base.admin.service.IDeptService;
import com.base.util.BaseUtil;

@Controller
@RequestMapping("/base/admin/dept")
public class DeptController {

	public static final Logger LOGGER = Logger
			.getLogger(DeptController.class);

	@Autowired
	private IDeptService deptService;

	/**
	 * 添加部门信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addNewDept.do")
	@ResponseBody
	public Map<String, Object> addNewDept(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String deptName = request.getParameter("DEPT_NAME");
		String deptSort = request.getParameter("DEPT_SORT");
		String deptCode = request.getParameter("DEPT_CODE");
		String upDeptId = request.getParameter("UP_DEPT_ID");
		Dept dept = new Dept();
		dept.setDeptId(-1l);
		dept.setDeptName(deptName);
		dept.setDeptSort(BaseUtil.strToLong(deptSort));
		dept.setUpDeptId(BaseUtil.strToLong(upDeptId));
		dept.setDeptCode(deptCode);
		return deptService.addNewDept(dept);
	}

	/**
	 * 删除部门
	 * 
	 * @param deptIds
	 *            部门id串
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delDepts.do")
	@ResponseBody
	public Map<String, Object> delDepts(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String deptIds = request.getParameter("DEPT_IDS");
		Dept dept = new Dept();
		dept.setIds(deptIds);
		return deptService.deleteDepts(dept);
	}

	/**
	 * 跳转到部门管理操作页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/openEditUI.do", method = RequestMethod.GET)
	public ModelAndView openEditUI(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView("/base/admin/dept/editUI");
	}

	/**
	 * 分页查询部门列表
	 * 
	 * @param page
	 *            页数
	 * @param rows
	 *            显示行数
	 * @param keyWord
	 *            关键字
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryDeptsPage.do")
	@ResponseBody
	public Map<String, Object> queryDeptsPage(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String deptInnerCode = request.getParameter("deptInnerCode");
		String keyWord = request.getParameter("keyWord");
		Dept dept = new Dept();
		dept.setCurrPage(Integer.parseInt(page));
		dept.setPageSize(Integer.parseInt(rows));
		dept.setDeptInnerCode(deptInnerCode);
		dept.setKeyWord(keyWord);
		return deptService.selectDeptsForPage(dept);
	}

	/**
	 * 查询部门树
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/queryDeptTree.do")
	@ResponseBody
	public void queryDeptTree(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.getWriter()
				.print(deptService.selectDeptsForTree().toLowerCase());
		response.getWriter().flush();
		response.getWriter().close();
	}

	/**
	 * 跳转到部门管理首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/index.do", method = RequestMethod.GET)
	public ModelAndView toIndex() {
		return new ModelAndView("/base/admin/dept/index");
	}

	/**
	 * 更新部门信息
	 * 
	 * @param deptId
	 *            部门id
	 * @param deptName
	 *            部门名称
	 * @param deptSort
	 *            部门排序号
	 * @param upDeptId
	 *            上级部门id
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateDept.do")
	@ResponseBody
	public Map<String, Object> updateDept(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String deptId = request.getParameter("DEPT_ID");
		String deptName = request.getParameter("DEPT_NAME");
		String deptSort = request.getParameter("DEPT_SORT");
		String deptCode = request.getParameter("DEPT_CODE");
		String upDeptId = request.getParameter("UP_DEPT_ID");
		Dept dept = new Dept();
		dept.setDeptId(BaseUtil.strToLong(deptId));
		dept.setDeptName(deptName);
		dept.setDeptSort(BaseUtil.strToLong(deptSort));
		dept.setUpDeptId(BaseUtil.strToLong(upDeptId));
		dept.setDeptCode(deptCode);
		return deptService.updateDept(dept);
	}
}
