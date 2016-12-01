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

import com.base.admin.entity.User;
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
		String userDeptId = request.getParameter("userDeptId");
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
	 * 
	 * 添加用户信息 @param userName 用户名称 @param userCode 用户编号 @param userPhone
	 * 手机号码 @param userSort 排序号 @param userDeptId 所属部门 @param request @param
	 * response @return @throws Exception 设定文件 @return Map
	 * <String,Object> 返回类型 @throws
	 */
	@RequestMapping("/addNewUser.do")
	@ResponseBody
	public Map<String, Object> addNewUser(
			@RequestParam("USER_NAME") String userName,
			@RequestParam("USER_CODE") String userCode,
			@RequestParam("USER_PHONE") String userPhone,
			@RequestParam("USER_SORT") String userSort,
			@RequestParam("USER_DEPT_ID") long userDeptId,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		User user = new User();
		user.setUserId(-1l);
		user.setUserName(userName);
		user.setUserCode(userCode);
		user.setUserPhone(userPhone);
		user.setUserSort(userSort);
		user.setUserDeptId(userDeptId);
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
	 * 更新用户信息 @param userName @param userCode @param userPhone @param
	 * userSort @param userDeptId @param request @param response @return @throws
	 * Exception 设定文件 @return Map<String,Object> 返回类型 @throws
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
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		User user = new User();
		user.setUserId(userId);
		user.setUserName(userName);
		user.setUserCode(userCode);
		user.setUserPhone(userPhone);
		user.setUserSort(userSort);
		user.setUserDeptId(userDeptId);
		int bool = userService.updateByPrimaryKeySelective(user);
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
	public Map<String, Object> delDepts(
			@RequestParam(value = "USER_IDS") String userIds,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {
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
