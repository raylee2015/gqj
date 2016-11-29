package com.base.admin.service;

import java.util.List;

import com.base.admin.entity.Menu;

public interface IMenuService {
	int deleteByPrimaryKey(Long menuId);

	int insert(Menu menu);

	int insertSelective(Menu menu);

	Menu selectByPrimaryKey(Long menuId);

	int updateByPrimaryKeySelective(Menu menu);

	int updateByPrimaryKey(Menu menu);

	List<Menu> selectMenusForList(Menu menu);

	List<Menu> selectMenusForPage(Menu menu);

	int selectCountOfMenusForPage(Menu menu);
}
