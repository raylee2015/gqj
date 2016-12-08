package com.base.admin.service;

import java.util.Map;

import com.base.admin.entity.Post;

public interface IPostService {
	int deleteByPrimaryKeys(Post post);

	int insertSelective(Post post);

	int updateByPrimaryKeySelective(Post post);

	Map<String, Object>  selectPostsForPage(Post post);

}
