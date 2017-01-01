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
import com.base.admin.service.IDeptService;
import com.base.admin.service.IPostService;
import com.base.util.BaseUtil;

@Controller
@RequestMapping("/base/admin/post")
public class PostController {
	public static final Logger LOGGER = Logger
			.getLogger(PostController.class);

	@Autowired
	private IDeptService deptService;

	@Autowired
	private PostMenuController postMenuController;

	@Autowired
	private IPostService postService;

	@Autowired
	private PostUserController postUserController;

	/**
	 * @Description 为岗位配置菜单权限
	 * @Author RayLee
	 * @Version 1.0
	 * @date 2016年12月9日
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addMenusToPost.do")
	@ResponseBody
	public Map<String, Object> addMenusToPost(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return postMenuController.addMenusToPost(request,
				response);
	}

	/**
	 * 添加岗位信息
	 * 
	 * @param postName
	 *            岗位名称
	 * @param postSort
	 *            岗位排序号
	 * @param upPostId
	 *            上级岗位id
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addNewPost.do")
	@ResponseBody
	public Map<String, Object> addNewPost(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String postName = request.getParameter("POST_NAME");
		String postDesp = request.getParameter("POST_DESP");
		String deptId = request.getParameter("DEPT_ID");
		String postSort = request.getParameter("POST_SORT");
		Post post = new Post();
		post.setPostId(-1l);
		post.setPostName(postName);
		post.setPostSort(BaseUtil.strToLong(postSort));
		post.setDeptId(BaseUtil.strToLong(deptId));
		post.setPostDesp(postDesp);
		return postService.addNewPost(post);
	}

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
		return postUserController.addUsersToPost(request,
				response);
	}

	/**
	 * @Description 删除岗位的菜单权限
	 * @Author RayLee
	 * @Version 1.0
	 * @date 2016年12月9日
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delMenusToPost.do")
	@ResponseBody
	public Map<String, Object> delMenusToPost(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return postMenuController.delMenusToPost(request,
				response);
	}

	/**
	 * 删除岗位
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delPosts.do")
	@ResponseBody
	public Map<String, Object> delPosts(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String postIds = request.getParameter("POST_IDS");
		Post post = new Post();
		post.setIds(postIds);
		return postService.deletePosts(post);
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
		return postUserController.delUsersToPost(request,
				response);
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
		return postService.selectPostsForPage(post);
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
				.print(deptService.selectDeptsForTree());
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
	@RequestMapping("/querySelectedMenusForTree.do")
	@ResponseBody
	public void querySelectedMenusForTree(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		postMenuController.querySelectedMenusForTree(
				request, response);
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
		return postUserController.querySelectedUsersForPage(
				request, response);
	}

	/**
	 * 分页查询未被选择菜单权限列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryUnSelectedMenusForTree.do")
	@ResponseBody
	public void queryUnSelectedMenusForTree(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		postMenuController.queryUnSelectedMenusForTree(
				request, response);
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
		return postUserController
				.queryUnSelectedUsersForPage(request,
						response);
	}

	/**
	 * 跳转到岗位管理首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/index.do", method = RequestMethod.GET)
	public ModelAndView toIndex() {
		return new ModelAndView("/base/admin/post/index");
	}

	/**
	 * 跳转到岗位管理操作页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/openEditUI.do", method = RequestMethod.GET)
	public ModelAndView openEditUI(
			HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView("/base/admin/post/editUI");
	}

	/**
	 * 跳转到匹配岗位人员操作页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/openChooseUserUI.do", method = RequestMethod.GET)
	public ModelAndView openChooseUserUI(
			HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView(
				"/base/admin/post/chooseUserUI");
	}

	/**
	 * 跳转到匹配岗位菜单操作页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/openChooseMenuUI.do", method = RequestMethod.GET)
	public ModelAndView openChooseMenuUI(
			HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView(
				"/base/admin/post/chooseMenuUI");
	}

	/**
	 * 更新岗位信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updatePost.do")
	@ResponseBody
	public Map<String, Object> updatePost(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String postId = request.getParameter("POST_ID");
		String postName = request.getParameter("POST_NAME");
		String postDesp = request.getParameter("POST_DESP");
		String deptId = request.getParameter("DEPT_ID");
		String postSort = request.getParameter("POST_SORT");
		Post post = new Post();
		post.setPostId(BaseUtil.strToLong(postId));
		post.setPostName(postName);
		post.setPostSort(BaseUtil.strToLong(postSort));
		post.setDeptId(BaseUtil.strToLong(deptId));
		post.setPostDesp(postDesp);
		return postService
				.updatePost(post);
	}

}
