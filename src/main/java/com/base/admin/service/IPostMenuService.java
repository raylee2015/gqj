package com.base.admin.service;

import java.sql.SQLException;
import java.util.Map;

import com.base.admin.entity.Post;
import com.base.admin.entity.PostMenu;

public interface IPostMenuService {
	Map<String, Object> deleteByPrimaryKeys(PostMenu postMenu);

	Map<String, Object> insert(PostMenu postMenu);

	Map<String, Object> selectPostsForPage(Post post);

	String selectDeptsForTree();

	String querySelectedMenusForTree(PostMenu postMenu) throws SQLException;

	String queryUnSelectedMenusForTree() throws SQLException;

}
