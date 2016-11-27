package com.base.admin.service;

import java.util.List;

import com.sample.entity.Sample;

public interface IUserService {
	List<Sample> queryUsers() throws Exception;
}
