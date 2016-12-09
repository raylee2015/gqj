package com.base.admin.dao;

import java.util.List;
import java.util.Map;

import com.base.admin.entity.PostMenu;

public interface PostMenuMapper {
	int deleteByPrimaryKeys(PostMenu postMenu);

	int insert(PostMenu postMenu);

	int selectCountByPrimaryKey(PostMenu postMenu);

	List<Map<String, Object>> selectSelectedMenusForTree(PostMenu postMenu);
}