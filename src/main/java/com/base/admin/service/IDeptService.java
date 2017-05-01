package com.base.admin.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.base.admin.entity.Dept;

public interface IDeptService {
	Map<String, Object> deleteDepts(Dept dept);

	Map<String, Object> addNewDept(Dept dept);

	Map<String, Object> updateDept(Dept dept);

	List<Dept> selectDeptsForList(Dept dept);

	String selectDeptsForTree();

	Map<String, Object> selectDeptsForPage(Dept dept) throws SQLException;
}
