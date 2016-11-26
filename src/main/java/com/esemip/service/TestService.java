package com.esemip.service;

import java.util.List;

import com.esemip.entity.User;

public interface TestService {
	
	List<User> queryUsers() throws Exception;

	/** 
	* @Title: testQuery 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @return
	* @param @throws Exception    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	String testQuery() throws Exception;
}
