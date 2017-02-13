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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.base.admin.entity.User;
import com.base.admin.service.IRoleUserService;
import com.base.admin.service.IUserService;
import com.base.util.BaseUtil;

/**
 * 改用岗位，不用角色
 * 
 * @author Administrator
 *
 */
@Deprecated
@Controller
@RequestMapping("/base/admin/role_user")
public class RoleUserController {

	public static final Logger LOGGER = Logger
			.getLogger(DeptController.class);

	/**
	 * 跳转到用户管理首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/index.do", method = RequestMethod.GET)
	public ModelAndView toIndex() {
		return new ModelAndView("/base/admin/role_user");
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
	public Map<String, Object> queryUserPage(
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

	@Autowired
	private IRoleUserService roleUserService;

	/**
	 * 查询角色树
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/queryRoles.do")
	@ResponseBody
	public void queryRoles(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.getWriter()
				.print(roleUserService.queryRoles());
		response.getWriter().flush();
		response.getWriter().close();
	}

	/**
	 * 
	 * 添加用户信息
	 * 
	 * @param userName
	 *            用户名称
	 * @param userCode
	 *            用户编号
	 * @param userPhone
	 *            手机号码
	 * @param userSort
	 *            排序号
	 * @param userDeptId
	 *            所属部门
	 * @param request
	 * @param response
	 * @throws Exception
	 * @return Map <String,Object> 返回类型
	 */
	@RequestMapping("/addNewUser.do")
	@ResponseBody
	public Map<String, Object> addNewUser(
			@RequestParam("USER_NAME") String userName,
			@RequestParam("USER_CODE") String userCode,
			@RequestParam("USER_PHONE") String userPhone,
			@RequestParam("USER_SORT") String userSort,
			@RequestParam("USER_DEPT_ID") long userDeptId,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		User user = new User();
		user.setUserId(-1l);
		user.setUserPassWord(BaseUtil.initUserPassWord());// 每个用户初始密码是123456
		user.setUserName(userName);
		user.setUserCode(userCode);
		user.setUserPhone(userPhone);
		user.setUserSort(userSort);
		user.setUserDeptId(userDeptId);
		// int bool = userService.insertSelective(user);
		// if (bool == 0) {
		// map.put("success", false);
		// map.put("msg", "保存出错，请联系管理员");
		// } else {
		// map.put("success", true);
		// map.put("msg", "保存成功");
		// }
		return map;
	}

	/**
	 * @Description 更新用户信息
	 * @Author RayLee
	 * @Version 1.0
	 * @date 2016年12月2日
	 * @param userId
	 *            用户id
	 * @param userName
	 *            用户名称
	 * @param userCode
	 *            用户编号
	 * @param userPhone
	 *            手机号码
	 * @param userSort
	 *            排序号
	 * @param userDeptId
	 *            用户所属部门
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateUser.do")
	@ResponseBody
	public Map<String, Object> updateDept(
			@RequestParam("USER_ID") long userId,
			@RequestParam("USER_NAME") String userName,
			@RequestParam("USER_CODE") String userCode,
			@RequestParam("USER_PHONE") String userPhone,
			@RequestParam("USER_SORT") String userSort,
			@RequestParam("USER_DEPT_ID") long userDeptId,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		User user = new User();
		user.setUserId(userId);
		user.setUserName(userName);
		user.setUserCode(userCode);
		user.setUserPhone(userPhone);
		user.setUserSort(userSort);
		user.setUserDeptId(userDeptId);
		// int bool = userService.updateByPrimaryKeysSelective(user);
		// if (bool == 0) {
		// map.put("success", false);
		// map.put("msg", "保存出错，请联系管理员");
		// } else {
		// map.put("success", true);
		// map.put("msg", "保存成功");
		// }
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
			@RequestParam(value = "USER_IDS") String userIds,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		User user = new User();
		user.setIds(userIds);
		user.setUserPassWord(BaseUtil.initUserPassWord());
		// int bool = userService.updateByPrimaryKeysSelective(user);
		// if (bool == 0) {
		// map.put("success", false);
		// map.put("msg", "初始化出错，请联系管理员");
		// } else {
		// map.put("success", true);
		// map.put("msg", "初始化成功");
		// }
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
	public Map<String, Object> delDepts(
			@RequestParam(value = "USER_IDS") String userIds,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		// int bool = userService.deleteByPrimaryKeys(userIds.split(","));
		// if (bool == 0) {
		// map.put("success", false);
		// map.put("msg", "删除失败，请联系管理员");
		// } else {
		// map.put("success", true);
		// map.put("msg", "删除成功");
		// }
		return map;
	}

}
