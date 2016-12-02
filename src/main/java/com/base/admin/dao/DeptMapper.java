package com.base.admin.dao;

import java.util.List;
import java.util.Map;

import com.base.admin.entity.Dept;

public interface DeptMapper {
	int deleteByPrimaryKeys(Dept dept);

	int insertSelective(Dept dept);

	int updateByPrimaryKeySelective(Dept dept);

	List<Dept> selectDeptsForList(Dept dept);

	List<Map<Object, Object>> selectDeptsForTree();

	List<Map<String, Object>> selectDeptsForPage(Dept dept);

	int selectCountOfDeptsForPage(Dept dept);

	int updataInnerData();
}