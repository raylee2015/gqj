package com.base.admin.dao;

import java.util.List;
import java.util.Map;

import com.base.admin.entity.Dept;

public interface DeptMapper {
	int deleteByPrimaryKey(Long deptId);

	int insert(Dept record);

	int insertSelective(Dept record);

	Dept selectByPrimaryKey(Long deptId);

	int updateByPrimaryKeySelective(Dept record);

	int updateByPrimaryKey(Dept record);

	List<Dept> selectDeptsForList(Dept dept);

	List<Map<Object, Object>> selectDeptsForTree();

	List<Dept> selectDeptsForPage(Dept dept);

	int selectCountOfDeptsForPage(Dept dept);
}