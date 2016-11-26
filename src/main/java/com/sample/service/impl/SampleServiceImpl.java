package com.sample.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sample.dao.ISampleDao;
import com.sample.entity.Sample;
import com.sample.service.ISampleService;

@Service
public class SampleServiceImpl implements ISampleService {

	@Autowired
	private ISampleDao dao;

	@Override
	public List<Sample> queryUsers() {
		List<Sample> users = dao.querySamples();
		return users;
	}
}
