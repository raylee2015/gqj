package com.base.admin.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.admin.dao.MenuMapper;
import com.base.admin.entity.Menu;
import com.base.admin.entity.User;
import com.base.admin.service.IMenuService;
import com.base.util.BaseUtil;

import net.sf.json.JSONArray;

@Service
public class MenuServiceImpl implements IMenuService {

	@Autowired
	private MenuMapper menuMapper;

	@Override
	public int deleteByPrimaryKeys(Menu menu) {
		return menuMapper.deleteByPrimaryKeys(menu);
	}

	@Override
	public int insertSelective(Menu menu) {
		return menuMapper.insertSelective(menu);
	}

	@Override
	public Map<String, Object> queryMenusForPage(Menu menu) {
		List<Map<String, Object>> menus = menuMapper
				.queryMenusForPage(menu);
		int count = menuMapper.queryCountOfMenusForPage(menu);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", menus);
		map.put("total", count);
		return map;
	}

	@Override
	public List<Map<String, Object>> queryMenusForList(User user) {
		return menuMapper.queryMenusForList(user);
	}

	@Override
	public String queryMenusForTree(Menu menu) {
		List<Map<Object, Object>> menus = menuMapper
				.queryMenusForTree(menu);
		JSONArray menuArr = JSONArray.fromObject(menus);
		String tree = BaseUtil
				.list2Tree(menuArr, -1, "id", "up_menu_id", "children")
				.toString().toLowerCase();
		return tree;
	}

	@Override
	public int updateByPrimaryKeySelective(Menu menu) {
		return menuMapper.updateByPrimaryKeySelective(menu);
	}

	@Override
	public int updataInnerData() {
		return menuMapper.updataInnerData();
	}

}
