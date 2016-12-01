package com.base.admin.service;

import java.util.List;
import java.util.Map;

import com.base.admin.entity.Dept;

public interface IDeptService {
	int deleteByPrimaryKeys(String[] deptIds);

	int insertSelective(Dept dept);

	int updateByPrimaryKeySelective(Dept dept);

	List<Dept> selectDeptsForList(Dept dept);

	String selectDeptsForTree();

	List<Map<String, Object>> selectDeptsForPage(Dept dept);

	int selectCountOfDeptsForPage(Dept dept);

	int updataInnerData();
}
