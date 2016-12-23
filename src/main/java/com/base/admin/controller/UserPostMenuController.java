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

import com.base.admin.entity.PostMenu;
import com.base.admin.entity.PostUser;
import com.base.admin.entity.User;
import com.base.admin.service.IDeptService;
import com.base.admin.service.IPostMenuService;
import com.base.admin.service.IPostUserService;
import com.base.admin.service.IUserService;
import com.base.util.BaseUtil;

@Controller
@RequestMapping("/base/admin/user_post_menu")
public class UserPostMenuController {

	public static final Logger LOGGER = Logger
			.getLogger(DeptController.class);

	@Autowired
	private IDeptService deptService;

	@Autowired
	private IPostMenuService postMenuService;

	@Autowired
	private IPostUserService postUserService;

	@Autowired
	private IUserService userService;

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
	 * 分页查询已经被选择菜单权限列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryMenusForTree.do")
	@ResponseBody
	public void queryMenusForTree(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String postId = request.getParameter("POST_ID");
		PostMenu postMenu = new PostMenu();
		postMenu.setPostId(BaseUtil.strToLong(postId));
		response.getWriter().print(
				postMenuService.querySelectedMenusForTree(postMenu));
		response.getWriter().flush();
		response.getWriter().close();
	}

	/**
	 * 分页查询已经被选择人员列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/querySelectedPostsForPage.do")
	@ResponseBody
	public Map<String, Object> querySelectedPostsForPage(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String keyWord = request.getParameter("KEY_WORD");
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String userId = request.getParameter("USER_ID");
		PostUser postUser = new PostUser();
		postUser.setCurrPage(Integer.parseInt(page));
		postUser.setPageSize(Integer.parseInt(rows));
		postUser.setUserId(BaseUtil.strToLong(userId));
		postUser.setKeyWord(keyWord);
		return postUserService.querySelectedPostsForPage(postUser);
	}

	/**
	 * 分页查询用户列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryUsersPage.do")
	@ResponseBody
	public Map<String, Object> queryUsersPage(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String userDeptId = request.getParameter("USER_DEPT_ID");
		String keyWord = request.getParameter("keyWord");
		User user = new User();
		user.setCurrPage(BaseUtil.strToInt(page));
		user.setPageSize(BaseUtil.strToInt(rows));
		user.setUserDeptId(BaseUtil.strToLong(userDeptId));
		user.setKeyWord(keyWord);
		return userService.queryUsersForPage(user);
	}

	/**
	 * 跳转到用户管理首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/index.do", method = RequestMethod.GET)
	public ModelAndView toIndex() {
		return new ModelAndView("/base/admin/user_post_menu");
	}

}
