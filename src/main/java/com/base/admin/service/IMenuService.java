package com.base.admin.service;

import java.util.List;
import java.util.Map;

import com.base.admin.entity.Menu;
import com.base.admin.entity.User;

public interface IMenuService {
	Map<String, Object> deleteMenus(Menu menu);

	Map<String, Object> addNewMenu(Menu menu);

	Map<String, Object> updateMenu(
			Menu menu);

	String queryMenusForTree(Menu menu);

	Map<String, Object> queryMenusForPage(Menu menu);

	List<Map<String, Object>> queryMenusForList(User user);

	Map<String, Object> queryMenusForObject(Menu menu);
}
