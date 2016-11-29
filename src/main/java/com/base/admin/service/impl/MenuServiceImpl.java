package com.base.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.admin.dao.IMenuMapper;
import com.base.admin.entity.Menu;
import com.base.admin.service.IMenuService;

@Service
public class MenuServiceImpl implements IMenuService {

	@Autowired
	private IMenuMapper menuMapper;

	@Override
	public int deleteByPrimaryKey(Long menuId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(Menu menu) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertSelective(Menu menu) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Menu selectByPrimaryKey(Long menuId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateByPrimaryKeySelective(Menu menu) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKey(Menu menu) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Menu> selectMenusForList(Menu menu) {
		return menuMapper.selectMenusForList(menu);
	}

	@Override
	public List<Menu> selectMenusForPage(Menu menu) {
		return menuMapper.selectMenusForPage(menu);
	}

	@Override
	public int selectCountOfMenusForPage(Menu menu) {
		return menuMapper.selectCountOfMenusForPage(menu);
	}

}
