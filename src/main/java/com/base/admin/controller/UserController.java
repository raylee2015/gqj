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
import org.springframework.web.servlet.ModelAndView;

import com.base.admin.entity.User;
import com.base.admin.service.IDictionaryService;
import com.base.admin.service.IUserService;
import com.base.util.BaseUtil;

@Controller
@RequestMapping("/base/admin/user")
public class UserController {

	public static final Logger LOGGER = Logger
			.getLogger(DeptController.class);

	/**
	 * 跳转到用户管理首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/index.do", method = RequestMethod.GET)
	public ModelAndView toIndex() {
		return new ModelAndView("/base/admin/user");
	}

	@Autowired
	private IUserService userService;

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
	@RequestMapping("/queryUserPage.do")
	@ResponseBody
	public Map<String, Object> queryUserPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String userDeptId = request.getParameter("USER_DEPT_ID");
		String keyWord = request.getParameter("keyWord");
		User user = new User();
		user.setCurrPage(BaseUtil.strToInt(page));
		user.setPageSize(BaseUtil.strToInt(rows));
		user.setUserDeptId(BaseUtil.strToLong(userDeptId));
		user.setKeyWord(keyWord);
		List<Map<String, Object>> users = userService
				.selectUsersForPage(user);
		int count = userService.selectCountOfUsersForPage(user);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", users);
		map.put("total", count);
		return map;
	}

	@Autowired
	private IDictionaryService dictionaryService;

	/**
	 * 查询下拉列表
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/queryUserLockFlagDropList.do")
	@ResponseBody
	public void queryUserLockFlagDropList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.getWriter().print(
				dictionaryService.getDictionarysByDicCode("YESORNO"));
		response.getWriter().flush();
		response.getWriter().close();
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
	public void queryUserUseFlagDropList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.getWriter().print(
				dictionaryService.getDictionarysByDicCode("YESORNO"));
		response.getWriter().flush();
		response.getWriter().close();
	}

	@Autowired
	private DeptController deptController;

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
		deptController.queryDeptTree(request, response);
	}

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
	public Map<String, Object> addNewUser(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String userName = request.getParameter("USER_NAME");
		String userCode = request.getParameter("USER_CODE");
		String userPhone = request.getParameter("USER_PHONE");
		String userSort = request.getParameter("USER_SORT");
		String userDeptId = request.getParameter("USER_DEPT_ID");
		String userLockFlag = request.getParameter("USER_LOCK_FLAG");
		String userUseFlag = request.getParameter("USER_USE_FLAG");
		Map<String, Object> map = new HashMap<String, Object>();
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
		int bool = userService.insertSelective(user);
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
	public Map<String, Object> updateDept(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String userId = request.getParameter("USER_ID");
		String userName = request.getParameter("USER_NAME");
		String userCode = request.getParameter("USER_CODE");
		String userPhone = request.getParameter("USER_PHONE");
		String userSort = request.getParameter("USER_SORT");
		String userDeptId = request.getParameter("USER_DEPT_ID");
		String userLockFlag = request.getParameter("USER_LOCK_FLAG");
		String userUseFlag = request.getParameter("USER_USE_FLAG");
		Map<String, Object> map = new HashMap<String, Object>();
		User user = new User();
		user.setUserId(BaseUtil.strToLong(userId));
		user.setUserName(userName);
		user.setUserCode(userCode);
		user.setUserPhone(userPhone);
		user.setUserSort(userSort);
		user.setUserDeptId(BaseUtil.strToLong(userDeptId));
		user.setUserLockFlag(userLockFlag);
		user.setUserUseFlag(userUseFlag);
		int bool = userService.updateByPrimaryKeysSelective(user);
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
	@RequestMapping("/initUserPassWord.do")
	@ResponseBody
	public Map<String, Object> initUserPassWord(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String userIds = request.getParameter("USER_IDS");
		Map<String, Object> map = new HashMap<String, Object>();
		User user = new User();
		user.setIds(userIds);
		user.setUserPassWord(BaseUtil.initUserPassWord());
		int bool = userService.updateByPrimaryKeysSelective(user);
		if (bool == 0) {
			map.put("success", false);
			map.put("msg", "初始化出错，请联系管理员");
		} else {
			map.put("success", true);
			map.put("msg", "初始化成功");
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
	public Map<String, Object> delDepts(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String userIds = request.getParameter("USER_IDS");
		Map<String, Object> map = new HashMap<>();
		int bool = userService.deleteByPrimaryKeys(userIds.split(","));
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
