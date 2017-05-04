package com.base.admin.service.impl;

import java.sql.Clob;
import java.sql.SQLException;
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
	public Map<String, Object> deleteMenus(Menu menu) {
		Map<String, Object> map = new HashMap<String, Object>();
		int bool = menuMapper.deleteByPrimaryKeys(menu);
		bool = menuMapper.updataInnerData();
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
	public Map<String, Object> addNewMenu(Menu menu) {
		Map<String, Object> map = new HashMap<String, Object>();
		int bool = menuMapper.insertSelective(menu);
		bool = menuMapper.updataInnerData();
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
	public Map<String, Object> queryMenusForPage(Menu menu) {
		List<Map<String, Object>> menus = menuMapper.queryMenusForPage(menu);
		int count = menuMapper.queryCountOfMenusForPage(menu);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", menus);
		map.put("total", count);
		return map;
	}

	@Override
	public Map<String, Object> queryMenusForObject(Menu menu) {
		return menuMapper.queryMenusForObject(menu);
	}

	@Override
	public List<Map<String, Object>> queryMenusForList(User user) throws SQLException {
		List<Map<String, Object>> menus = menuMapper.queryMenusForList(user);
		for (Map<String, Object> item : menus) {
			if (item.get("VIEW_MENU_UP_INNER_CODE") instanceof Clob) {
				Clob clob = (Clob) item.get("VIEW_MENU_UP_INNER_CODE");
				String viewMenuUpInnerCode = "";
				if (clob != null) {
					viewMenuUpInnerCode = clob.getSubString((long) 1, (int) clob.length());
					item.put("VIEW_MENU_UP_INNER_CODE", viewMenuUpInnerCode);
				}
			}
			if (item.get("VIEW_MENU_INNER_CODE") instanceof Clob) {
				Clob clob = (Clob) item.get("VIEW_MENU_INNER_CODE");
				String viewMenuInnerCode = "";
				if (clob != null) {
					viewMenuInnerCode = clob.getSubString((long) 1, (int) clob.length());
					item.put("VIEW_MENU_INNER_CODE", viewMenuInnerCode);
				}
			}
		}
		return menus;
	}

	@Override
	public String queryMenusForTree(Menu menu) throws SQLException {
		List<Map<String, Object>> menus = menuMapper.queryMenusForTree(menu);
		for (Map<String, Object> item : menus) {
			if (item.get("VIEW_MENU_UP_INNER_CODE") instanceof Clob) {
				Clob clob = (Clob) item.get("VIEW_MENU_UP_INNER_CODE");
				String viewMenuUpInnerCode = "";
				if (clob != null) {
					viewMenuUpInnerCode = clob.getSubString((long) 1, (int) clob.length());
					item.put("VIEW_MENU_UP_INNER_CODE", viewMenuUpInnerCode);
				}
			}
			if (item.get("VIEW_MENU_INNER_CODE") instanceof Clob) {
				Clob clob = (Clob) item.get("VIEW_MENU_INNER_CODE");
				String viewMenuInnerCode = "";
				if (clob != null) {
					viewMenuInnerCode = clob.getSubString((long) 1, (int) clob.length());
					item.put("VIEW_MENU_INNER_CODE", viewMenuInnerCode);
				}
			}
		}
		JSONArray menuArr = JSONArray.fromObject(menus);
		String tree = BaseUtil.list2Tree(menuArr, -1, "id", "up_menu_id", "children").toString().toLowerCase();
		return tree;
	}

	@Override
	public Map<String, Object> updateMenu(Menu menu) {
		Map<String, Object> map = new HashMap<String, Object>();
		int bool = menuMapper.updateByPrimaryKeySelective(menu);
		bool = menuMapper.updataInnerData();
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
