package com.base.admin.dao;

import java.util.List;
import java.util.Map;

import com.base.admin.entity.Post;
import com.base.admin.entity.PostUser;

public interface PostUserMapper {
	int deleteUsersByPost(PostUser postUser);

	int insertUsersByPost(PostUser postUser);

	int deletePostsByUser(PostUser postUser);

	int insertPostsByUser(PostUser postUser);

	String selectDeptsForTree();

	Map<String, Object> selectPostsForPage(Post post);

	List<Map<String, Object>> querySelectedUsers(PostUser postUser);

	List<Map<String, Object>> queryUnSelectedUsers(PostUser postUser);

	int queryCountOfSelectedUsers(PostUser postUser);

	int queryCountOfUnSelectedUsers(PostUser postUser);

	List<Map<String, Object>> querySelectedPosts(PostUser postUser);

	List<Map<String, Object>> queryUnSelectedPosts(PostUser postUser);

	int queryCountOfSelectedPosts(PostUser postUser);

	int queryCountOfUnSelectedPosts(PostUser postUser);

}