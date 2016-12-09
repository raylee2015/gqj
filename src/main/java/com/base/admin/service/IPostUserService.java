package com.base.admin.service;

import java.util.Map;

import com.base.admin.entity.Post;
import com.base.admin.entity.PostUser;

public interface IPostUserService {
	int deleteByPrimaryKeys(PostUser postUser);

	int insert(PostUser postUser);

	Map<String, Object> selectPostsForPage(Post post);

	String selectDeptsForTree();

	Map<String, Object> querySelectedUsersForPage(PostUser postUser);

	Map<String, Object> queryUnSelectedUsersForPage(PostUser postUser);

}
