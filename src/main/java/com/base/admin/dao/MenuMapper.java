package com.base.admin.dao;

import java.util.List;
import java.util.Map;

import com.base.admin.entity.Menu;
import com.base.admin.entity.User;

public interface MenuMapper {
	int deleteByPrimaryKeys(Menu menu);

	int insertSelective(Menu menu);

	int updateByPrimaryKeySelective(Menu menu);

	List<Map<String, Object>> queryMenusForTree(Menu menu);

	List<Map<String, Object>> queryMenusForPage(Menu menu);

	Map<String, Object> queryMenusForObject(Menu menu);

	List<Map<String, Object>> queryMenusForList(User user);

	int queryCountOfMenusForPage(Menu menu);

	int updataInnerData();
}