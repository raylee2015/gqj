package com.base.admin.service;

import java.util.Map;

import com.base.admin.entity.Post;
import com.base.admin.entity.PostUser;

public interface IPostUserService {
	int deleteUsersByPost(PostUser postUser);

	int insertUsersByPost(PostUser postUser);

	Map<String, Object> selectPostsForPage(Post post);

	String selectDeptsForTree();

	Map<String, Object> querySelectedUsersForPage(PostUser postUser);

	Map<String, Object> queryUnSelectedUsersForPage(PostUser postUser);

	Map<String, Object> querySelectedPostsForPage(PostUser postUser);

	Map<String, Object> queryUnSelectedPostsForPage(PostUser postUser);

	int deletePostsByUser(PostUser postUser);

	int insertPostsByUser(PostUser postUser);

}
