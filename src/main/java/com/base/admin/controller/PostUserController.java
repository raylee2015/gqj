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

import com.base.admin.entity.Post;
import com.base.admin.entity.PostUser;
import com.base.admin.service.IPostUserService;
import com.base.util.BaseUtil;

@Controller
@RequestMapping("/base/admin/post_user")
public class PostUserController {

	public static final Logger LOGGER = Logger
			.getLogger(PostUserController.class);

	@Autowired
	private IPostUserService postUserService;

	/**
	 * @Description 为岗位配置人员
	 * @Author RayLee
	 * @Version 1.0
	 * @date 2016年12月9日
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addUsersToPost.do")
	@ResponseBody
	public Map<String, Object> addUsersToPost(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String userIds = request.getParameter("USER_IDS");
		String postId = request.getParameter("POST_ID");
		PostUser postUser = new PostUser();
		postUser.setPostId(BaseUtil.strToLong(postId));
		postUser.setIds(userIds);
		return postUserService.insertUsersByPost(postUser);
	}

	/**
	 * @Description 删除岗位的人员
	 * @Author RayLee
	 * @Version 1.0
	 * @date 2016年12月9日
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delUsersToPost.do")
	@ResponseBody
	public Map<String, Object> delUsersToPost(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String userIds = request.getParameter("USER_IDS");
		String postId = request.getParameter("POST_ID");
		PostUser postUser = new PostUser();
		postUser.setPostId(BaseUtil.strToLong(postId));
		postUser.setIds(userIds);
		return postUserService.deleteUsersByPost(postUser);
	}

	/**
	 * 分页查询岗位列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryPostPage.do")
	@ResponseBody
	public Map<String, Object> queryPostPage(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String deptIds = request.getParameter("DEPT_IDS");
		String keyWord = request.getParameter("keyWord");
		Post post = new Post();
		post.setCurrPage(Integer.parseInt(page));
		post.setPageSize(Integer.parseInt(rows));
		post.setKeyWord(keyWord);
		post.setIds(deptIds);
		return postUserService.selectPostsForPage(post);
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
	public void queryPostTree(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.getWriter().print(
				postUserService.selectDeptsForTree());
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
	@RequestMapping("/querySelectedUsersForPage.do")
	@ResponseBody
	public Map<String, Object> querySelectedUsersForPage(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String keyWord = request.getParameter("KEY_WORD");
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String postId = request.getParameter("POST_ID");
		PostUser postUser = new PostUser();
		postUser.setCurrPage(Integer.parseInt(page));
		postUser.setPageSize(Integer.parseInt(rows));
		postUser.setPostId(BaseUtil.strToLong(postId));
		postUser.setKeyWord(keyWord);
		return postUserService
				.querySelectedUsersForPage(postUser);
	}

	/**
	 * 分页查询未被选择人员列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryUnSelectedUsersForPage.do")
	@ResponseBody
	public Map<String, Object> queryUnSelectedUsersForPage(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String keyWord = request.getParameter("KEY_WORD");
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String postId = request.getParameter("POST_ID");
		PostUser postUser = new PostUser();
		postUser.setCurrPage(Integer.parseInt(page));
		postUser.setPageSize(Integer.parseInt(rows));
		postUser.setPostId(BaseUtil.strToLong(postId));
		postUser.setKeyWord(keyWord);
		return postUserService
				.queryUnSelectedUsersForPage(postUser);
	}

	/**
	 * 跳转到配对岗位人员首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/index.do", method = RequestMethod.GET)
	public ModelAndView toIndex() {
		return new ModelAndView("/base/admin/post_user");
	}

}
