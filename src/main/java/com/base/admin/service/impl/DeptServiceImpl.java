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

	@Override
	public int deleteByPrimaryKey(Long deptId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(Dept dept) {
		return deptMapper.insert(dept);
	}

	@Override
	public int insertSelective(Dept dept) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Dept selectByPrimaryKey(Long deptId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateByPrimaryKeySelective(Dept dept) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKey(Dept dept) {
		// TODO Auto-generated method stub
		return 0;
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
	public int selectCountOfDeptsForPage(Dept dept) {
		return deptMapper.selectCountOfDeptsForPage(dept);
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

}
