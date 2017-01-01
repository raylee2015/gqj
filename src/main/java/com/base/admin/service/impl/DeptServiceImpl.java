package com.base.admin.service.impl;

import java.util.HashMap;
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
	public Map<String, Object> deleteDepts(Dept dept) {
		Map<String, Object> map = new HashMap<>();
		int bool = deptMapper.deleteByPrimaryKeys(dept);
		bool = deptMapper.updataInnerData();
		if (bool == 0) {
			map.put("success", false);
			map.put("msg", "删除失败，请联系管理员");
		} else {
			map.put("success", true);
			map.put("msg", "删除成功");
		}
		return map;
	}

	@Override
	public Map<String, Object> addNewDept(Dept dept) {
		Map<String, Object> map = new HashMap<String, Object>();
		int bool = deptMapper.insertSelective(dept);
		bool = deptMapper.updataInnerData();
		if (bool == 0) {
			map.put("success", false);
			map.put("msg", "保存出错，请联系管理员");
		} else {
			map.put("success", true);
			map.put("msg", "保存成功");
		}
		return map;
	}

	@Override
	public List<Dept> selectDeptsForList(Dept dept) {
		return deptMapper.selectDeptsForList(dept);
	}

	@Override
	public Map<String, Object> selectDeptsForPage(
			Dept dept) {
		List<Map<String, Object>> depts = deptMapper
				.selectDeptsForPage(dept);
		int count = deptMapper
				.selectCountOfDeptsForPage(dept);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", depts);
		map.put("total", count);
		return map;
	}

	@Override
	public String selectDeptsForTree() {
		List<Map<Object, Object>> depts = deptMapper
				.selectDeptsForTree();
		JSONArray deptArr = JSONArray.fromObject(depts);
		// 返回是必须全是小写
		String tree = BaseUtil
				.list2Tree(deptArr, -1, "id", "up_dept_id",
						"children")
				.toString().toLowerCase();
		return tree;
	}

	@Override
	public Map<String, Object> updateDept(Dept dept) {
		Map<String, Object> map = new HashMap<String, Object>();
		int bool = deptMapper
				.updateByPrimaryKeySelective(dept);
		bool = deptMapper.updataInnerData();
		if (bool == 0) {
			map.put("success", false);
			map.put("msg", "保存出错，请联系管理员");
		} else {
			map.put("success", true);
			map.put("msg", "保存成功");
		}
		return map;
	}

}
