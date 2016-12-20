package com.base.admin.service;

import java.util.List;
import java.util.Map;

import com.base.admin.entity.Menu;
import com.base.admin.entity.User;

public interface IMenuService {
	int deleteByPrimaryKeys(Menu menu);

	int insertSelective(Menu menu);

	int updateByPrimaryKeySelective(Menu menu);

	String queryMenusForTree(Menu menu);

	Map<String, Object> queryMenusForPage(Menu menu);

	int updataInnerData();

	List<Map<String, Object>> queryMenusForList(User user);
}
