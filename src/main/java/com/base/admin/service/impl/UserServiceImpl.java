package com.base.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.admin.service.IUserService;
import com.sample.dao.ISampleDao;
import com.sample.entity.Sample;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private ISampleDao dao;

	@Override
	public List<Sample> queryUsers() {
		return dao.selectSamplesForList();
	}
}
