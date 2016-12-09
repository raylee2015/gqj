package com.base.admin.service;

import java.util.Map;

import com.base.admin.entity.Post;
import com.base.admin.entity.PostMenu;

public interface IPostMenuService {
	int deleteByPrimaryKeys(PostMenu postMenu);

	int insert(PostMenu postMenu);

	Map<String, Object> selectPostsForPage(Post post);

	String selectDeptsForTree();

	String querySelectedMenusForTree(PostMenu postMenu);

	String queryUnSelectedMenusForTree();

}
