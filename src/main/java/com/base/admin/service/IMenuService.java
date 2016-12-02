package com.base.admin.service;

import java.util.List;
import java.util.Map;

import com.base.admin.entity.Menu;

public interface IMenuService {
	int deleteByPrimaryKeys(Menu menu);

	int insertSelective(Menu menu);

	int updateByPrimaryKeySelective(Menu menu);

	String selectMenusForTree();

	List<Map<String, Object>> selectMenusForPage(Menu menu);

	int selectCountOfMenusForPage(Menu menu);
}
