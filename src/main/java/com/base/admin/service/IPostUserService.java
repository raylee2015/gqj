package com.base.admin.service;

import java.util.Map;

import com.base.admin.entity.Post;
import com.base.admin.entity.PostUser;

public interface IPostUserService {
	Map<String, Object> deleteUsersByPost(PostUser postUser);

	Map<String, Object> insertUsersByPost(PostUser postUser);

	Map<String, Object> selectPostsForPage(Post post);

	String selectDeptsForTree();

	Map<String, Object> querySelectedUsersForPage(PostUser postUser);

	Map<String, Object> queryUnSelectedUsersForPage(PostUser postUser);

	Map<String, Object> querySelectedPostsForPage(PostUser postUser);

	Map<String, Object> queryUnSelectedPostsForPage(PostUser postUser);

	int deletePostsByUser(PostUser postUser);

	int insertPostsByUser(PostUser postUser);

}
