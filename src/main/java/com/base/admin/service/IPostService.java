package com.base.admin.service;

import java.util.Map;

import com.base.admin.entity.Post;

public interface IPostService {
	Map<String, Object> deletePosts(Post post);

	Map<String, Object> addNewPost(Post post);

	Map<String, Object> updatePost(Post post);

	Map<String, Object> selectPostsForPage(Post post);

}
