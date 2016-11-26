package com.esemip.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esemip.dao.TestDao;
import com.esemip.entity.User;
import com.esemip.service.TestService;

@Service
public class TestServiceImpl implements TestService {

	@Autowired
	private TestDao dao;

	@Override
	public String testQuery() throws Exception {
		List<User> users = dao.testQuery();
		String res = "";
		if (users != null && users.size() > 0) {
			for (User user : users) {
				res += user.toString() + "|";
			}
		} else {
			res = "Not found.";
		}
		return res;
	}

	@Override
	public List<User> queryUsers() throws Exception {
		List<User> users = dao.testQuery();
		return users;
	}
}
