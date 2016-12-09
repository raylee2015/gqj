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
import com.base.admin.entity.PostMenu;
import com.base.admin.service.IPostMenuService;
import com.base.util.BaseUtil;

@Controller
@RequestMapping("/base/admin/post_menu")
public class PostMenuController {

	public static final Logger LOGGER = Logger
			.getLogger(PostMenuController.class);

	/**
	 * 跳转到配对岗位菜单权限首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/index.do", method = RequestMethod.GET)
	public ModelAndView toIndex() {
		return new ModelAndView("/base/admin/post_menu");
	}

	@Autowired
	private IPostMenuService postMenuService;

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
		return postMenuService.selectPostsForPage(post);
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
	public void queryUnSelectedMenusForTree(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.getWriter()
				.print(postMenuService.queryUnSelectedMenusForTree());
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
	public void querySelectedMenusForTree(HttpServletRequest request,
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
		response.getWriter()
				.print(postMenuService.selectDeptsForTree());
		response.getWriter().flush();
		response.getWriter().close();
	}

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
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String menuIds = request.getParameter("MENU_IDS");
		String postId = request.getParameter("POST_ID");
		Map<String, Object> map = new HashMap<String, Object>();
		PostMenu postMenu = new PostMenu();
		postMenu.setPostId(BaseUtil.strToLong(postId));
		postMenu.setIds(menuIds);
		int bool = postMenuService.insert(postMenu);
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
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String menuIds = request.getParameter("MENU_IDS");
		String postId = request.getParameter("POST_ID");
		Map<String, Object> map = new HashMap<String, Object>();
		PostMenu postMenu = new PostMenu();
		postMenu.setPostId(BaseUtil.strToLong(postId));
		postMenu.setIds(menuIds);
		int bool = postMenuService.deleteByPrimaryKeys(postMenu);
		if (bool == 0) {
			map.put("success", false);
			map.put("msg", "保存出错，请联系管理员");
		} else {
			map.put("success", true);
			map.put("msg", "保存成功");
		}
		return map;
	}

}
