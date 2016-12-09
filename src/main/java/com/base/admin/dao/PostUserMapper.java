package com.base.admin.dao;

import java.util.List;
import java.util.Map;

import com.base.admin.entity.Post;
import com.base.admin.entity.PostUser;

public interface PostUserMapper {
	int deleteByPrimaryKeys(PostUser postUser);

	int insert(PostUser postUser);

	String selectDeptsForTree();

	Map<String, Object> selectPostsForPage(Post post);

	List<Map<String, Object>> querySelectedUsers(PostUser postUser);

	List<Map<String, Object>> queryUnSelectedUsers(PostUser postUser);

	int queryCountOfSelectedUsers(PostUser postUser);

	int queryCountOfUnSelectedUsers(PostUser postUser);

}