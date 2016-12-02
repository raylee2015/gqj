package com.base.admin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.admin.dao.MenuMapper;
import com.base.admin.entity.Menu;
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
	public int selectCountOfMenusForPage(Menu menu) {
		return menuMapper.selectCountOfMenusForPage(menu);
	}

	@Override
	public List<Map<String, Object>> selectMenusForPage(Menu menu) {
		return menuMapper.selectMenusForPage(menu);
	}

	@Override
	public String selectMenusForTree() {
		List<Map<Object, Object>> menus = menuMapper
				.selectMenusForTree();
		JSONArray menuArr = JSONArray.fromObject(menus);
		String tree = BaseUtil
				.list2Tree(menuArr, -1, "id", "up_menu_id", "children")
				.toString();
		return tree;
	}

	@Override
	public int updateByPrimaryKeySelective(Menu menu) {
		return updateByPrimaryKeySelective(menu);
	}

}
