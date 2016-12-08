package com.base.admin.controller;

import java.util.HashMap;
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

	/**
	 * 跳转到岗位管理首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/index.do", method = RequestMethod.GET)
	public ModelAndView toIndex() {
		return new ModelAndView("/base/admin/post");
	}

	@Autowired
	private IPostService postService;

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
	public Map<String, Object> queryPostPage(HttpServletRequest request,
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

	@Autowired
	private IDeptService deptService;

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
		response.getWriter().print(deptService.selectDeptsForTree());
		response.getWriter().flush();
		response.getWriter().close();
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
	public Map<String, Object> addNewPost(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String postName = request.getParameter("POST_NAME");
		String postDesp = request.getParameter("POST_DESP");
		String deptId = request.getParameter("DEPT_ID");
		String postSort = request.getParameter("POST_SORT");
		Map<String, Object> map = new HashMap<String, Object>();
		Post post = new Post();
		post.setPostId(-1l);
		post.setPostName(postName);
		post.setPostSort(BaseUtil.strToLong(postSort));
		post.setDeptId(BaseUtil.strToLong(deptId));
		post.setPostDesp(postDesp);
		int bool = postService.insertSelective(post);
		if (bool == 0) {
			map.put("success", false);
			map.put("msg", "保存出错，请联系管理员");
		} else {
			map.put("success", true);
			map.put("msg", "保存成功");
		}
		return map;
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
	public Map<String, Object> updatePost(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String postId = request.getParameter("POST_ID");
		String postName = request.getParameter("POST_NAME");
		String postDesp = request.getParameter("POST_DESP");
		String deptId = request.getParameter("DEPT_ID");
		String postSort = request.getParameter("POST_SORT");
		Map<String, Object> map = new HashMap<String, Object>();
		Post post = new Post();
		post.setPostId(BaseUtil.strToLong(postId));
		post.setPostName(postName);
		post.setPostSort(BaseUtil.strToLong(postSort));
		post.setDeptId(BaseUtil.strToLong(deptId));
		post.setPostDesp(postDesp);
		int bool = postService.updateByPrimaryKeySelective(post);
		if (bool == 0) {
			map.put("success", false);
			map.put("msg", "保存出错，请联系管理员");
		} else {
			map.put("success", true);
			map.put("msg", "保存成功");
		}
		return map;
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
	public Map<String, Object> delPosts(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String postIds = request.getParameter("POST_IDS");
		Map<String, Object> map = new HashMap<>();
		Post post = new Post();
		post.setIds(postIds);
		int bool = postService.deleteByPrimaryKeys(post);
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
