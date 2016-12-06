package com.base.admin.dao;

import java.util.List;
import java.util.Map;

import com.base.admin.entity.Menu;

public interface MenuMapper {
	int deleteByPrimaryKeys(Menu menu);

	int insertSelective(Menu menu);

	int updateByPrimaryKeySelective(Menu menu);

	List<Map<Object, Object>> selectMenusForTree(Menu menu);

	List<Map<String, Object>> selectMenusForPage(Menu menu);

	int selectCountOfMenusForPage(Menu menu);
}