package com.base.admin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.admin.dao.DeptMapper;
import com.base.admin.entity.Dept;
import com.base.admin.service.IDeptService;
import com.base.util.BaseUtil;

import net.sf.json.JSONArray;

@Service
public class DeptServiceImpl implements IDeptService {

	@Autowired
	private DeptMapper deptMapper;

	/*
	 * (非 Javadoc) <p>Title: deleteByPrimaryKeys</p> <p>Description: </p>
	 * 
	 * @param deptIds
	 * 
	 * @return
	 * 
	 * @see
	 * com.base.admin.service.IDeptService#deleteByPrimaryKeys(java.lang.String)
	 */
	@Override
	public int deleteByPrimaryKeys(Dept dept) {
		return deptMapper.deleteByPrimaryKeys(dept);
	}

	@Override
	public int insertSelective(Dept dept) {
		return deptMapper.insertSelective(dept);
	}

	@Override
	public int selectCountOfDeptsForPage(Dept dept) {
		return deptMapper.selectCountOfDeptsForPage(dept);
	}

	@Override
	public List<Dept> selectDeptsForList(Dept dept) {
		return deptMapper.selectDeptsForList(dept);
	}

	@Override
	public List<Map<String, Object>> selectDeptsForPage(Dept dept) {
		return deptMapper.selectDeptsForPage(dept);
	}

	@Override
	public String selectDeptsForTree() {
		List<Map<Object, Object>> depts = deptMapper
				.selectDeptsForTree();
		JSONArray deptArr = JSONArray.fromObject(depts);
		String tree = BaseUtil
				.list2Tree(deptArr, -1, "id", "up_dept_id", "children")
				.toString();
		return tree;
	}

	@Override
	public int updataInnerData() {
		return deptMapper.updataInnerData();
	}

	@Override
	public int updateByPrimaryKeySelective(Dept dept) {
		return deptMapper.updateByPrimaryKeySelective(dept);
	}

}
