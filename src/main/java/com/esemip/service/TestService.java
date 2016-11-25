package com.esemip.service;

import java.util.List;

import com.esemip.entity.User;

public interface TestService {
    public String testQuery() throws Exception;

	List<User> queryUsers() throws Exception;
}
