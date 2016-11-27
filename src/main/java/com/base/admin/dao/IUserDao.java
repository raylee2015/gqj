package com.base.admin.dao;

import java.util.List;

import com.sample.entity.Sample;

public interface IUserDao {
	int deleteByPrimaryKey(Long userId);

	int insert(Sample sample);

	int insertSelective(Sample sample);

	Sample selectByPrimaryKey(Long userId);

	int updateByPrimaryKeySelective(Sample sample);

	int updateByPrimaryKey(Sample sample);

	List<Sample> selectSamplesForList();
}
