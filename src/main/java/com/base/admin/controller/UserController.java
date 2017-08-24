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

import com.base.admin.entity.PostUser;
import com.base.admin.entity.User;
import com.base.admin.service.IDeptService;
import com.base.admin.service.IDictionaryService;
import com.base.admin.service.IPostUserService;
import com.base.admin.service.IUserService;
import com.base.util.BaseUtil;

@Controller
@RequestMapping("/base/admin/user")
public class UserController {

	public static final Logger LOGGER = Logger
			.getLogger(DeptController.class);

	@Autowired
	private IDeptService deptService;

	@Autowired
	private IDictionaryService dictionaryService;

	@Autowired
	private IPostUserService postUserService;

	@Autowired
	private IUserService userService;

	/**
	 * 添加用户信息
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 * @return Map <String,Object> 返回类型
	 */
	@RequestMapping("/addNewUser.do")
	@ResponseBody
	public Map<String, Object> addNewUser(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String userName = request.getParameter("USER_NAME");
		String userCode = request.getParameter("USER_CODE");
		String userPhone = request
				.getParameter("USER_PHONE");
		String userSort = request.getParameter("USER_SORT");
		String userDeptId = request
				.getParameter("USER_DEPT_ID");
		String userLockFlag = request
				.getParameter("USER_LOCK_FLAG");
		String userUseFlag = request
				.getParameter("USER_USE_FLAG");
		User user = new User();
		user.setUserId(-1l);
		user.setUserPassWord(BaseUtil.initUserPassWord());// 每个用户初始密码是123456
		user.setUserName(userName);
		user.setUserCode(userCode);
		user.setUserPhone(userPhone);
		user.setUserSort(userSort);
		user.setUserDeptId(BaseUtil.strToLong(userDeptId));
		user.setUserLockFlag(userLockFlag);
		user.setUserUseFlag(userUseFlag);
		return userService.insertSelective(user);
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
	@RequestMapping("/addPostsToUser.do")
	@ResponseBody
	public Map<String, Object> addPostsToUser(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String userId = request.getParameter("USER_ID");
		String postIds = request.getParameter("POST_IDS");
		Map<String, Object> map = new HashMap<String, Object>();
		PostUser postUser = new PostUser();
		postUser.setUserId(BaseUtil.strToLong(userId));
		postUser.setIds(postIds);
		int bool = postUserService
				.insertPostsByUser(postUser);
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
	 * 删除用户
	 * 
	 * @param userIds
	 *            用户id串
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delUsers.do")
	@ResponseBody
	public Map<String, Object> delUsers(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String userIds = request.getParameter("USER_IDS");
		User user = new User();
		user.setIds(userIds);
		return userService.deleteByPrimaryKeys(user);
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
	@RequestMapping("/delPostsToUser.do")
	@ResponseBody
	public Map<String, Object> delPostsToUser(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String userId = request.getParameter("USER_ID");
		String postIds = request.getParameter("POST_IDS");
		Map<String, Object> map = new HashMap<String, Object>();
		PostUser postUser = new PostUser();
		postUser.setUserId(BaseUtil.strToLong(userId));
		postUser.setIds(postIds);
		int bool = postUserService
				.deletePostsByUser(postUser);
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
	 * @Description 初始化用户密码
	 * @Author RayLee
	 * @Version 1.0
	 * @date 2016年12月2日
	 * @param userIds
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/initUsersPassWord.do")
	@ResponseBody
	public Map<String, Object> initUsersPassWord(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String userIds = request.getParameter("USER_IDS");
		User user = new User();
		user.setIds(userIds);
		user.setUserPassWord(BaseUtil.initUserPassWord());
		return userService
				.updateByPrimaryKeysSelective(user);
	}

	/**
	 * 跳转到选择岗位管理操作页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/openChoosePostUI.do", method = RequestMethod.GET)
	public ModelAndView openChoosePostUI(
			HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView(
				"/base/admin/user/choosePostUI");
	}

	/**
	 * 跳转到用户管理操作页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/openEditUI.do", method = RequestMethod.GET)
	public ModelAndView openEditUI(
			HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView("/base/admin/user/editUI");
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
		response.getWriter().print(deptService
				.selectDeptsForTree().toLowerCase());
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
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String keyWord = request.getParameter("KEY_WORD");
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String userId = request.getParameter("USER_ID");
		PostUser postUser = new PostUser();
		postUser.setCurrPage(Integer.parseInt(page));
		postUser.setPageSize(Integer.parseInt(rows));
		postUser.setUserId(BaseUtil.strToLong(userId));
		postUser.setKeyWord(keyWord);
		return postUserService
				.querySelectedPostsForPage(postUser);
	}

	/**
	 * 分页查询未被选择人员列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryUnSelectedPostsForPage.do")
	@ResponseBody
	public Map<String, Object> queryUnSelectedPostsForPage(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String keyWord = request.getParameter("KEY_WORD");
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String userId = request.getParameter("USER_ID");
		PostUser postUser = new PostUser();
		postUser.setCurrPage(Integer.parseInt(page));
		postUser.setPageSize(Integer.parseInt(rows));
		postUser.setUserId(BaseUtil.strToLong(userId));
		postUser.setKeyWord(keyWord);
		return postUserService
				.queryUnSelectedPostsForPage(postUser);
	}

	/**
	 * 查询下拉列表
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/queryUserLockFlagDropList.do")
	@ResponseBody
	public void queryUserLockFlagDropList(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.getWriter().print(dictionaryService
				.getDictionarysByDicCode("YESORNO"));
		response.getWriter().flush();
		response.getWriter().close();
	}

	/**
	 * 分页查询用户列表
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
	@RequestMapping("/queryUsersPage.do")
	@ResponseBody
	public Map<String, Object> queryUsersPage(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String userDeptId = request
				.getParameter("USER_DEPT_ID");
		String keyWord = request.getParameter("keyWord");
		User user = new User();
		user.setCurrPage(BaseUtil.strToInt(page));
		user.setPageSize(BaseUtil.strToInt(rows));
		user.setUserDeptId(BaseUtil.strToLong(userDeptId));
		user.setKeyWord(keyWord);
		return userService.queryUsersForPage(user);
	}

	/**
	 * 查询下拉列表
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/queryUserUseFlagDropList.do")
	@ResponseBody
	public void queryUserUseFlagDropList(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.getWriter().print(dictionaryService
				.getDictionarysByDicCode("YESORNO"));
		response.getWriter().flush();
		response.getWriter().close();
	}

	/**
	 * 跳转到用户管理首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/index.do", method = RequestMethod.GET)
	public ModelAndView toIndex() {
		return new ModelAndView("/base/admin/user/index");
	}

	/**
	 * @Description 更新用户信息
	 * @Author RayLee
	 * @Version 1.0
	 * @date 2016年12月2日
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateUser.do")
	@ResponseBody
	public Map<String, Object> updateUser(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String userId = request.getParameter("USER_ID");
		String userName = request.getParameter("USER_NAME");
		String userCode = request.getParameter("USER_CODE");
		String userPhone = request
				.getParameter("USER_PHONE");
		String userSort = request.getParameter("USER_SORT");
		String userDeptId = request
				.getParameter("USER_DEPT_ID");
		String userLockFlag = request
				.getParameter("USER_LOCK_FLAG");
		String userUseFlag = request
				.getParameter("USER_USE_FLAG");
		User user = new User();
		user.setUserId(BaseUtil.strToLong(userId));
		user.setUserName(userName);
		user.setUserCode(userCode);
		user.setUserPhone(userPhone);
		user.setUserSort(userSort);
		user.setUserDeptId(BaseUtil.strToLong(userDeptId));
		user.setUserLockFlag(userLockFlag);
		user.setUserUseFlag(userUseFlag);
		return userService
				.updateByPrimaryKeysSelective(user);
	}

}
